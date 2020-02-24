package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.ProjectConfigurationServiceException;
import com.dmi3.projects.services.api.ProjectConfigurationService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Properties;
import java.util.Set;

public class ProjectConfigurationServiceImpl implements ProjectConfigurationService
{
    private static final Logger LOG = Logger.getLogger(ProjectConfigurationServiceImpl.class);
    private static final String CONFIG_FILE_PATH = "config.properties";
    private final Gson gson;

    ProjectConfigurationServiceImpl()
    {
        this.gson = new Gson();
    }

    @Override
    public ConfigurationDto getConfig() throws ProjectConfigurationServiceException
    {
        final Properties properties = new Properties();
        try
        {
            properties.load(new FileInputStream(CONFIG_FILE_PATH));
        }
        catch (IOException ex)
        {
            String errorMessage = "Error occurred during getting configurations";
            LOG.error(errorMessage, ex);
            throw new ProjectConfigurationServiceException(errorMessage, ex);
        }

        final ConfigurationDto configurationDto = ConfigurationDto.newBuilder()
                .delimiters(getDelimitersProperty(properties.getProperty(ConfigParams.DELIMITERS_CONFIG.getConfigKey())))
                .onlyTextFromHtml(getOnlyTextFromHtmlProperty(properties.getProperty(ConfigParams.ONLY_TEXT_FROM_HTML.getConfigKey())))
                .parsedPageFilePath(properties.getProperty(ConfigParams.PARSED_PAGE_FILE_PATH.getConfigKey()))
                .resultFilePath(properties.getProperty(ConfigParams.RESULT_FILE_PATH.getConfigKey()))
                .build();

        validate((configurationDto));

        return configurationDto;
    }

    private void validate(ConfigurationDto configurationDto) throws ProjectConfigurationServiceException
    {
        if (configurationDto.getDelimiters().isEmpty())
        {
            throw new ProjectConfigurationServiceException("You must define minimum one delimiter");
        }
        if (StringUtils.isEmpty(configurationDto.getParsedPageFilePath()))
        {
            throw new ProjectConfigurationServiceException("You must define path to parsed file");
        }

        if (StringUtils.isEmpty(configurationDto.getParsedPageFilePath()))
        {
            throw new ProjectConfigurationServiceException("Path to input parsedFile is incorrect");
        }

    }

    private boolean getOnlyTextFromHtmlProperty(String value)
    {
        return Boolean.parseBoolean(value);
    }

    private Collection<String> getDelimitersProperty(String value)
    {
        if (StringUtils.isEmpty(value))
        {
            throw new ProjectConfigurationServiceException("Delimiters was not found in configuration file!");
        }
        try
        {
            return gson.fromJson(value, new TypeToken<Set<String>>()
            {
            }.getType());
        }
        catch (JsonSyntaxException ex)
        {
            throw new ProjectConfigurationServiceException("Error in configuration file Delimiters written in incorrect format", ex);
        }
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
