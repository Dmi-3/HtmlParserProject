package com.dmi3.projects.services.impl;

import com.dmi3.projects.exceptions.FileServiceException;
import com.dmi3.projects.exceptions.ProjectConfigurationException;
import com.dmi3.projects.exceptions.TextProcessingException;
import com.dmi3.projects.services.api.BasicWebService;
import com.dmi3.projects.services.api.FileService;
import com.dmi3.projects.services.api.TextProcessingService;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.Collections;
import java.util.Map;

public class BasicWebServicesImpl implements BasicWebService
{
    private final FileService fileService;
    private final TextProcessingService textProcessingService;

    public BasicWebServicesImpl(FileService fileService, TextProcessingService textProcessingService)
    {
        this.fileService = fileService;
        this.textProcessingService = textProcessingService;
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
}
