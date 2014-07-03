package org.reindeer.simpleblog.core.reader.impl;

import org.junit.Assert;
import org.junit.Test;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


/**
 * Created by fzy on 2014/7/3.
 */
public class Markdown4jConvertTest {

    @Test
    public void testConvert() throws Exception {
        Markdown4jConvert convert = new Markdown4jConvert();
        Markdown4jProcessor processor = mock(Markdown4jProcessor.class);
        ReflectionTestUtils.setField(convert, "processor", processor, Markdown4jProcessor.class);

        given(processor.process(anyString())).willReturn("converted text");
        String output = convert.convert("input");
        verify(processor).process("input");
        Assert.assertEquals("converted text", output);
    }
}
