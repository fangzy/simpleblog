package org.reindeer.simpleblog.springConfig;

import org.reindeer.simpleblog.core.SiteConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

/**
 * Created by fzy on 2014/6/22.
 */
@Configuration
@ComponentScan("org.reindeer.simpleblog.core")
@EnableScheduling
public class AppConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Value("classpath:freemarker.properties")
    private Resource freemarkerSettings;

    @Value("classpath:site.properties")
    private Resource siteSettings;

    @Bean
    public SiteConfig siteConfig() {
        return new SiteConfig(siteSettings);
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath(siteConfig().get("blogTemplate"));
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        freeMarkerConfigurer.setConfigLocation(freemarkerSettings);
        return freeMarkerConfigurer;
    }

}
