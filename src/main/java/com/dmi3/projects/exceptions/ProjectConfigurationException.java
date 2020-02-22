package com.dmi3.projects.exceptions;

public class ProjectConfigurationException extends AbstractException
{
    public ProjectConfigurationException(String message, Exception exception)
    {
        super(message, exception);
    }
}
