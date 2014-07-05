package org.reindeer.simpleblog.controller;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.reindeer.simpleblog.core.model.BlogView;
import org.reindeer.simpleblog.test.AbstractControllerTests;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by fzy on 2014/7/5.
 */
public class BlogContentControllerTest extends AbstractControllerTests {
    @Test
    public void testGetBlog() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/blog/Hello world!"))
                .andExpect(view().name("blog"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(BlogView.class)))
                .andReturn();
        BlogView view = (BlogView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("Hello world!", view.getBlogData().getTitle());
        Assert.assertEquals("default", view.getBlogData().getCategory());
        Assert.assertEquals("像黑客一样写自己的技术博客", view.getNextTitle());

        result = this.mockMvc.perform(get(URI.create("/blog/像黑客一样写自己的技术博客")))
                .andExpect(view().name("blog"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(BlogView.class)))
                .andReturn();
        view = (BlogView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("像黑客一样写自己的技术博客", view.getBlogData().getTitle());
        Assert.assertEquals("thinking", view.getBlogData().getCategory());
        Assert.assertEquals("Hello world!", view.getPrevTitle());

        this.mockMvc.perform(get("/blog/test"))
                .andExpect(status().isNotFound());
    }
}
