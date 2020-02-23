package com.dmi3.projects.services.api;

import com.dmi3.projects.dto.ConfigurationDto;

public interface ProjectConfigurationService
{
    ConfigurationDto init();

    ConfigurationDto getConfig();
}
