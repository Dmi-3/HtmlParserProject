package com.dmi3.projects.exceptions;

public abstract class AbstractException extends RuntimeException
{
    private final String message;
    private final Exception exception;

    public AbstractException(String message, Exception exception)
    {
        this.message = message;
        this.exception = exception;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Exception getException()
    {
        return exception;
    }
}
