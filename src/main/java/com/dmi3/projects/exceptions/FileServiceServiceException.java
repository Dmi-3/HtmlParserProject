package com.dmi3.projects.exceptions;

public class FileServiceServiceException extends AbstractServiceException
{
    public FileServiceServiceException(String message, Exception exception)
    {
        super(message, exception);
    }

    public FileServiceServiceException(String message)
    {
        super(message);
    }
}
