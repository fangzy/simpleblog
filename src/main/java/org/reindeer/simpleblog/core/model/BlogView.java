package org.reindeer.simpleblog.core.model;

import java.util.Arrays;

/**
 * Created by fzy on 2014/6/28.
 */
public class BlogView extends AbstractView {

    private BlogData blogData;

    private String prevTitle;

    private String nextTitle;

    private String[] relatedTitles;

    public BlogData getBlogData() {
        return blogData;
    }

    public void setBlogData(BlogData blogData) {
        this.blogData = blogData;
    }

    public String getPrevTitle() {
        return prevTitle;
    }

    public void setPrevTitle(String prevTitle) {
        this.prevTitle = prevTitle;
    }

    public String getNextTitle() {
        return nextTitle;
    }

    public void setNextTitle(String nextTitle) {
        this.nextTitle = nextTitle;
    }

    public String[] getRelatedTitles() {
        return relatedTitles;
    }

    public void setRelatedTitles(String[] relatedTitles) {
        this.relatedTitles = relatedTitles;
    }

    @Override
    public String toString() {
        return "BlogView{" +
                "blogData=" + blogData +
                ", prevTitle='" + prevTitle + '\'' +
                ", nextTitle='" + nextTitle + '\'' +
                ", relatedTitles=" + Arrays.toString(relatedTitles) +
                ", randomTitles=" + Arrays.toString(randomTitles) +
                '}';
    }
}
