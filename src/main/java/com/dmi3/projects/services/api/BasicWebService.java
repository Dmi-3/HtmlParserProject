package com.dmi3.projects.services.api;

import java.util.Map;

public interface BasicWebService
{
    Map<String, Integer> parseWebPage(String url) ;
    void saveResultToFile(Map<String, Integer> result);
}
