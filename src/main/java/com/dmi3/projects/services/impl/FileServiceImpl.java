package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.Configuration;
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
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class FileServiceImpl implements FileService
{
    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);
    private final Configuration config;

    public FileServiceImpl(Configuration config)
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
            FileUtils.copyURLToFile(new URL(url), file);
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during download page", ex);
            throw new FileServiceException("Error occurred during download page", ex);
        }

        boolean onlyTextFromHtml = config.isOnlyTextFromHtml();
        if (onlyTextFromHtml)
        {
            String textFromHtmlPage = parseHtmlFile(file);
            saveFile(textFromHtmlPage, file, false);
            //todo:rename file extension
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
        elements.forEach(element -> stringBuilder.append(element.text()));
        return stringBuilder.toString();
    }

    private Document getDomContentFromFile(File file)
    {
        try
        {
            return Jsoup.parse(file, StandardCharsets.UTF_8.name());
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during reading local file: " + file.getPath(), ex);
        }
        return null;
    }

    private void saveFile(String text, File file, boolean append)
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(file, append);
            fileOutputStream.write(text.trim().getBytes());
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during saving content to file", ex);
            throw new FileServiceException("Error occurred during saving content to file", ex);
        }
    }
}
