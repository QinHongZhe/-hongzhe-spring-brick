package com.gitee.starblues.bootstrap;

import com.gitee.starblues.core.descriptor.*;
import com.gitee.starblues.core.launcher.plugin.PluginInteractive;
import com.gitee.starblues.integration.AutoIntegrationConfiguration;
import com.gitee.starblues.integration.IntegrationConfiguration;
import com.gitee.starblues.spring.MainApplicationContext;
import com.gitee.starblues.spring.extract.DefaultOpExtractFactory;
import com.gitee.starblues.spring.extract.OpExtractFactory;
import com.gitee.starblues.spring.invoke.DefaultInvokeSupperCache;
import com.gitee.starblues.spring.invoke.InvokeSupperCache;

import java.nio.file.Paths;

/**
 * 插件自己的Interactive
 * @author starBlues
 * @version 3.0.0
 */
public class PluginOneselfInteractive implements PluginInteractive {

    private final InsidePluginDescriptor pluginDescriptor;
    private final MainApplicationContext mainApplicationContext;
    private final IntegrationConfiguration configuration;
    private final InvokeSupperCache invokeSupperCache;
    private final OpExtractFactory opExtractFactory;

    public PluginOneselfInteractive(){
        this.pluginDescriptor = createPluginDescriptor();
        this.mainApplicationContext = new EmptyMainApplicationContext();
        this.configuration = new AutoIntegrationConfiguration();
        this.invokeSupperCache = new DefaultInvokeSupperCache();
        this.opExtractFactory = new DefaultOpExtractFactory();
    }


    @Override
    public InsidePluginDescriptor getPluginDescriptor() {
        return pluginDescriptor;
    }

    @Override
    public MainApplicationContext getMainApplicationContext() {
        return mainApplicationContext;
    }

    @Override
    public IntegrationConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public InvokeSupperCache getInvokeSupperCache() {
        return invokeSupperCache;
    }

    @Override
    public OpExtractFactory getOpExtractFactory() {
        return opExtractFactory;
    }

    private InsidePluginDescriptor createPluginDescriptor(){
        InsidePluginDescriptor pluginDescriptor;
        try (PluginDescriptorLoader pluginDescriptorLoader = new DevPluginDescriptorLoader()){
            pluginDescriptor = pluginDescriptorLoader.load(
                    Paths.get(this.getClass().getResource("/").toURI()));
            if(pluginDescriptor == null){
                pluginDescriptor = new EmptyPluginDescriptor();
            }
        } catch (Exception e){
            pluginDescriptor = new EmptyPluginDescriptor();
        }
        return pluginDescriptor;
    }
}
