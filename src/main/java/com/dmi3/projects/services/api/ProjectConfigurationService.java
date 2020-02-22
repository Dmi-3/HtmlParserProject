package com.dmi3.projects.services.api;

import com.dmi3.projects.dto.Configuration;

public interface ProjectConfigurationService
{
    Configuration init();

    Configuration getConfig();
}
