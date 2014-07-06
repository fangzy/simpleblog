package org.reindeer.simpleblog.core.util;

import org.junit.Test;
import org.reindeer.simpleblog.test.AbstractControllerTests;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * Created by fzy on 2014/7/6.
 */
public class JedisProxyTest extends AbstractControllerTests {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testCreate() throws Exception {
        Jedis jedis = JedisProxy.create();
        logger.debug(jedis.info());
    }
}
