package org.reindeer.simpleblog.core.reader.impl;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reindeer.simpleblog.core.SiteConfig;
import org.reindeer.simpleblog.core.model.BlogData;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by fzy on 2014/6/30.
 */
public class MarkdownFileReaderTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRead() throws Exception {
        SiteConfig config = new SiteConfig();
        config.put("blogContentPath", "src/test/resources/data");
        MarkdownFileReader markdownFileReader = new MarkdownFileReader();
        ReflectionTestUtils.setField(markdownFileReader, "config", config, SiteConfig.class);
        markdownFileReader.setConvert(new Markdown4jConvert());
        List<BlogData> list = markdownFileReader.read();
        Collections.sort(list);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("Hello world!", list.get(0).getTitle());
        Assert.assertFalse("".equals(list.get(0).getContent()));
        Assert.assertEquals("thinking", list.get(1).getCategory());
    }

    @Test
    public void testSetMetaInfo() throws Exception {
        MarkdownFileReader markdownFileReader = new MarkdownFileReader();
        BlogData mockBlog = Mockito.mock(BlogData.class);

        String line1 = "title: \"java nio:Java NIO Tutorial\"  ";
        markdownFileReader.setMetaInfo(mockBlog, line1);
        Mockito.verify(mockBlog).setValue("title", "\"java nio:Java NIO Tutorial\"");

        String line2 = " title : \"java nio : Java NIO Tutorial\"  ";
        markdownFileReader.setMetaInfo(mockBlog, line2);
        Mockito.verify(mockBlog).setValue("title", "\"java nio:Java NIO Tutorial\"");
    }
}
