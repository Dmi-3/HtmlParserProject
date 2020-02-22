package com.dmi3.projects.services.api;

import com.dmi3.projects.exceptions.FileServiceException;

import java.io.File;

public interface FileService
{
    File downloadPage(String url) throws FileServiceException;

    void saveFile(String text, File file, boolean append);
}
