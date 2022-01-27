package com.gitee.starblues.core.launcher.plugin;

import com.gitee.starblues.core.descriptor.InsidePluginDescriptor;
import com.gitee.starblues.core.launcher.JavaMainResourcePatternDefiner;
import com.gitee.starblues.spring.MainApplicationContext;
import com.gitee.starblues.utils.ObjectUtils;
import com.gitee.starblues.utils.SpringBeanCustomUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * 定义插件从主程序加载资源的匹配
 * @author starBlues
 * @version 3.0.0
 */
public class PluginMainResourcePatternDefiner extends JavaMainResourcePatternDefiner {

    private static final String FRAMEWORK = "com/gitee/starblues/**";

    public static final String FACTORIES_RESOURCE_LOCATION = "META-INF/spring.factories";

    private final String mainPackage;
    private final InsidePluginDescriptor descriptor;
    private final BasicMainResourcePatternDefiner basicPatternDefiner;

    public PluginMainResourcePatternDefiner(PluginInteractive pluginInteractive) {
        mainPackage = pluginInteractive.getConfiguration().mainPackage();
        this.descriptor = pluginInteractive.getPluginDescriptor();
        basicPatternDefiner = getPatternDefiner(pluginInteractive);
    }

    @Override
    public Set<String> getIncludePatterns() {
        Set<String> includeResourcePatterns = super.getIncludePatterns();
        Set<String> includePatterns = basicPatternDefiner.getIncludePatterns();
        if(!ObjectUtils.isEmpty(includePatterns)){
            includeResourcePatterns.addAll(includePatterns);
        } else {
            includeResourcePatterns.add(mainPackage);
        }
        includeResourcePatterns.add(FRAMEWORK);
        addWebIncludeResourcePatterns(includeResourcePatterns);
        addSwagger(includeResourcePatterns);

        // 配置插件自定义从主程序加载的资源匹配
        Set<String> includeMainResourcePatterns = descriptor.getIncludeMainResourcePatterns();
        if(ObjectUtils.isEmpty(includeMainResourcePatterns)){
            return includeResourcePatterns;
        }

        for (String includeMainResourcePattern : includeMainResourcePatterns) {
            if(ObjectUtils.isEmpty(includeMainResourcePattern)){
                continue;
            }
            includeResourcePatterns.add(includeMainResourcePattern);
        }
        return includeResourcePatterns;
    }



    @Override
    public Set<String> getExcludePatterns() {
        Set<String> excludeResourcePatterns = new HashSet<>();
        Set<String> excludePatterns = basicPatternDefiner.getExcludePatterns();
        if(!ObjectUtils.isEmpty(excludePatterns)){
            excludeResourcePatterns.addAll(excludePatterns);
        }
        Set<String> excludeMainResourcePatterns = descriptor.getExcludeMainResourcePatterns();
        if(!ObjectUtils.isEmpty(excludeMainResourcePatterns)){
            excludeResourcePatterns.addAll(excludeMainResourcePatterns);
        }
        excludeResourcePatterns.add(FACTORIES_RESOURCE_LOCATION);
        return excludeResourcePatterns;
    }



    protected void addWebIncludeResourcePatterns(Set<String> patterns){
        patterns.add("org/springframework/web/**");
        patterns.add("org/springframework/http/**");
        patterns.add("org/springframework/remoting/**");
        patterns.add("org/springframework/ui/**");

        patterns.add("com/fasterxml/jackson/**");
    }

    protected void addSwagger(Set<String> patterns){
        patterns.add("springfox/documentation/**");
        patterns.add("io/swagger/**");
    }

    /**
     * 获取基本的 MainResourcePatternDefiner
     * @param pluginInteractive PluginInteractive
     * @return BasicMainResourcePatternDefiner
     */
    private BasicMainResourcePatternDefiner getPatternDefiner(PluginInteractive pluginInteractive){
        final MainApplicationContext mainApplicationContext = pluginInteractive.getMainApplicationContext();
        BasicMainResourcePatternDefiner definer = SpringBeanCustomUtils.getExistBean(
                mainApplicationContext, BasicMainResourcePatternDefiner.class);
        if(definer == null){
            return new BasicMainResourcePatternDefiner(mainPackage);
        } else {
            return definer;
        }
    }

}
