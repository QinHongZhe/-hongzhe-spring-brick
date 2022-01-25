package com.gitee.starblues.core.descriptor;

import java.nio.file.Path;
import java.util.Set;
import java.util.jar.Manifest;

/**
 * 内部的PluginDescriptor
 * @author starBlues
 * @version 3.0.0
 */
public interface InsidePluginDescriptor extends PluginDescriptor{

    /**
     * 得到插件的 Manifest 文件
     * @return Manifest
     */
    Manifest getManifest();


    /**
     * 获取插件配置文件名称
     * @return String
     */
    String getConfigFileName();

    /**
     * 得到内部的插件路径
     * @return Path
     */
    Path getInsidePluginPath();

    /**
     * 获取插件文件名称
     * @return String
     */
    String getPluginFileName();


    /**
     * 获取插件classes path路径
     * @return Path
     */
    String getPluginClassPath();

    /**
     * 获取插件依赖的路径
     * @return String
     */
    Set<String> getPluginLibPaths();

    /**
     * 设置当前插件包含主程序加载资源的匹配
     * @return Set
     */
    Set<String> getIncludeMainResourcePatterns();

    /**
     * 设置当前插件排除从主程序加载资源的匹配
     * @return Set
     */
    Set<String> getExcludeMainResourcePatterns();

    /**
     * 转换为 PluginDescriptor
     * @return PluginDescriptor
     */
    PluginDescriptor toPluginDescriptor();

}
