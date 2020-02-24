package com.dmi3.projects.exceptions;

public class TextProcessingServiceException extends AbstractServiceException
{
    public TextProcessingServiceException(String message, Exception exception)
    {
        super(message, exception);
    }

    public TextProcessingServiceException(String message)
    {
        super(message);
    }
}
