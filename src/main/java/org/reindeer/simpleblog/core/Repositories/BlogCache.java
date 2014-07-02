package org.reindeer.simpleblog.core.Repositories;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.model.CategoryData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fzy on 2014/6/29.
 */
public class BlogCache {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private List<BlogData> blogDataList = new ArrayList<>();

    private Map<String, BlogData> blogDataMap = new HashMap<>();

    private TreeMap<String, CategoryData> categoryDataMap = new TreeMap<>();

    private TreeMap<String, MutableInt> categoryCountMap = new TreeMap<>();

    private HashMap<String, CategoryData> archiveDataMap = new LinkedHashMap<>();

    private HashMap<String, MutableInt> archiveCountMap = new LinkedHashMap<>();

    private Comparator<BlogData> blogDataComparator = new BlogDataComparator();

    public void put(BlogData t) {
        blogDataMap.put(t.getTitle(), t);
        blogDataList.add(t);
        Collections.sort(blogDataList, blogDataComparator);
    }

    /**
     * 初始化缓存
     * <ul>
     * <li>存入所有数据并排序</li>
     * <li>生成分类计数,时间计数</li>
     * </ul>
     *
     * @param blogDataList blog数据列表
     */
    public void init(List<BlogData> blogDataList) {
        this.blogDataList = blogDataList;
        Collections.sort(this.blogDataList, blogDataComparator);

        //得到分类计数器,时间计数器
        DateFormat displayFormat = new SimpleDateFormat("MMMMM yyyy", Locale.ENGLISH);
        for (BlogData blogData : this.blogDataList) {
            blogDataMap.put(blogData.getTitle(), blogData);
            String category = blogData.getCategory();
            Date time = blogData.getCreated();
            String timeStr = displayFormat.format(time);
            MutableInt categoryCount = categoryCountMap.get(category);
            MutableInt archiveCount = archiveCountMap.get(timeStr);
            CategoryData categoryData = categoryDataMap.get(category);
            CategoryData archiveData = archiveDataMap.get(timeStr);
            if (categoryCount == null) {
                categoryCountMap.put(category, new MutableInt(1));
            } else {
                categoryCount.increment();
            }
            if (archiveCount == null) {
                archiveCountMap.put(timeStr, new MutableInt(1));
            } else {
                archiveCount.increment();
            }
            if (categoryData == null) {
                categoryDataMap.put(category, new CategoryData(category).addBlog(blogData));
            } else {
                categoryData.addBlog(blogData);
            }
            if (archiveData == null) {
                archiveDataMap.put(timeStr, new CategoryData(timeStr).addBlog(blogData));
            } else {
                archiveData.addBlog(blogData);
            }
        }

    }

    public BlogData get(String title) {
        return blogDataMap.get(title);
    }

    public List<BlogData> getBlogDataList() {
        return blogDataList;
    }

    public String getNextTitle(BlogData blogData) {
        String nextTitle = null;
        int pos = Collections.binarySearch(blogDataList, blogData, blogDataComparator);
        if (pos < blogDataList.size() - 1) {
            nextTitle = blogDataList.get(pos + 1).getTitle();
        }
        return nextTitle;
    }

    public String getPrevTitle(BlogData blogData) {
        String prevTitle = null;
        int pos = Collections.binarySearch(blogDataList, blogData, blogDataComparator);
        if (pos > 0) {
            prevTitle = blogDataList.get(pos - 1).getTitle();
        }
        return prevTitle;
    }

    public String[] getRandomTitles(int i) {
        List<String> randomTitles = new ArrayList<>();
        String[] randomArray = new String[i];
        int size = blogDataList.size();
        if (i < size) {
            for (int m = 0; m < i; m++) {
                String random = blogDataList.get(ThreadLocalRandom.current().nextInt(size)).getTitle();
                while (randomTitles.contains(random)) {
                    random = blogDataList.get(ThreadLocalRandom.current().nextInt(size)).getTitle();
                }
                randomTitles.add(random);
            }
            randomArray = randomTitles.toArray(randomArray);
        } else {
            blogDataMap.keySet().toArray(randomArray);
        }
        return randomArray;
    }

    public String[] getRecentTitles(int i) {
        String[] recentArray = new String[i];
        int size = blogDataList.size();
        List<BlogData> subList = new ArrayList<>();
        if (i < size) {
            subList.addAll(blogDataList.subList(0, i));
        } else {
            subList.addAll(blogDataList);
        }
        for (int m = 0; m < i; m++) {
            recentArray[m] = subList.get(m).getTitle();
        }
        return recentArray;
    }

    public TreeMap<String, CategoryData> getCategoryDataMap() {
        return categoryDataMap;
    }

    public CategoryData getCategoryDataMap(String name) {
        return categoryDataMap.get(name);
    }

    public TreeMap<String, MutableInt> getCategoryCountMap() {
        return categoryCountMap;
    }

    public HashMap<String, CategoryData> getArchiveDataMap() {
        return archiveDataMap;
    }

    public CategoryData getArchiveDataMap(String dateStr) {
        return archiveDataMap.get(dateStr);
    }

    public HashMap<String, MutableInt> getArchiveCountMap() {
        return archiveCountMap;
    }

    class BlogDataComparator implements Comparator<BlogData> {

        @Override
        public int compare(BlogData o1, BlogData o2) {
            int result = o1.getCreated().compareTo(o2.getCreated());
            if (result == -1) {
                result = 1;
            } else if (result == 1) {
                result = -1;
            } else {
                result = o1.getTitle().compareTo(o2.getTitle());
            }
            return result;
        }
    }
}
