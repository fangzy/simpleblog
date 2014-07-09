package org.reindeer.simpleblog.core.util;

import org.apache.commons.lang3.StringUtils;
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

/**
 * Created by fzy on 2014/7/6.
 */
public class GitHelper {

    private static final Logger logger = LoggerFactory.getLogger(GitHelper.class);

    public static void cloneRemoteRepository(String localPath, String remoteUrl) {
        File path = new File(FileHelper.getRealPath(localPath));
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
            if (exists) {
                exists = repository.getRemoteNames().contains(remoteUrl);
            }
            if (!exists) {
                logger.warn(path.toString() + " doesn't exist git repository.");
                //TODO delete all files
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

    public static String pullRemoteRepository(String localPath) {
        File path = new File(FileHelper.getRealPath(localPath));
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repository = null;
        try {
            repository = builder.setGitDir(new File(path.getAbsolutePath(), ".git"))
                    .findGitDir()
                    .build();

            PullResult pullResult = new Git(repository).pull().call();
            logger.trace(pullResult.toString());
            logger.debug("Having repository: " + repository.getDirectory() + " with head: " +
                    repository.getRef(Constants.HEAD) + "/" + repository.resolve("HEAD") + "/" +
                    repository.resolve("refs/heads/master"));
            return repository.resolve(Constants.HEAD).getName();
        } catch (Exception e) {
            logger.error("An unexpected error occurred.", e);
        } finally {
            if (repository != null) {
                repository.close();
            }
        }
        return null;
    }

    public static String getLastObjectId(String localPath, String remoteUrl) {
        if (StringUtils.isEmpty(localPath) || StringUtils.isEmpty(remoteUrl)) {
            return null;
        }
        cloneRemoteRepository(localPath, remoteUrl);
        String objectId = pullRemoteRepository(localPath);
        return objectId;
    }

}
