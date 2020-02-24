package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.FileServiceServiceException;
import com.dmi3.projects.exceptions.ProjectConfigurationServiceException;
import com.dmi3.projects.exceptions.TextProcessingServiceException;
import com.dmi3.projects.services.api.BasicWebService;
import com.dmi3.projects.services.api.FileService;
import com.dmi3.projects.services.api.TextProcessingService;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class BasicWebServicesImpl implements BasicWebService
{
    private final FileService fileService;
    private final TextProcessingService textProcessingService;
    private final ConfigurationDto config;

    public BasicWebServicesImpl()
    {
        this.config = new ProjectConfigurationServiceImpl().getConfig();
        this.fileService = new FileServiceImpl(config);
        this.textProcessingService = new TextProcessingServiceImpl(config);
    }

    public ConfigurationDto getConfig()
    {
        return config;
    }

    @Override
    public Map<String, Integer> parseWebPage(String url) throws FileServiceServiceException, ProjectConfigurationServiceException,
            TextProcessingServiceException
    {
        if (StringUtils.isEmpty(url))
        {
            return Collections.emptyMap();
        }
        File file = fileService.downloadPage(url);
        if (file == null)
        {
            return Collections.emptyMap();
        }
        return textProcessingService.processFile(file);
    }

    @Override
    public Map<String, Integer> parseFile(String filePath)
    {
        if (StringUtils.isEmpty(filePath))
        {
            return Collections.emptyMap();
        }

        return textProcessingService.processFile(new File(filePath));
    }

    @Override
    public void saveResultToFile(Map<String, Integer> result)
    {
        String resultText = StringUtils.EMPTY;
        if (MapUtils.isNotEmpty(result))
        {
            resultText = result.keySet().stream().map(word -> word + " - " + result.get(word) + "\n").collect(Collectors.joining());
        }
        fileService.saveFile(resultText, new File(config.getResultFilePath()), false);
    }
}
