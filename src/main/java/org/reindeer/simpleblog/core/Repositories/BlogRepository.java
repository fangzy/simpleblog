package org.reindeer.simpleblog.core.Repositories;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.model.BlogView;
import org.reindeer.simpleblog.core.model.CategoryView;
import org.reindeer.simpleblog.core.model.PageView;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by fzy on 2014/6/27.
 */
public interface BlogRepository {

    void put(BlogData blogData);

    void init(List<BlogData> blogDataCollection);

    BlogData get(String title);

    BlogView getBlogView(String title);

    TreeMap<String,MutableInt> getCategoryCount();

    HashMap<String,MutableInt> getTimeCount();

    String[] getRecentTitles(int i);

    PageView getPageView(int index,int pageSize);

    CategoryView getCategoryView(String id);
}
