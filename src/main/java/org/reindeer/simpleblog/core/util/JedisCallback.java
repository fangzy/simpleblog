package org.reindeer.simpleblog.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Method;

/**
 * Created by fzy on 2014/7/6.
 */
@Component
public class JedisCallback implements MethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JedisPool jedisPool;


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        final Jedis jedis = jedisPool.getResource();
        boolean isBroken = false;
        try {
            return methodProxy.invoke(jedis, objects);
        } catch (Exception e) {
            isBroken = true;
            logger.error("", e);
            throw e;
        } finally {
            release(jedis, isBroken);
        }
    }

    private void release(Jedis jedis, boolean isBroken) {
        if (jedis != null) {
            if (isBroken) {
                jedisPool.returnBrokenResource(jedis);
            } else {
                jedisPool.returnResource(jedis);
            }
        }
    }
}
