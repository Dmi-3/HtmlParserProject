package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.Configuration;
import com.dmi3.projects.exceptions.FileServiceException;
import com.dmi3.projects.exceptions.ProjectConfigurationException;
import com.dmi3.projects.exceptions.TextProcessingException;
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
    private final Configuration config;

    public BasicWebServicesImpl(FileService fileService, TextProcessingService textProcessingService, Configuration config)
    {
        this.fileService = fileService;
        this.textProcessingService = textProcessingService;
        this.config = config;
    }

    @Override
    public Map<String, Integer> parseWebPage(String url) throws FileServiceException, ProjectConfigurationException,
            TextProcessingException
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
