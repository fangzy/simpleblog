package org.reindeer.simpleblog.core.reader.impl;

import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.reader.BlogDataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Read all the markdown files in the content path
 * Created by fzy on 2014/6/25.
 */
@Component
public class MarkdownFileReader extends BlogDataReader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public MarkdownFileReader() {
        setConvert(new Markdown4jConvert());
    }

    @Override
    public List<BlogData> read() {
        MarkdownFileFinder finder = new MarkdownFileFinder();
        try {
            Files.walkFileTree(Paths.get(config.get("blogContentPath")), finder);
        } catch (IOException e) {
            logger.error("Error occured while searching markdown files.", e);
        }
        List<Path> files = finder.getPaths();
        if (files.size() == 0) {
            throw new RuntimeException("Can't find any markdown files.");
        }
        logger.debug("All the files : " + files.toString());

        ExecutorService exec = Executors.newFixedThreadPool(10, new ThreadFactory() {
            AtomicInteger i = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                String threadName = "MarkdownFileReader#" + i.addAndGet(1) + " thread";
                return new Thread(r, threadName);
            }
        });
        CompletionService<BlogData> completionService = new ExecutorCompletionService<>(exec);
        for (Path file : files) {
            completionService.submit(new ReadTask(file));
        }
        List<BlogData> list = new ArrayList<>();
        for (int i = 0, n = files.size(); i < n; i++) {
            try {
                list.add(completionService.take().get());
            } catch (InterruptedException | ExecutionException e) {
                logger.error(null, e);
            }
        }
        exec.shutdown();
        return list;
    }

    class MarkdownFileFinder extends SimpleFileVisitor<Path> {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        private final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*.md");

        private List<Path> paths = new ArrayList<>();

        public List<Path> getPaths() {
            return this.paths;
        }

        private void find(Path file) {
            Path name = file.getFileName();
            if (name != null && matcher.matches(name)) {
                this.paths.add(file);
            }
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            find(file);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            logger.error("Visit file failed, file:" + file.getFileName(), exc);
            return FileVisitResult.CONTINUE;
        }

    }

    class ReadTask implements Callable<BlogData> {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        private BlogData blog = new BlogData();

        private Path file;

        private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        public ReadTask(Path file) {
            this.file = file;
        }

        @Override
        public BlogData call() throws Exception {
            List<String> allLines = Files.readAllLines(this.file, Charset.forName("UTF-8"));
            logger.trace(allLines.toString());
            StringBuilder body = new StringBuilder();
            int headerCount = 0;
            boolean headerEnd = false;
            String separator = System.getProperty("line.separator");
            for (String line : allLines) {
                if (line.startsWith("-") && !headerEnd) {
                    headerCount++;
                    continue;
                }
                if (headerCount == 1) {
                    String[] header = line.split(":");
                    blog.setValue(header[0].trim(), header[1].trim());
                    continue;
                }
                if (headerEnd) {
                    body.append(line)
                            .append(separator);
                    continue;
                }
                if (headerCount == 2) {
                    headerEnd = true;
                }
            }
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            String dateStr = file.getFileName().toString().substring(0, 10);
            blog.setCreated(df.parse(dateStr));
            blog.setLastModified(new Date(attr.lastModifiedTime().toMillis()));
            blog.setContent(convert.convert(body.toString()));
            String category=blog.getCategory();
            if("".equals(category)){
                blog.setCategory("default");
            }
            logger.debug(blog.getTitle() + " has been read.");
            logger.trace(blog.toString());
            return blog;
        }
    }

}
