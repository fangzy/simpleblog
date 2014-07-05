package org.reindeer.simpleblog.core.Repositories.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.Constant;
import org.reindeer.simpleblog.core.Repositories.BlogCache;
import org.reindeer.simpleblog.core.Repositories.BlogRepository;
import org.reindeer.simpleblog.core.model.*;
import org.reindeer.simpleblog.exception.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

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
            throw new ResourceNotFoundException("Can't find any blog.");
        }
        blogView.setBlogData(blogData);
        blogView.setNextTitle(cache.getNextTitle(blogData));
        blogView.setPrevTitle(cache.getPrevTitle(blogData));
        blogView.setRandomTitles(cache.getRandomTitles(Constant.RANDOM_NO));
        return blogView;
    }

    @Override
    public TreeMap<String, MutableInt> getCategoryCount() {
        return cache.getCategoryCountMap();
    }

    @Override
    public HashMap<String, MutableInt> getArchiveCount() {
        return cache.getArchiveCountMap();
    }

    @Override
    public String[] getRecentTitles(int i) {
        return cache.getRecentTitles(i);
    }

    @Override
    public PageView getPageView(int index,int pageSize) {
        PageView pageView = new PageView();
        List<BlogData> blogDataList = new ArrayList<>();
        blogDataList.addAll(cache.getBlogDataList());
        int totalSize=blogDataList.size();
        int currentPos=(index-1)*pageSize;
        if (totalSize<currentPos+1){
            throw new ResourceNotFoundException("Wrong page no.");
        }
        int endPos = currentPos + pageSize;
        if(totalSize<endPos){
            endPos = totalSize;
        }
        int totalPage = (int) Math.ceil(totalSize / pageSize) + 1;

        List<BlogData> subList = blogDataList.subList(currentPos,endPos);
        pageView.setBlogDataList(subList);
        pageView.setPageSize(pageSize);
        pageView.setPageCurrent(index);
        pageView.setPageTotal(totalPage);
        pageView.setRandomTitles(cache.getRandomTitles(Constant.RANDOM_NO));
        return pageView;
    }

    @Override
    public CategoryView getCategoryView(String id) {
        CategoryData categoryData = cache.getCategoryData(id);
        if (categoryData == null) {
            throw new ResourceNotFoundException("Can't find category");
        }
        CategoryView view = new CategoryView();
        view.addCategory(categoryData);
        view.setRandomTitles(cache.getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    @Override
    public CategoryView getCategoryView() {
        CategoryView view = new CategoryView();
        TreeMap<String, CategoryData> categoryDataMap = cache.getCategoryDataMap();
        for (CategoryData categoryData : categoryDataMap.values()) {
            view.addCategory(categoryData);
        }
        view.setRandomTitles(cache.getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    @Override
    public CategoryView getArchiveView(String dateStr) {
        CategoryData categoryData = cache.getArchiveData(dateStr);
        if (categoryData == null) {
            throw new ResourceNotFoundException("Can't find archive");
        }
        CategoryView view = new CategoryView();
        view.addCategory(categoryData);
        view.setRandomTitles(cache.getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    @Override
    public CategoryView getArchiveView() {
        CategoryView view = new CategoryView();
        HashMap<String, CategoryData> archiveDataMap = cache.getArchiveDataMap();
        for (CategoryData categoryData : archiveDataMap.values()) {
            view.addCategory(categoryData);
        }
        view.setRandomTitles(cache.getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

}
