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
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

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

    @Value("classpath:redis.properties")
    private Resource redisSettings;

    @Bean
    public SiteConfig siteConfig() {
        return new SiteConfig(siteSettings);
    }

    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/");
        freeMarkerConfigurer.setDefaultEncoding("UTF-8");
        freeMarkerConfigurer.setConfigLocation(freemarkerSettings);
        return freeMarkerConfigurer;
    }

    @Bean(destroyMethod = "destroy")
    public JedisPool jedisPool() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(redisSettings.getInputStream(), "UTF-8"));
        } catch (IOException e) {
            logger.error("Can't load redisSettings.", e);
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(Integer.parseInt(properties.getProperty("redis.pool.maxTotal")));
        jedisPoolConfig.setMaxIdle(Integer.parseInt(properties.getProperty("redis.pool.maxIdle")));
        jedisPoolConfig.setMaxWaitMillis(Integer.parseInt(properties.getProperty("redis.pool.maxWait")));
        jedisPoolConfig.setTestOnBorrow(Boolean.parseBoolean(properties.getProperty("redis.pool.testOnBorrow")));
        jedisPoolConfig.setTestOnReturn(Boolean.parseBoolean(properties.getProperty("redis.pool.testOnReturn")));

        String host = properties.getProperty("redis.host");
        int port = Integer.parseInt(properties.getProperty("redis.port"));
        String password = properties.getProperty("redis.password");
        return new JedisPool(jedisPoolConfig, host, port, 3000, password);
    }

}
