package org.reindeer.simpleblog.core.repositories;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.Constant;
import org.reindeer.simpleblog.core.model.*;
import org.reindeer.simpleblog.exception.ResourceNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by fzy on 2014/6/27.
 */
public abstract class BlogRepository {

    public abstract void init(List<BlogData> blogDataCollection);

    protected abstract BlogData get(String title);

    public BlogView getBlogView(String title) {
        BlogView blogView = new BlogView();
        BlogData blogData = get(title);
        if (blogData == null) {
            throw new ResourceNotFoundException("Can't find any blog.");
        }
        blogView.setBlogData(blogData);
        blogView.setNextTitle(getNextTitle(blogData));
        blogView.setPrevTitle(getPrevTitle(blogData));
        blogView.setRandomTitles(getRandomTitles(Constant.RANDOM_NO));
        return blogView;
    }

    protected abstract String[] getRandomTitles(int i);

    protected abstract String getPrevTitle(BlogData blogData);

    protected abstract String getNextTitle(BlogData blogData);

    protected abstract TreeMap<String, MutableInt> getCategoryCount();

    protected abstract HashMap<String, MutableInt> getArchiveCount();

    protected abstract String[] getRecentTitles(int i);

    public PageView getPageView(int index, int pageSize) {
        PageView pageView = new PageView();
        int totalSize = getTotalSize();
        int currentPos = (index - 1) * pageSize;
        if (totalSize < currentPos + 1) {
            throw new ResourceNotFoundException("Wrong page no.");
        }
        int endPos = currentPos + pageSize;
        if (totalSize < endPos) {
            endPos = totalSize;
        }
        int totalPage = (int) Math.ceil(totalSize / pageSize) + (totalSize % pageSize == 0 ? 0 : 1);

        List<BlogData> blogDataList = getSubList(currentPos, endPos);
        pageView.setBlogDataList(blogDataList);
        pageView.setPageSize(pageSize);
        pageView.setPageCurrent(index);
        pageView.setPageTotal(totalPage);
        pageView.setRandomTitles(getRandomTitles(Constant.RANDOM_NO));
        return pageView;
    }

    public CategoryView getCategoryView(String id) {
        CategoryData categoryData = getCategoryData(id);
        if (categoryData == null) {
            throw new ResourceNotFoundException("Can't find category");
        }
        CategoryView view = new CategoryView();
        view.addCategory(categoryData);
        view.setRandomTitles(getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    public CategoryView getCategoryView() {
        CategoryView view = new CategoryView();
        TreeMap<String, CategoryData> categoryDataMap = getCategoryDataMap();
        for (CategoryData categoryData : categoryDataMap.values()) {
            view.addCategory(categoryData);
        }
        view.setRandomTitles(getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    public CategoryView getArchiveView(String dateStr) {
        CategoryData categoryData = getArchiveData(dateStr);
        if (categoryData == null) {
            throw new ResourceNotFoundException("Can't find archive");
        }
        CategoryView view = new CategoryView();
        view.addCategory(categoryData);
        view.setRandomTitles(getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    public CategoryView getArchiveView() {
        CategoryView view = new CategoryView();
        HashMap<String, CategoryData> archiveDataMap = getArchiveDataMap();
        for (CategoryData categoryData : archiveDataMap.values()) {
            view.addCategory(categoryData);
        }
        view.setRandomTitles(getRandomTitles(Constant.RANDOM_NO));
        return view;
    }

    protected abstract List<BlogData> getSubList(int currentPos, int endPos);

    protected abstract int getTotalSize();

    protected abstract TreeMap<String, CategoryData> getCategoryDataMap();

    protected abstract CategoryData getCategoryData(String id);

    protected abstract HashMap<String, CategoryData> getArchiveDataMap();

    protected abstract CategoryData getArchiveData(String id);

    public Map<String, Object> getFreeMarkerVariables() {
        Map<String, Object> map = new HashMap<>();
        map.put("categoryCount", getCategoryCount());
        map.put("archiveCount", getArchiveCount());
        map.put("recentTitles", getRecentTitles(10));
        return map;
    }

    public boolean isEmpty() {
        return true;
    }
}
