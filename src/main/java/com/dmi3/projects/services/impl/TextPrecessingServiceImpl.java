package com.dmi3.projects.services.impl;

import com.dmi3.projects.services.api.TextProcessingService;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TextPrecessingServiceImpl implements TextProcessingService
{
    private static final String REGEX_DELIMETERS = "[ ,.!?\";:\\[\\]()\n\r\t]";

    @Override
    public Map<String, Integer> processText(String text)
    {
        if (StringUtils.isEmpty(text))
        {
            return Collections.emptyMap();
        }

        String[] words = text.split(REGEX_DELIMETERS);
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
