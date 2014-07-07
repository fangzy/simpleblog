package org.reindeer.simpleblog.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by fzy on 2014/7/6.
 */
public class GitHelperTest {

    @Test
    public void testCloneRemoteRepository() throws Exception {
        String path = "%BLOG_TEST%";
        GitHelper.cloneRemoteRepository(path, "https://github.com/fangzy/simpleblog.git");
    }

    @Test
    public void testPullRemoteRepository() throws Exception {
        String path = "D:/contenttest/";
        GitHelper.pullRemoteRepository(path);
    }

    @Test
    public void testGetRealPath() {
        Assert.assertEquals("D:\\blogTest\\", GitHelper.getRealPath("%BLOG_TEST%"));
        Assert.assertEquals("D:\\blogTest\\", GitHelper.getRealPath("${BLOG_TEST}"));
        Assert.assertEquals("D:\\blogTest\\", GitHelper.getRealPath("$BLOG_TEST"));
        Assert.assertEquals("D:\\blogTest\\test\\", GitHelper.getRealPath("%BLOG_TEST%/test"));
        Assert.assertEquals("D:\\blogTest\\test\\", GitHelper.getRealPath("${BLOG_TEST}/test"));
        Assert.assertEquals("D:\\blogTest\\test\\", GitHelper.getRealPath("$BLOG_TEST/test"));
        Assert.assertEquals("D:/contenttest/", GitHelper.getRealPath("D:/contenttest/"));
    }
}
