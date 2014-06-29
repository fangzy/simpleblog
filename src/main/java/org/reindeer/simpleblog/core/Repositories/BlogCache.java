package org.reindeer.simpleblog.core.Repositories;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.core.model.BlogData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fzy on 2014/6/29.
 */
public class BlogCache {

    private List<BlogData> blogDataList = new ArrayList<>();

    private Map<String, BlogData> blogDataMap = new HashMap<>();

    private TreeMap<String, MutableInt> categoryCountMap = new TreeMap<>();

    private HashMap<String, MutableInt> timeCountMap = new LinkedHashMap<>();

    public void put(BlogData t) {
        blogDataMap.put(t.getTitle(), t);
        blogDataList.add(t);
        Collections.sort(blogDataList);
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

        //得到分类计数器,时间计数器
        TreeMap<Date, MutableInt> timeCountMap = new TreeMap<>();
        for (BlogData blogData : this.blogDataList) {
            blogDataMap.put(blogData.getTitle(), blogData);
            String category = blogData.getCategory();
            Date time = blogData.getCreated();
            MutableInt categoryCount = categoryCountMap.get(category);
            MutableInt timeCount = timeCountMap.get(time);
            if (categoryCount == null) {
                categoryCountMap.put(category, new MutableInt(1));
            } else {
                categoryCount.increment();
            }
            if (timeCount == null) {
                timeCountMap.put(time, new MutableInt(1));
            } else {
                timeCount.increment();
            }
        }
        DateFormat dateFormat = new SimpleDateFormat("MMMMM yyyy", Locale.ENGLISH);
        for (Map.Entry<Date, MutableInt> entry : timeCountMap.entrySet()) {
            this.timeCountMap.put(dateFormat.format(entry.getKey()), entry.getValue());
        }

        Collections.sort(this.blogDataList);
    }

    public BlogData get(String title) {
        return blogDataMap.get(title);
    }

    public List<BlogData> getBlogDataList() {
        return blogDataList;
    }

    public String getNextTitle(BlogData blogData) {
        String nextTitle = null;
        int pos = Collections.binarySearch(blogDataList, blogData);
        if (pos < blogDataList.size() - 1) {
            nextTitle = blogDataList.get(pos + 1).getTitle();
        }
        return nextTitle;
    }

    public String getPrevTitle(BlogData blogData) {
        String prevTitle = null;
        int pos = Collections.binarySearch(blogDataList, blogData);
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

    public TreeMap<String, MutableInt> getCategoryCountMap() {
        return categoryCountMap;
    }

    public HashMap<String, MutableInt> getTimeCountMap() {
        return timeCountMap;
    }

    public String[] getRecentTitles(int i) {
        String[] recentArray = new String[i];
        int size = blogDataList.size();
        List<BlogData> subList = new ArrayList<>();
        if (i < size) {
            subList.addAll(blogDataList.subList(size - i, size));
        } else {
            subList.addAll(blogDataList);
        }
        Collections.reverse(subList);
        for (int m = 0; m < i; m++) {
            recentArray[m] = subList.get(m).getTitle();
        }
        return recentArray;
    }
}
