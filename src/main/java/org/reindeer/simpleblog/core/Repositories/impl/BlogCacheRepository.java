package org.reindeer.simpleblog.core.Repositories.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.core.Repositories.BlogCache;
import org.reindeer.simpleblog.core.Repositories.BlogRepository;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.model.BlogView;
import org.reindeer.simpleblog.core.model.PageView;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by fzy on 2014/6/27.
 */
@Repository
public class BlogCacheRepository implements BlogRepository {

    private BlogCache cache = new BlogCache();

    @Override
    public void put(BlogData blogData) {
        cache.put(blogData);
    }

    @Override
    public void init(List<BlogData> blogDataList) {
        cache.init(blogDataList);
    }

    @Override
    public BlogData get(String title) {
        return cache.get(title);
    }

    @Override
    public BlogView getBlogView(String title) {
        BlogView blogView = new BlogView();
        BlogData blogData = get(title);
        if (blogData == null) {
            throw new IllegalArgumentException("Can't find any blog.");
        }
        blogView.setBlogData(blogData);
        blogView.setNextTitle(cache.getNextTitle(blogData));
        blogView.setPrevTitle(cache.getPrevTitle(blogData));
        blogView.setRandomTitles(cache.getRandomTitles(5));
        return blogView;
    }

    @Override
    public TreeMap<String, MutableInt> getCategoryCount() {
        return cache.getCategoryCountMap();
    }

    @Override
    public HashMap<String, MutableInt> getTimeCount() {
        return cache.getTimeCountMap();
    }

    @Override
    public String[] getRecentTitles(int i) {
        return cache.getRecentTitles(i);
    }

    @Override
    public PageView getPageView(int index,int pageSize) {
        Assert.isTrue(index > 0 ,"Index must be bigger than zero.");
        PageView pageView = new PageView();
        List<BlogData> blogDataList = new ArrayList<>();
        blogDataList.addAll(cache.getBlogDataList());
        Collections.reverse(blogDataList);
        int totalSize=blogDataList.size();
        int currentPos=(index-1)*pageSize;
        if (totalSize<currentPos+1){
            throw new IllegalArgumentException("Wrong page no.");
        }
        int endPos = currentPos + pageSize;
        if(totalSize<endPos){
            endPos = totalSize;
        }
        int totalPage = (int) Math.ceil(totalSize/pageSize);

        List<BlogData> subList = blogDataList.subList(currentPos,endPos);
        pageView.setBlogDataList(subList);
        pageView.setPageSize(pageSize);
        pageView.setPageCurrent(index);
        pageView.setPageTotal(totalPage);
        pageView.setRandomTitles(cache.getRandomTitles(5));
        return pageView;
    }

}
