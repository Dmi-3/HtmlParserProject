package com.dmi3.projects.exceptions;

public class TextProcessingException extends AbstractException
{
    public TextProcessingException(String message, Exception exception)
    {
        super(message, exception);
    }
}
