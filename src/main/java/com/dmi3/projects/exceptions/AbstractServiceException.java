package com.dmi3.projects.exceptions;

public abstract class AbstractServiceException extends RuntimeException
{
    public AbstractServiceException(String message, Exception exception)
    {
        super(message, exception);
    }

    public AbstractServiceException(String message)
    {
        super(message);
    }
}
