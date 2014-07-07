package org.reindeer.simpleblog.core;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.reader.BlogDataReader;
import org.reindeer.simpleblog.core.repositories.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.util.List;

/**
 * Created by fzy on 2014/6/24.
 */
@Component
public class BlogDataProcessor implements ApplicationListener<ContextRefreshedEvent> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlogDataReader reader;

    @Autowired
    private BlogRepository repository;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    private SiteConfig siteConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            logger.debug("Refresh event was triggered.");
            if (repository.isEmpty()) {
                refreshBlogData();
            }
            setFreeMarkerVariables();
        }
    }

    private void setFreeMarkerVariables() {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try {
            configuration.setAllSharedVariables(new SimpleHash(repository.getFreeMarkerVariables()));
            configuration.setSharedVariable("site", siteConfig.properties());
        } catch (TemplateModelException e) {
            logger.error("error", e);
        }
    }

    private void refreshBlogData() {
        List<BlogData> list = reader.read();
        repository.init(list);
    }
}
