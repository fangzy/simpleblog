package org.reindeer.simpleblog.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by fzy on 2014/7/9.
 */
public class FileHelperTest {

    @Test
    public void testGetRealPath() {
        Assert.assertEquals("D:\\blogTest\\", FileHelper.getRealPath("%BLOG_TEST%"));
        Assert.assertEquals("D:\\blogTest\\", FileHelper.getRealPath("${BLOG_TEST}"));
        Assert.assertEquals("D:\\blogTest\\", FileHelper.getRealPath("$BLOG_TEST"));
        Assert.assertEquals("D:\\blogTest\\test\\", FileHelper.getRealPath("%BLOG_TEST%/test"));
        Assert.assertEquals("D:\\blogTest\\test\\", FileHelper.getRealPath("${BLOG_TEST}/test"));
        Assert.assertEquals("D:\\blogTest\\test\\", FileHelper.getRealPath("$BLOG_TEST/test"));
        Assert.assertEquals("D:/contenttest/", FileHelper.getRealPath("D:/contenttest/"));
    }
}
