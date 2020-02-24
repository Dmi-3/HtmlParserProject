package com.dmi3.projects.services.api;

import java.io.File;
import java.util.Map;

public interface TextProcessingService
{
    Map<String, Integer> processFile(File file);
}
