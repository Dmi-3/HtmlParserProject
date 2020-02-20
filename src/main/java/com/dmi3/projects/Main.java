package com.dmi3.projects;

import com.dmi3.projects.parsers.HtmlParser;
import com.dmi3.projects.services.api.FileService;
import com.dmi3.projects.services.api.TextProcessingService;
import com.dmi3.projects.services.impl.FileServiceImpl;
import com.dmi3.projects.services.impl.TextPrecessingServiceImpl;

import java.io.File;
import java.util.Map;

public class Main
{
    public static void main(String[] args)
    {
        FileService fileService = new FileServiceImpl();
        File file = fileService.downloadPage("https://www.google.com/");
        HtmlParser htmlParser = new HtmlParser();

        //System.out.println(htmlParser.parseHtmlFile(file));
        TextProcessingService textProcessingService = new TextPrecessingServiceImpl();
        String parsedPage = htmlParser.parseHtmlFile(file);
        Map<String, Integer> uniqWords = textProcessingService.processText(parsedPage);
        for (String word : uniqWords.keySet())
        {
            System.out.println(word + '-' + uniqWords.get(word));
        }
    }
}
