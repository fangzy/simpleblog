package org.reindeer.simpleblog.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;

/**
 * Created by fzy on 2014/6/25.
 */
public class SiteConfig {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private Properties properties = new Properties();

    public SiteConfig() {

    }

    public SiteConfig(Resource resource) {
        try {
            properties.load(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            logger.error("Can't load settings.", e);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public void put(String key, String value) {
        this.properties.put(key, value);
    }

    public Hashtable<Object, Object> properties() {
        return properties;
    }
}
