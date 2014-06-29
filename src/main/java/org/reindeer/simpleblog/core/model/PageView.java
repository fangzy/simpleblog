package org.reindeer.simpleblog.core.model;

import java.util.List;

/**
 * Created by fzy on 2014/6/29.
 */
public class PageView extends AbstractView {

    private List<BlogData> blogDataList;

    private int pageTotal;

    private int pageCurrent;

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

    public int getPageCurrent() {
        return pageCurrent;
    }

    public void setPageCurrent(int pageCurrent) {
        this.pageCurrent = pageCurrent;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
