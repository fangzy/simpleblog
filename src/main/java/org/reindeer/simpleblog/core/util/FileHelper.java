package org.reindeer.simpleblog.core.util;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fzy on 2014/7/9.
 */
public class FileHelper {
    private static final Pattern PREFIX_ENV = Pattern.compile("[%|\\$][\\{]?(.*?)[\\}|%]?[\\\\/](.*)");

    public static String getRealPath(String localPath) {
        String realPath = localPath;
        if (!localPath.endsWith(File.separator)) {
            localPath = localPath + File.separator;
        }
        Matcher matcher = PREFIX_ENV.matcher(localPath);
        if (matcher.find() && matcher.groupCount() == 2) {
            realPath = System.getenv(matcher.group(1)) + File.separator + matcher.group(2);
        }
        return realPath;
    }
}
