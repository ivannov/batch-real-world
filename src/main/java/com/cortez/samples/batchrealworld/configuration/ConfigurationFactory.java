package com.cortez.samples.batchrealworld.configuration;

import com.cortez.samples.batchrealworld.entity.FolderType;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cortez.samples.batchrealworld.entity.FolderType.*;

/**
 * @author Roberto Cortez
 */
@Startup
@Singleton
@SuppressWarnings("unchecked")
public class ConfigurationFactory {
    private Map<String, Object> configurations;

    @PostConstruct
    public void initConfiguration() {
        configurations = new HashMap<>();

        Map<String, List<FolderType>> folders = new HashMap<>();
        folders.put("batch/work/", Arrays.asList(FI_TMP, FO_TMP));
        folders.put("batch/files/", Arrays.asList(FI, FO));

        configurations.put("folders", folders);
    }

    @Produces
    @Configuration
    public String getString(InjectionPoint injectionPoint) {
        return (String) configurations.get(injectionPoint.getMember().getName());
    }

    @Produces
    @Configuration
    public <K,V> Map<K,V> getMap(InjectionPoint injectionPoint) {
        return (Map) configurations.get(injectionPoint.getMember().getName());
    }
}
