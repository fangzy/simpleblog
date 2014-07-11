package org.reindeer.simpleblog.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by fzy on 2014/7/12.
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext ac;

    public static <T> T getBean(Class<T> tClass) {
        return ac.getBean(tClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ac = applicationContext;
    }
}
