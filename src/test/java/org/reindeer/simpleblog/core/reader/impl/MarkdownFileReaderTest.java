package org.reindeer.simpleblog.core.reader.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.reindeer.simpleblog.core.model.BlogData;

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
