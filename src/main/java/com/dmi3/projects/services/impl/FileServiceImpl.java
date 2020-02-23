package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.FileServiceException;
import com.dmi3.projects.services.api.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class FileServiceImpl implements FileService
{
    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);
    private final ConfigurationDto config;

    public FileServiceImpl(ConfigurationDto config)
    {
        this.config = config;
    }

    @Override
    public File downloadPage(String url) throws FileServiceException
    {
        if (StringUtils.isEmpty(url))
        {
            return null;
        }

        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url))
        {
            LOG.warn("Url is not valid.");
            return null;
        }

        String parsedFilePath = config.getParsedPageFilePath();
        if (StringUtils.isBlank(parsedFilePath))
        {
            LOG.warn("Parsed file path was not found");
            return null;
        }

        File file = new File(parsedFilePath);

        try
        {
            FileUtils.write(file, StringUtils.EMPTY,StandardCharsets.UTF_8);
            FileUtils.copyURLToFile(new URL(url), file);
        }
        catch (IOException ex)
        {
            String errorMessage = "Error occurred during download page";
            LOG.error(errorMessage, ex);
            throw new FileServiceException(errorMessage, ex);
        }

        boolean onlyTextFromHtml = config.isOnlyTextFromHtml();
        if (onlyTextFromHtml)
        {
            String textFromHtmlPage = parseHtmlFile(file);
            saveFile(textFromHtmlPage, file, false);
        }

        return file;
    }

    private String parseHtmlFile(File file)
    {
        Document document = getDomContentFromFile(file);
        if (document == null)
        {
            return StringUtils.EMPTY;
        }

        Collection<Element> elements = document.getAllElements();
        StringBuilder stringBuilder = new StringBuilder();
        elements.stream().filter(element -> element.children().size() == 0 && element.hasText())
                .forEach(element -> stringBuilder.append(StringUtils.LF).append(element.text()));
        return stringBuilder.toString();
    }

    private Document getDomContentFromFile(File file) throws FileServiceException
    {
        try
        {
            return Jsoup.parse(file, StandardCharsets.UTF_8.name());
        }
        catch (IOException ex)
        {
            String errorMessage = "Error occurred during reading local file: " + file.getPath();
            LOG.error(errorMessage, ex);
            throw new FileServiceException(errorMessage, ex);
        }
    }

    public void saveFile(String text, File file, boolean append)
    {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))
        {
            writer.write(text.trim());
        }
        catch (IOException ex)
        {
            String errorMessage = "File was not save. Error occurred during saving content to file";
            LOG.error(errorMessage, ex);
            throw new FileServiceException(errorMessage, ex);
        }
    }
}
