package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.TextProcessingServiceException;
import com.dmi3.projects.services.api.TextProcessingService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TextProcessingServiceImpl implements TextProcessingService
{
    private static final Logger LOG = Logger.getLogger(TextProcessingServiceImpl.class);
    private final ConfigurationDto config;

    public TextProcessingServiceImpl(ConfigurationDto config)
    {
        this.config = config;
    }

    @Override
    public Map<String, Integer> processFile(File file) throws TextProcessingServiceException
    {
        if (file == null)
        {
            return Collections.emptyMap();
        }
        Collection<String> delimiters = config.getDelimiters();
        if (CollectionUtils.isEmpty(delimiters))
        {
            throw new TextProcessingServiceException("Text cannot be processed because delimiters was not found in config files");
        }

        Map<String, Integer> processedWords = new HashMap<>();
        final StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath())))
        {
            int charNum = reader.read();
            while (charNum != -1)
            {
                String charString = Character.toString((char) charNum);
                if (config.getDelimiters().contains(charString))
                {
                    if (stringBuilder.length() > 0)
                    {
                        processWord(stringBuilder, processedWords);
                    }
                }
                else
                {
                    stringBuilder.append(charString);
                }

                charNum = reader.read();
            }
            processWord(stringBuilder, processedWords);
        }
        catch (IOException ex)
        {
            String message = "Error occurred during processing text from file";
            LOG.error(message, ex);
            throw new TextProcessingServiceException(message, ex);
        }

        return processedWords;
    }

    private void processWord(StringBuilder stringBuilder, Map<String, Integer> uniqWords)
    {
        if (stringBuilder.length() > 0)
        {
            uniqWords.compute(stringBuilder.toString(), (uniqWord, count) -> count == null ? 1 : count + 1);
            stringBuilder.setLength(0);
        }
    }
}
