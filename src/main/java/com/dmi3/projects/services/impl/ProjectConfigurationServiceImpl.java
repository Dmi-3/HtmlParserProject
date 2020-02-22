package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.Configuration;
import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.ProjectConfigurationException;
import com.dmi3.projects.services.api.ProjectConfigurationService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectConfigurationServiceImpl implements ProjectConfigurationService
{
    private static final Logger LOG = Logger.getLogger(ProjectConfigurationServiceImpl.class);
    private static final String SPLIT_CONFIGS_REGEX = "\\s*=\\s*";
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\'(.?)\'");
    private static final String CONFIG_FILE_PATH = "Config.txt";

    private Configuration config;

    public Configuration getConfig()
    {
        if (config == null)
        {
            return init();
        }
        return config;
    }

    @Override
    public Configuration init()
    {
        config = new ConfigurationDto();
        try
        {
            Collection<String> lines = FileUtils.readLines(new File(CONFIG_FILE_PATH), "UTF-8");
            for (String line : lines)
            {
                String[] params = line.split(SPLIT_CONFIGS_REGEX);
                if (ArrayUtils.isEmpty(params) || params.length < 2)
                {
                    continue;
                }

                String configKey = params[0];
                String configValue = params[1];
                if (ConfigParams.DELIMITERS_CONFIG.getConfigKey().equals(configKey))
                {
                    initDelimiters(configValue);
                    continue;
                }
                if (ConfigParams.ONLY_TEXT_FROM_HTML.getConfigKey().equals(configKey))
                {
                    initOnlyTextFromHtml(configValue);
                    continue;
                }
                if (ConfigParams.PARSED_PAGE_FILE_PATH.getConfigKey().equals(configKey))
                {
                    initParsedPageFilePath(configValue);
                    continue;
                }
                if (ConfigParams.RESULT_FILE_PATH.getConfigKey().equals(configKey))
                {
                    initResultFilePath(configValue);
                }
            }
        }
        catch (IOException ex)
        {
            LOG.error("Project config file was not found.", ex);
            throw new ProjectConfigurationException("Error occured during getting configurations", ex);
        }

        return config;
    }

    private void initDelimiters(String configValue)
    {
        Matcher matcher = DELIMITER_PATTERN.matcher(configValue);
        Collection<String> delimiters = new ArrayList<>();
        while (matcher.find())
        {
            String delimiter = matcher.group(1);
            if (StringUtils.isEmpty(delimiter))
            {
                continue;
            }
            delimiters.add(delimiter);
        }
        config.setDelimiters(delimiters);
    }

    private void initOnlyTextFromHtml(String value)
    {
        config.setOnlyTextFromHtml(Boolean.parseBoolean(value));
    }

    private void initResultFilePath(String path)
    {
        config.setResultFilePath(path);
    }

    private void initParsedPageFilePath(String path)
    {
        config.setParsedPageFilePath(path);
    }

    public enum ConfigParams
    {
        DELIMITERS_CONFIG("config.delimiters"),
        ONLY_TEXT_FROM_HTML("config.onlyTextFromHtml"),
        PARSED_PAGE_FILE_PATH("config.parsedPageFilePath"),
        RESULT_FILE_PATH("config.resultFilePath");

        private final String configKey;

        ConfigParams(String configKey)
        {
            this.configKey = configKey;
        }

        public String getConfigKey()
        {
            return configKey;
        }
    }

}
