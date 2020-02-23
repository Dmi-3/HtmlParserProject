package com.dmi3.projects.services.api;

import com.dmi3.projects.dto.ConfigurationDto;

import java.util.Map;

public interface BasicWebService
{
    ConfigurationDto getConfig();

    Map<String, Integer> parseWebPage(String url);

    Map<String, Integer> parseFile(String filePath);

    void saveResultToFile(Map<String, Integer> result);
}
