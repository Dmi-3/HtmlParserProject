package com.dmi3.projects.services.impl;

import com.dmi3.projects.parsers.HtmlParser;
import com.dmi3.projects.services.api.FileService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class FileServiceImpl implements FileService
{
    private static final Logger LOG = Logger.getLogger(FileServiceImpl.class);
    private static final String WORDS_REGEX = "";
    private final static String HTML_FILE_PATH = "Test.html";
    private static final String RESULT_FILE_PATH = "TestResult.txt";

    @Override
    public File downloadPage(String url)
    {
        if (StringUtils.isEmpty(url))
        {
            return null;
        }

        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url))
        {
            return null;
        }

        File file = new File(HTML_FILE_PATH);
        try
        {

            FileUtils.copyURLToFile(new URL(url), file);
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during download page");
        }

        return file;
    }
}
