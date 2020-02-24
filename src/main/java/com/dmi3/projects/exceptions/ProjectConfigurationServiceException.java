package com.dmi3.projects.exceptions;

public class ProjectConfigurationServiceException extends AbstractServiceException
{
    public ProjectConfigurationServiceException(String message, Exception exception)
    {
        super(message, exception);
    }

    public ProjectConfigurationServiceException(String message)
    {
        super(message);
    }
}
