package com.dmi3.projects.services.impl;

import com.dmi3.projects.dto.ConfigurationDto;
import com.dmi3.projects.exceptions.TextProcessingException;
import com.dmi3.projects.services.api.TextProcessingService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class TextProcessingServiceImpl implements TextProcessingService
{
    private static final Logger LOG = Logger.getLogger(TextProcessingServiceImpl.class);
    private final ConfigurationDto config;

    public TextProcessingServiceImpl(ConfigurationDto config)
    {
        this.config = config;
    }

    @Override
    public Map<String, Integer> processFile(File file) throws TextProcessingException
    {
        if (file == null)
        {
            return Collections.emptyMap();
        }
        Collection<String> delimiters = config.getDelimiters();
        if (CollectionUtils.isEmpty(delimiters))
        {
            throw new TextProcessingException("Text cannot be processed because delimiters was not found in config files", null);
        }
        Pattern delimiterPattern = generateDelimiterRegex(delimiters);

        Map<String, Integer> processedWords = new HashMap<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(file.getPath())))
        {
            String line;
            String lastWord = StringUtils.EMPTY;
            while ((line = reader.readLine()) != null)
            {
                if (StringUtils.isEmpty(line))
                {
                    addUniqWord(lastWord.trim(), processedWords, delimiters);
                }
                line = lastWord + line;
                String[] words = delimiterPattern.split(line);
                if (ArrayUtils.isEmpty(words))
                {
                    continue;
                }
                lastWord = words[words.length - 1];
                for (int i = 0; i < words.length - 1; i++)
                {
                    addUniqWord(words[i].trim(), processedWords, delimiters);
                }
            }
        }
        catch (IOException ex)
        {
            String message = "Error occurred during processing text from file";
            LOG.error(message, ex);
            throw new TextProcessingException(message, ex);
        }

        return processedWords;
    }

    private Pattern generateDelimiterRegex(Collection<String> delimiters)
    {
        String regex = "\\s*" + delimiters.stream().map(elem -> "\\Q" + elem + "\\E").collect(Collectors.joining("|")) + "\\s*";
        try
        {
            return Pattern.compile(regex);
        }
        catch (PatternSyntaxException ex)
        {
            throw new TextProcessingException("Error occurred during processing delimiters", null);
        }
    }

    private void addUniqWord(String word, Map<String, Integer> uniqWords, Collection<String> delimiters)
    {
        if (StringUtils.isEmpty(word))
        {
            return;
        }
        int cnt = 0;
        if (uniqWords.containsKey(word) && !delimiters.contains(word))
        {
            cnt = uniqWords.get(word);
        }
        uniqWords.put(word, ++cnt);
    }

    @Override
    public Map<String, Integer> processText(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            return Collections.emptyMap();
        }

        Collection<String> delimiters = config.getDelimiters();
        if (CollectionUtils.isEmpty(delimiters))
        {
            throw new TextProcessingException("Text cannot be processed because delimiters was not found in config files", null);
        }

        String[] words = text.split('[' + String.join("", delimiters) + ']');
        Map<String, Integer> uniqWords = new HashMap<>();
        for (String word : words)
        {
            if (StringUtils.isEmpty(word))
            {
                continue;
            }
            int cnt = 0;
            if (uniqWords.containsKey(word))
            {
                cnt = uniqWords.get(word);
            }
            uniqWords.put(word, ++cnt);
        }
        return uniqWords;
    }
}
