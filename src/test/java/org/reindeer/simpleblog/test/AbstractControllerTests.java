package org.reindeer.simpleblog.test;

import org.junit.Before;
import org.reindeer.simpleblog.springConfig.AppConfig;
import org.reindeer.simpleblog.springConfig.MvcConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by fzy on 2014/7/5.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class, MvcConfig.class})
public abstract class AbstractControllerTests extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected WebApplicationContext wac;

    protected MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = webAppContextSetup(this.wac).build();
    }
}
