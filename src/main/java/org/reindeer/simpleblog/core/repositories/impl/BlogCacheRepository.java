package org.reindeer.simpleblog.core.repositories.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.model.CategoryData;
import org.reindeer.simpleblog.core.repositories.BlogCache;
import org.reindeer.simpleblog.core.repositories.BlogRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by fzy on 2014/6/27.
 */
@Repository
public class BlogCacheRepository extends BlogRepository {

    private BlogCache cache = new BlogCache();

    @Override
    public void init(List<BlogData> blogDataList) {
        cache.init(blogDataList);
    }

    protected BlogData get(String title) {
        return cache.get(title);
    }

    @Override
    protected String[] getRandomTitles(int i) {
        return cache.getRandomTitles(i);
    }

    @Override
    protected String getPrevTitle(BlogData blogData) {
        return cache.getPrevTitle(blogData);
    }

    @Override
    protected String getNextTitle(BlogData blogData) {
        return cache.getNextTitle(blogData);
    }

    @Override
    protected TreeMap<String, MutableInt> getCategoryCount() {
        return cache.getCategoryCountMap();
    }

    @Override
    protected HashMap<String, MutableInt> getArchiveCount() {
        return cache.getArchiveCountMap();
    }

    @Override
    protected String[] getRecentTitles(int i) {
        return cache.getRecentTitles(i);
    }

    @Override
    protected List<BlogData> getSubList(int currentPos, int endPos) {
        List<BlogData> blogDataList = new ArrayList<>();
        blogDataList.addAll(cache.getBlogDataList());
        return blogDataList.subList(currentPos, endPos);
    }

    @Override
    protected int getTotalSize() {
        return cache.getBlogDataList().size();
    }

    @Override
    protected TreeMap<String, CategoryData> getCategoryDataMap() {
        return cache.getCategoryDataMap();
    }

    @Override
    protected CategoryData getCategoryData(String id) {
        return cache.getCategoryData(id);
    }

    @Override
    protected HashMap<String, CategoryData> getArchiveDataMap() {
        return cache.getArchiveDataMap();
    }

    @Override
    protected CategoryData getArchiveData(String id) {
        return cache.getArchiveData(id);
    }

}
