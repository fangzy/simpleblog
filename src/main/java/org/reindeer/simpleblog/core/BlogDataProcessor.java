package org.reindeer.simpleblog.core;

import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateModelException;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.reader.BlogDataReader;
import org.reindeer.simpleblog.core.repositories.BlogRepository;
import org.reindeer.simpleblog.core.util.GitHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.annotation.Scheduled;
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

    private String objectId;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            logger.debug("Refresh event was triggered.");
            if (!checkRepository()) {
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

    private boolean checkRepository() {
        String objectId = GitHelper.getLastObjectId(siteConfig.get("blogLocal"), siteConfig.get("blogRemote"));
        boolean result = repository.checkObjectId(objectId);
        if (!result) {
            this.objectId = objectId;
        }
        return result;
    }

    private void refreshBlogData() {
        List<BlogData> list = reader.read();
        repository.init(list);
        repository.saveObjectId(this.objectId);
    }

    @Scheduled(cron = "0 1 * * * ?")
    public void syncBlogData(){
        logger.debug("sync test");
        String objectId = GitHelper.pullRemoteRepository(siteConfig.get("blogLocal"));
        boolean result = repository.checkObjectId(objectId);
        if (!result) {
            List<BlogData> list = reader.read();
            repository.syncBlogData(list, objectId);
        }
    }
}
