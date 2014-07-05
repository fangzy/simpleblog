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
public class BlogArchiveControllerTest extends AbstractControllerTests {

    @Test
    public void testGetIndex() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/archives/"))
                .andExpect(view().name("category"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(CategoryView.class)))
                .andReturn();
        CategoryView view = (CategoryView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("March 2012", view.getCategoryDataList().get(0).getName());
        Assert.assertEquals(new MutableInt(2), view.getCategoryDataList().get(0).getCount());
    }

    @Test
    public void testGetArchive() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/archives/2012/03"))
                .andExpect(view().name("category"))
                .andExpect(model().attribute("view", IsInstanceOf.instanceOf(CategoryView.class)))
                .andReturn();
        CategoryView view = (CategoryView) result.getModelAndView().getModel().get("view");
        Assert.assertEquals("March 2012", view.getCategoryDataList().get(0).getName());
        Assert.assertEquals(new MutableInt(2), view.getCategoryDataList().get(0).getCount());

        this.mockMvc.perform(get("/archives/March 2012"))
                .andExpect(status().is4xxClientError());
    }
}
