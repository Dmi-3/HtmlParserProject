package com.dmi3.projects.dto;

import java.util.Collection;

public interface Configuration
{
    Collection<String> getDelimiters();

    void setDelimiters(Collection<String> delimiters);

    boolean isOnlyTextFromHtml();

    void setOnlyTextFromHtml(boolean onlyTextFromHtml);

    String getParsedPageFilePath();

    void setParsedPageFilePath(String parsedPageFilePath);

    String getResultFilePath();

    public void setResultFilePath(String resultFilePath);
}
