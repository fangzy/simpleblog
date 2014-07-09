package org.reindeer.simpleblog.test;

import org.junit.Test;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.util.JedisProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.util.StopWatch;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by fzy on 2014/7/9.
 */
public class BenchmarkTest extends AbstractControllerTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("classpath:redis.properties")
    private Resource redisSettings;

    @Test
    public void beanMapCreate() throws InterruptedException {
        StopWatch stopWatch = new StopWatch("beanMapCreate");
        stopWatch.start("use beanMap");
        int n = 10000;
        for (int i = 0; i < n; i++) {
            BlogData blogData = new BlogData();
            blogData.init();
        }
        stopWatch.stop();
        System.gc();
        Thread.sleep(5000);

        stopWatch.start("no beanMap");
        for (int i = 0; i < n; i++) {
            BlogData blogData = new BlogData();
            blogData.setTitle("");
        }
        stopWatch.stop();

        logger.info(stopWatch.prettyPrint());
    }

    @Test
    public void jedisPoolTest() throws InterruptedException {
        JedisPool jedisPool = initJedisPool();
        StopWatch stopWatch = new StopWatch("jedisPoolTest");
        stopWatch.start("jedisPool");
        int n = 10000;
        for (int i = 0; i < n; i++) {
            Jedis jedis = jedisPool.getResource();
            jedis.zrevrange("blog:list:all", 0, -1);
            jedisPool.returnResource(jedis);
        }
        stopWatch.stop();
        jedisPool.destroy();
        System.gc();
        Thread.sleep(5000);

        jedisPool = initJedisPool();
        stopWatch.start("jedisProxy");
        for (int i = 0; i < n; i++) {
            Jedis jedis = JedisProxy.create();
            jedis.zrevrange("blog:list:all", 0, -1);
        }
        stopWatch.stop();
        jedisPool.destroy();
        System.gc();
        Thread.sleep(5000);

//        stopWatch.start("jedisNative");
//        for (int i=0;i<n;i++){
//            Jedis jedis = new Jedis("192.168.56.1",6379);
//            jedis.zrevrange("blog:list:all",0,-1);
//            jedis.disconnect();
//        }
//        stopWatch.stop();

        logger.info(stopWatch.prettyPrint());
    }

    private JedisPool initJedisPool() {
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
        return new JedisPool(jedisPoolConfig, host, port);
    }
}
