package org.reindeer.simpleblog.core.Repositories.impl;

import org.apache.commons.lang3.mutable.MutableInt;
import org.reindeer.simpleblog.core.Repositories.BlogRepository;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.model.CategoryData;
import org.reindeer.simpleblog.core.util.JedisProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by fzy on 2014/6/27.
 */
@Repository
public class BlogRedisRepository extends BlogRepository {

    private static final String BLOG_LIST_KEY = "blog:list:%s";
    private static final String BLOG_CATEGORY_KEY = "blog:category:%s";
    private static final String BLOG_TIME_KEY = "blog:time:%s";
    private static final String BLOG_DATA_KEY = "blog:data:%s";
    private static final String BLOG_TAG_KEY = "blog:tag:%s";
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void init(List<BlogData> blogDataList) {
        Pipeline pipeline = JedisProxy.create().pipelined();
        pipeline.flushDB();
        DateFormat displayFormat = new SimpleDateFormat("MMMMM yyyy", Locale.ENGLISH);
        for (BlogData blogData : blogDataList) {
            Date time = blogData.getCreated();
            String timeStr = displayFormat.format(time);
            Date yearMonth = null;
            try {
                yearMonth = displayFormat.parse(timeStr);
            } catch (ParseException e) {
                logger.error("An unexpected error occurred.", e);
            }
            pipeline.zadd(String.format(BLOG_LIST_KEY, "all"), time.getTime(), blogData.getTitle());
            pipeline.sadd(String.format(BLOG_LIST_KEY, "category"), blogData.getCategory());
            pipeline.zadd(String.format(BLOG_LIST_KEY, "time"), yearMonth != null ? yearMonth.getTime() : 0, timeStr);
            pipeline.zadd(String.format(BLOG_CATEGORY_KEY, blogData.getCategory()), time.getTime(), blogData.getTitle());
            pipeline.zadd(String.format(BLOG_TIME_KEY, timeStr), time.getTime(), blogData.getTitle());
            pipeline.hmset(String.format(BLOG_DATA_KEY, blogData.getTitle()), blogData.toMap());
        }
        pipeline.sync();
    }

    @Override
    protected BlogData get(String title) {
        Jedis jedis = JedisProxy.create();
        Map<String, String> map = jedis.hgetAll(String.format(BLOG_DATA_KEY, title));
        return BlogData.valueOf(map);
    }

    @Override
    protected String[] getRandomTitles(int i) {
        Jedis jedis = JedisProxy.create();
        List<String> randomTitles = new ArrayList<>();
        String[] randomArray = new String[i];
        String listKey = String.format(BLOG_LIST_KEY, "all");
        int size = jedis.zcard(listKey).intValue();
        if (i < size) {
            for (int m = 0; m < i; m++) {
                int num = ThreadLocalRandom.current().nextInt(size);
                String random = jedis.zrevrange(listKey, num, num + 1).iterator().next();
                while (randomTitles.contains(random)) {
                    num = ThreadLocalRandom.current().nextInt(size);
                    random = jedis.zrevrange(listKey, num, num + 1).iterator().next();
                }
                randomTitles.add(random);
            }
            randomArray = randomTitles.toArray(randomArray);
        } else {
            randomArray = jedis.zrevrange(listKey, 0, -1).toArray(new String[size]);
        }
        return randomArray;
    }

    @Override
    protected String getPrevTitle(BlogData blogData) {
        Jedis jedis = JedisProxy.create();
        String listKey = String.format(BLOG_LIST_KEY, "all");
        String prevTitle = null;
        int pos = jedis.zrevrank(listKey, blogData.getTitle()).intValue();
        if (pos > 0) {
            prevTitle = jedis.zrevrange(listKey, pos - 1, pos).iterator().next();
        }
        return prevTitle;
    }

    @Override
    protected String getNextTitle(BlogData blogData) {
        Jedis jedis = JedisProxy.create();
        String listKey = String.format(BLOG_LIST_KEY, "all");
        String nextTitle = null;
        int pos = jedis.zrevrank(listKey, blogData.getTitle()).intValue();
        if (pos < jedis.zcard(listKey) - 1) {
            nextTitle = jedis.zrevrange(listKey, pos + 1, pos + 2).iterator().next();
        }
        return nextTitle;
    }

    @Override
    protected TreeMap<String, MutableInt> getCategoryCount() {
        Jedis jedis = JedisProxy.create();
        TreeMap<String, MutableInt> categoryCountMap = new TreeMap<>();
        Set<String> categorySet = jedis.smembers(String.format(BLOG_LIST_KEY, "category"));
        for (String category : categorySet) {
            categoryCountMap.put(category, new MutableInt(jedis.zcard(String.format(BLOG_CATEGORY_KEY, category)).intValue()));
        }
        return categoryCountMap;
    }

    @Override
    protected HashMap<String, MutableInt> getArchiveCount() {
        Jedis jedis = JedisProxy.create();
        HashMap<String, MutableInt> archiveCountMap = new LinkedHashMap<>();
        Set<String> archiveSet = jedis.zrevrange(String.format(BLOG_LIST_KEY, "time"), 0, -1);
        for (String archive : archiveSet) {
            archiveCountMap.put(archive, new MutableInt(jedis.zcard(String.format(BLOG_TIME_KEY, archive)).intValue()));
        }
        return archiveCountMap;
    }

    @Override
    protected String[] getRecentTitles(int i) {
        Jedis jedis = JedisProxy.create();
        String listKey = String.format(BLOG_LIST_KEY, "all");
        int size = jedis.zcard(listKey).intValue();
        List<String> subList = new ArrayList<>();
        if (i < size) {
            subList.addAll(jedis.zrevrange(listKey, 0, i));
        } else {
            subList.addAll(jedis.zrevrange(listKey, 0, -1));
            i = size;
        }
        return subList.toArray(new String[i]);
    }

    @Override
    protected List<BlogData> getSubList(int currentPos, int endPos) {
        Jedis jedis = JedisProxy.create();
        String listKey = String.format(BLOG_LIST_KEY, "all");
        List<BlogData> blogDataList = new ArrayList<>();
        Set<String> blogTitleList = jedis.zrevrange(listKey, currentPos, endPos - 1);
        for (String title : blogTitleList) {
            blogDataList.add(get(title));
        }
        return blogDataList;
    }

    @Override
    protected int getTotalSize() {
        Jedis jedis = JedisProxy.create();
        String listKey = String.format(BLOG_LIST_KEY, "all");
        return jedis.zcard(listKey).intValue();
    }

    @Override
    protected TreeMap<String, CategoryData> getCategoryDataMap() {
        Jedis jedis = JedisProxy.create();
        Set<String> categories = jedis.smembers(String.format(BLOG_LIST_KEY, "category"));
        TreeMap<String, CategoryData> categoryDataMap = new TreeMap<>();
        for (String category : categories) {
            categoryDataMap.put(category, getCategoryData(category));
        }
        return categoryDataMap;
    }

    @Override
    protected CategoryData getCategoryData(String id) {
        Jedis jedis = JedisProxy.create();
        CategoryData categoryData = new CategoryData(id);
        Set<String> titles = jedis.zrevrange(String.format(BLOG_CATEGORY_KEY, id), 0, -1);
        for (String title : titles) {
            categoryData.addBlog(get(title));
        }
        return categoryData;
    }

    @Override
    protected HashMap<String, CategoryData> getArchiveDataMap() {
        Jedis jedis = JedisProxy.create();
        HashMap<String, CategoryData> archiveDataMap = new LinkedHashMap<>();
        Set<String> archives = jedis.zrevrange(String.format(BLOG_LIST_KEY, "time"), 0, -1);
        for (String archive : archives) {
            archiveDataMap.put(archive, getArchiveData(archive));
        }
        return archiveDataMap;
    }

    @Override
    protected CategoryData getArchiveData(String id) {
        Jedis jedis = JedisProxy.create();
        CategoryData categoryData = new CategoryData(id);
        Set<String> titles = jedis.zrevrange(String.format(BLOG_TIME_KEY, id), 0, -1);
        for (String title : titles) {
            categoryData.addBlog(get(title));
        }
        return categoryData;
    }

    @Override
    public Map<String, Object> getFreeMarkerVariables() {
        Map<String, Object> map = new HashMap<>();
        map.put("categoryCount", getCategoryCount());
        map.put("archiveCount", getArchiveCount());
        map.put("recentTitles", getRecentTitles(10));
        return map;
    }

    @Override
    public boolean isEmpty() {
        Jedis jedis = JedisProxy.create();
        return jedis.zcard(String.format(BLOG_LIST_KEY, "all")) == 0;
    }

}
