package org.reindeer.simpleblog.springConfig;

import org.reindeer.simpleblog.core.SiteConfig;
import org.reindeer.simpleblog.interceptor.CacheCheckInterceptor;
import org.reindeer.simpleblog.interceptor.ProcessTimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Created by fzy on 2014/6/22.
 */
@Configuration
@EnableWebMvc
@ComponentScan("org.reindeer.simpleblog.controller")
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private SiteConfig siteConfig;

    @Bean
    public FreeMarkerViewResolver viewResolver() {
        FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
        viewResolver.setCache(true);
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".ftl");
        viewResolver.setContentType("text/html; charset=utf-8");
        return viewResolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CacheCheckInterceptor());
        registry.addInterceptor(new ProcessTimeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.setOrder(0).addResourceHandler("/favicon.ico")
                .addResourceLocations("/favicon.ico").setCachePeriod(36000);
        registry.setOrder(0).addResourceHandler("/robots.txt")
                .addResourceLocations("/robots.txt").setCachePeriod(36000);
        registry.addResourceHandler("/asset/**")
                .addResourceLocations(siteConfig.get("blogTemplate") + "/asset/")
                .setCachePeriod(Integer.MAX_VALUE);
    }
}
