package org.reindeer.simpleblog.core.reader;

import org.reindeer.simpleblog.core.SiteConfig;
import org.reindeer.simpleblog.core.model.BlogData;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by fzy on 2014/6/25.
 */
public abstract class BlogDataReader {

    @Autowired
    protected SiteConfig config;

    protected MarkdownConvert convert;

    public void setConvert(MarkdownConvert convert) {
        this.convert = convert;
    }

    /**
     * read blog data
     *
     * @return list of blog
     */
    public abstract List<BlogData> read();

}
