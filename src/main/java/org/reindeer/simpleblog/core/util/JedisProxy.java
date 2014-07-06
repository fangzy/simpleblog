package org.reindeer.simpleblog.core.util;

import org.springframework.beans.BeansException;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.CallbackFilter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.lang.reflect.Method;

/**
 * Created by fzy on 2014/7/6.
 */
@Component
public class JedisProxy implements ApplicationContextAware {

    private static final CallbackFilter FINALIZE_FILTER = new CallbackFilter() {
        public int accept(Method method) {
            if (method.getName().equals("finalize") &&
                    method.getParameterTypes().length == 0 &&
                    method.getReturnType() == Void.TYPE) {
                return 0;
            }
            return 1;
        }
    };
    private static ApplicationContext ac;

    public static Jedis create() {
        Enhancer en = new Enhancer();
        en.setSuperclass(Jedis.class);
        en.setCallbackFilter(FINALIZE_FILTER);
        en.setCallbacks(new Callback[]{NoOp.INSTANCE, ac.getBean(JedisCallback.class)});
        return (Jedis) en.create(new Class[]{String.class}, new Object[]{"cglibProxy"});
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }
}
