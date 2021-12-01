package com.gitee.starblues.spring;

import com.gitee.starblues.core.loader.PluginWrapper;
import com.gitee.starblues.integration.IntegrationConfiguration;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author starBlues
 * @version 1.0
 */
public class DefaultSpringPluginRegistryInfo extends DefaultRegistryInfo implements SpringPluginRegistryInfo{

    private final PluginWrapper pluginWrapper;
    private final PluginSpringApplication pluginSpringApplication;
    private final ConfigurableApplicationContext mainApplicationContext;
    private final IntegrationConfiguration configuration;

    public DefaultSpringPluginRegistryInfo(PluginWrapper pluginWrapper,
                                           PluginSpringApplication springApplication,
                                           ConfigurableApplicationContext mainApplicationContext) {
        this.pluginWrapper = pluginWrapper;
        this.pluginSpringApplication = springApplication;
        this.mainApplicationContext = mainApplicationContext;
        this.configuration = mainApplicationContext.getBean(IntegrationConfiguration.class);
    }

    @Override
    public PluginWrapper getPluginWrapper() {
        return pluginWrapper;
    }

    @Override
    public PluginSpringApplication getPluginSpringApplication() {
        return pluginSpringApplication;
    }

    @Override
    public ConfigurableApplicationContext getMainApplicationContext() {
        return mainApplicationContext;
    }

    @Override
    public IntegrationConfiguration getConfiguration() {
        return configuration;
    }

}
