package org.reindeer.simpleblog.core.model;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzy on 2014/7/1.
 */
public class CategoryData {

    private String name;

    private List<BlogData> blogList = new ArrayList<>();

    private MutableInt count = new MutableInt(0);

    public CategoryData(String category) {
        name = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BlogData> getBlogList() {
        return blogList;
    }

    public MutableInt getCount() {
        return count;
    }

    public void increment() {
        this.count.increment();
    }

    public CategoryData addBlog(BlogData blogData) {
        blogList.add(blogData);
        increment();
        return this;
    }
}
