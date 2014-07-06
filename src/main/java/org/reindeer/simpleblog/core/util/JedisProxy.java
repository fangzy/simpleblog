package org.reindeer.simpleblog.core.util;

import org.springframework.beans.BeansException;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

/**
 * Created by fzy on 2014/7/6.
 */
@Component
public class JedisProxy implements ApplicationContextAware {

    private static ApplicationContext ac;

    public static Jedis create() {
        Enhancer en = new Enhancer();
        en.setSuperclass(Jedis.class);
        en.setCallback(ac.getBean(JedisCallback.class));
        return (Jedis) en.create(new Class[]{String.class}, new Object[]{"cglibProxy"});
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }
}
