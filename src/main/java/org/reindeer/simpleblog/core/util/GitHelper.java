package org.reindeer.simpleblog.core.util;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fzy on 2014/7/6.
 */
public class GitHelper {

    private static final Logger logger = LoggerFactory.getLogger(GitHelper.class);

    private static final Pattern PREFIX_ENV = Pattern.compile("[%|\\$][\\{]?(.*?)[\\}|%]?[\\\\/](.*)");

    public static void cloneRemoteRepository(String localPath, String remoteUrl) {
        File path = new File(getRealPath(localPath));
        if (!path.exists()) {
            path.mkdirs();
        }
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder.setGitDir(new File(path.getAbsolutePath(), ".git"))
                    .findGitDir()
                    .build();

            boolean exists = repository.getObjectDatabase().exists();
            if (!exists) {
                logger.warn(path.toString() + " doesn't exist git repository.");
                Git.cloneRepository()
                        .setURI(remoteUrl)
                        .setDirectory(path)
                        .call();
            }
        } catch (IOException | GitAPIException e) {
            logger.error("An unexpected error occurred.", e);
        } finally {
            if (repository != null) {
                repository.close();
            }
        }
    }

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

    public static void pullRemoteRepository(String localPath) {
        File path = new File(localPath);
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder.setGitDir(new File(path.getAbsolutePath(), ".git"))
                    .findGitDir()
                    .build();

            PullResult pullResult = new Git(repository).pull().call();
            logger.debug(pullResult.toString());
            logger.debug("Having repository: " + repository.getDirectory() + " with head: " +
                    repository.getRef(Constants.HEAD) + "/" + repository.resolve("HEAD") + "/" +
                    repository.resolve("refs/heads/master"));
        } catch (Exception e) {
            logger.error("An unexpected error occurred.", e);
        } finally {
            if (repository != null) {
                repository.close();
            }
        }
    }

}
