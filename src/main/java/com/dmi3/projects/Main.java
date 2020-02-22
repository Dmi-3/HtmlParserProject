package com.dmi3.projects;

import com.dmi3.projects.gui.ProjectGui;
import com.dmi3.projects.services.api.BasicWebService;
import com.dmi3.projects.services.api.FileService;
import com.dmi3.projects.services.api.ProjectConfigurationService;
import com.dmi3.projects.services.api.TextProcessingService;
import com.dmi3.projects.services.impl.BasicWebServicesImpl;
import com.dmi3.projects.services.impl.FileServiceImpl;
import com.dmi3.projects.services.impl.ProjectConfigurationServiceImpl;
import com.dmi3.projects.services.impl.TextProcessingServiceImpl;

public class Main
{
    public static void main(String[] args)
    {
        ProjectConfigurationService configService = new ProjectConfigurationServiceImpl();
        TextProcessingService textProcessingService = new TextProcessingServiceImpl(configService.getConfig());
        FileService fileService = new FileServiceImpl(configService.getConfig());
        BasicWebService basicWebService = new BasicWebServicesImpl(fileService, textProcessingService);
        ProjectGui projectGui = new ProjectGui(basicWebService);
        projectGui.showGui();
    }
}
