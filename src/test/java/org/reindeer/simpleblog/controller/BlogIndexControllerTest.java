package org.reindeer.simpleblog.controller;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.reindeer.simpleblog.core.model.PageView;
import org.reindeer.simpleblog.test.AbstractControllerTests;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by fzy on 2014/7/5.
 */
public class BlogIndexControllerTest extends AbstractControllerTests {
    @Test
    public void testGetIndex() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/"))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(PageView.class)))
                .andReturn();
        PageView view = (PageView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("Hello world!", view.getBlogDataList().get(0).getTitle());
        Assert.assertEquals(1, view.getPageTotal());
        Assert.assertEquals(1, view.getPageNo());

        this.mockMvc.perform(get("/index"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/page/1"))
                .andExpect(view().name("home"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(PageView.class)))
                .andReturn();
        PageView view = (PageView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("Hello world!", view.getBlogDataList().get(0).getTitle());
        Assert.assertEquals(1, view.getPageTotal());
        Assert.assertEquals(1, view.getPageNo());

        this.mockMvc.perform(get("/page/2"))
                .andExpect(status().isNotFound());
    }
}
