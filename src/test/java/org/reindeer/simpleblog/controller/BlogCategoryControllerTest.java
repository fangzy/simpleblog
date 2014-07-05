package org.reindeer.simpleblog.controller;

import org.apache.commons.lang3.mutable.MutableInt;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Assert;
import org.junit.Test;
import org.reindeer.simpleblog.core.model.CategoryView;
import org.reindeer.simpleblog.test.AbstractControllerTests;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by fzy on 2014/7/5.
 */
public class BlogCategoryControllerTest extends AbstractControllerTests {
    @Test
    public void testGetIndex() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/category/"))
                .andExpect(view().name("category"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(CategoryView.class)))
                .andReturn();
        CategoryView view = (CategoryView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("default", view.getCategoryDataList().get(0).getName());
        Assert.assertEquals("thinking", view.getCategoryDataList().get(1).getName());
        Assert.assertEquals(new MutableInt(1), view.getCategoryDataList().get(0).getCount());
        Assert.assertEquals("Hello world!", view.getCategoryDataList().get(0).getBlogList().get(0).getTitle());
    }

    @Test
    public void testGetCategory() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/category/thinking"))
                .andExpect(view().name("category"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(CategoryView.class)))
                .andReturn();
        CategoryView view = (CategoryView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("thinking", view.getCategoryDataList().get(0).getName());
        Assert.assertEquals(new MutableInt(1), view.getCategoryDataList().get(0).getCount());
        Assert.assertEquals("像黑客一样写自己的技术博客", view.getCategoryDataList().get(0).getBlogList().get(0).getTitle());

        this.mockMvc.perform(get("/category/test"))
                .andExpect(status().isNotFound());
    }
}
