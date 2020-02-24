package com.dmi3.projects.dto;

import java.util.Collection;

public class ConfigurationDto
{
    private Collection<String> delimiters;
    private boolean onlyTextFromHtml;
    private String parsedPageFilePath;
    private String resultFilePath;

    private ConfigurationDto(Builder builder)
    {
        delimiters = builder.delimiters;
        onlyTextFromHtml = builder.onlyTextFromHtml;
        parsedPageFilePath = builder.parsedPageFilePath;
        resultFilePath = builder.resultFilePath;
    }

    public static Builder newBuilder()
    {
        return new Builder();
    }

    public Collection<String> getDelimiters()
    {
        return delimiters;
    }

    public boolean isOnlyTextFromHtml()
    {
        return onlyTextFromHtml;
    }

    public String getParsedPageFilePath()
    {
        return parsedPageFilePath;
    }

    public String getResultFilePath()
    {
        return resultFilePath;
    }

    public static final class Builder
    {
        private Collection<String> delimiters;
        private boolean onlyTextFromHtml;
        private String parsedPageFilePath;
        private String resultFilePath;

        private Builder()
        {
        }

        public Builder delimiters(Collection<String> delimiters)
        {
            this.delimiters = delimiters;
            return this;
        }

        public Builder onlyTextFromHtml(boolean onlyTextFromHtml)
        {
            this.onlyTextFromHtml = onlyTextFromHtml;
            return this;
        }

        public Builder parsedPageFilePath(String parsedPageFilePath)
        {
            this.parsedPageFilePath = parsedPageFilePath;
            return this;
        }

        public Builder resultFilePath(String resultFilePath)
        {
            this.resultFilePath = resultFilePath;
            return this;
        }

        public ConfigurationDto build()
        {
            return new ConfigurationDto(this);
        }
    }
}
