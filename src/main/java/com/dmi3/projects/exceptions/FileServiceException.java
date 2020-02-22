package com.dmi3.projects.exceptions;

public class FileServiceException extends AbstractException
{
    public FileServiceException(String message, Exception exception)
    {
        super(message, exception);
    }
}
