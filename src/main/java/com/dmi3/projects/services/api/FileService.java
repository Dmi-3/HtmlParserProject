package com.dmi3.projects.services.api;

import com.dmi3.projects.exceptions.FileServiceServiceException;

import java.io.File;

public interface FileService
{
    File downloadPage(String url) throws FileServiceServiceException;

    void saveFile(String text, File file, boolean append);
}
