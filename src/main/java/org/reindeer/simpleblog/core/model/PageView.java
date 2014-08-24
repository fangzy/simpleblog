package org.reindeer.simpleblog.core.model;

import java.util.List;

/**
 * Created by fzy on 2014/6/29.
 */
public class PageView extends AbstractView {

    private List<BlogData> blogDataList;

    private int pageTotal;

    private int pageNo;

    private int pageSize;

    public List<BlogData> getBlogDataList() {
        return blogDataList;
    }

    public void setBlogDataList(List<BlogData> blogDataList) {
        this.blogDataList = blogDataList;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
