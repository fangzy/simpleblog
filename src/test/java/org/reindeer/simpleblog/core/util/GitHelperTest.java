package org.reindeer.simpleblog.core.util;

import org.junit.Test;

/**
 * Created by fzy on 2014/7/6.
 */
public class GitHelperTest {

    @Test
    public void testCloneRemoteRepository() throws Exception {
        String path = "D:/contenttest/";
        GitHelper.cloneRemoteRepository(path, "https://github.com/fangzy/simpleblog.git");
    }

    @Test
    public void testPullRemoteRepository() throws Exception {
        String path = "D:/contenttest/";
        GitHelper.pullRemoteRepository(path);
    }
}
