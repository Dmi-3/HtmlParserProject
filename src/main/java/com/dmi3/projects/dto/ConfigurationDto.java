package com.dmi3.projects.dto;

import java.util.Collection;

public class ConfigurationDto implements Configuration
{
    private Collection<String> delimiters;
    private boolean onlyTextFromHtml;
    private String parsedPageFilePath;
    private String resultFilePath;

    public Collection<String> getDelimiters()
    {
        return delimiters;
    }

    public void setDelimiters(Collection<String> delimiters)
    {
        this.delimiters = delimiters;
    }

    public boolean isOnlyTextFromHtml()
    {
        return onlyTextFromHtml;
    }

    public void setOnlyTextFromHtml(boolean onlyTextFromHtml)
    {
        this.onlyTextFromHtml = onlyTextFromHtml;
    }

    public String getParsedPageFilePath()
    {
        return parsedPageFilePath;
    }

    public void setParsedPageFilePath(String parsedPageFilePath)
    {
        this.parsedPageFilePath = parsedPageFilePath;
    }

    public String getResultFilePath()
    {
        return resultFilePath;
    }

    public void setResultFilePath(String resultFilePath)
    {
        this.resultFilePath = resultFilePath;
    }

}
