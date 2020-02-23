package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.ProjectConfigurationException;
import com.dmi3.projects.services.api.ProjectConfigurationService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectConfigurationServiceImpl implements ProjectConfigurationService
{
    private static final Logger LOG = Logger.getLogger(ProjectConfigurationServiceImpl.class);
    private static final Pattern DELIMITER_PATTERN = Pattern.compile("\'(.*?)\'");
    private static final String CONFIG_FILE_PATH = "Config.txt";
    private final Properties properties;
    private ConfigurationDto config;

    public ProjectConfigurationServiceImpl()
    {
        this.properties = new Properties();
    }

    public ConfigurationDto getConfig()
    {
        if (config == null)
        {
            return init();
        }
        return config;
    }

    @Override
    public ConfigurationDto init() throws ProjectConfigurationException
    {
        config = new ConfigurationDto();

        try
        {
            properties.load(new FileInputStream(CONFIG_FILE_PATH));
        }
        catch (IOException ex)
        {
            String errorMessage = "Error occurred during getting configurations";
            LOG.error(errorMessage, ex);
            throw new ProjectConfigurationException(errorMessage, ex);
        }

        initDelimiters((String) properties.get(ConfigParams.DELIMITERS_CONFIG.getConfigKey()));
        initOnlyTextFromHtml((String) properties.get(ConfigParams.ONLY_TEXT_FROM_HTML.getConfigKey()));
        initParsedPageFilePath((String) properties.get(ConfigParams.PARSED_PAGE_FILE_PATH.getConfigKey()));
        initResultFilePath((String) properties.get(ConfigParams.RESULT_FILE_PATH.getConfigKey()));

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
        DELIMITERS_CONFIG("delimiters"),
        ONLY_TEXT_FROM_HTML("onlyTextFromHtml"),
        PARSED_PAGE_FILE_PATH("parsedPageFilePath"),
        RESULT_FILE_PATH("resultFilePath");

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
