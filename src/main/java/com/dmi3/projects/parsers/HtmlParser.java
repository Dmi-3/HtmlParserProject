package com.dmi3.projects.parsers;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

public class HtmlParser
{
    private static final Logger LOG = Logger.getLogger(HtmlParser.class);
    private static final String WORDS_REGEX = "";
    private final static String HTML_FILE_PATH = "Test.html";
    private static final String RESULT_FILE_PATH = "TestResult.txt";
    //    private static final String  = {' ', ',', '.', '!', '?', '"', ';', ':', '[', ']', '(', ')',
//            '\n', '\r', '\t'};
    private final Pattern pattern = Pattern.compile("\\S");

    public Map<String, Integer> parseWebContent(String url)
    {
       /* saveWebPage(url);
        Document document = getDomContentFromFile();
        if (document == null)
        {
            return Collections.emptyMap();
        }

        Map<String, Integer> map = new HashMap<>();
        for(Element element : document.getAllElements())
        {
            String text = element.text();
            if(StringUtils.isBlank(text))
            {
                continue;
            }
            text = text.split()

        }*/
        return null;
    }

    public Document getDomContentFromFile(File file)
    {
        try
        {
            return Jsoup.parse(file, "ISO-8859-1");
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during reading local file: " + HTML_FILE_PATH, ex);
        }
        return null;
    }

    public String parseHtmlFile(File file)
    {
        Document document = getDomContentFromFile(file);
        if (document == null)
        {
            return StringUtils.EMPTY;
        }

        Collection<Element> elements = document.getAllElements();
        StringBuilder stringBuilder = new StringBuilder();
        elements.forEach(element -> stringBuilder.append(element.text()));
        return stringBuilder.toString();
    }

    private void saveWebPage(String url)
    {
        if (StringUtils.isEmpty(url))
        {
            return;
        }

        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url))
        {
            return;
        }

        String html = getHtmlFromWebPage(url);
        saveFile(html);
    }

    private String getHtmlFromWebPage(String url)
    {
        try
        {
            Connection.Response response = Jsoup.connect(url).execute();
            if (response.statusCode() == 200)
            {
                return response.body();
            }
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during parsing web page by url: " + url, ex);
        }

        return StringUtils.EMPTY;
    }

    private void saveFile(String text)
    {
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(HTML_FILE_PATH));
            fileOutputStream.write(text.trim().getBytes());
        }
        catch (IOException ex)
        {
            LOG.error("Error occurred during saving content to file", ex);
        }
    }

}
