package org.reindeer.simpleblog.core.model;

import org.springframework.cglib.beans.BeanMap;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fzy on 2014/6/25.
 */
public class BlogData implements Comparable<BlogData> {

    private BeanMap beanMap = BeanMap.create(this);

    private String title;

    private String description;

    private String category;

    private String tags;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date created;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastModified;

    private String content;

    public static BlogData valueOf(Map<String, String> map) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        BlogData blogData = new BlogData();
        blogData.setTitle(map.get("title"));
        blogData.setDescription(map.get("description"));
        blogData.setCategory(map.get("category"));
        blogData.setContent(map.get("content"));
        try {
            blogData.setCreated(dateFormat.parse(map.get("created")));
            blogData.setLastModified(dateFormat.parse(map.get("lastModified")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return blogData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setValue(String key, Object value) {
        beanMap.put(key, value);
    }

    @Override
    public String toString() {
        return "BlogData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", tags=" + tags + '\'' +
                ", created=" + created +
                ", lastModified=" + lastModified +
                ", content='" + content + "...\'" +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlogData)) return false;

        BlogData blogData = (BlogData) o;

        if (!category.equals(blogData.category)) return false;
        if (!content.equals(blogData.content)) return false;
        if (!created.equals(blogData.created)) return false;
        if (description != null ? !description.equals(blogData.description) : blogData.description != null)
            return false;
        if (!lastModified.equals(blogData.lastModified)) return false;
        if (tags != null ? !tags.equals(blogData.tags) : blogData.tags != null) return false;
        if (!title.equals(blogData.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + category.hashCode();
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + created.hashCode();
        result = 31 * result + lastModified.hashCode();
        result = 31 * result + content.hashCode();
        return result;
    }

    @Override
    public int compareTo(BlogData o) {
        int result = this.getCreated().compareTo(o.getCreated());
        if (result == 0) {
            result = this.getTitle().compareTo(o.getTitle());
        }
        return result;
    }

    public Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        map.put("title", getTitle());
//        map.put("description",getDescription());
        map.put("category", getCategory());
        map.put("content", getContent());
        map.put("created", dateFormat.format(getCreated()));
        map.put("lastModified", dateFormat.format(getLastModified()));
        return map;
    }
}
