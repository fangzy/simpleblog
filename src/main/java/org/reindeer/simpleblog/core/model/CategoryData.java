package org.reindeer.simpleblog.core.model;

import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzy on 2014/7/1.
 */
public class CategoryData {

    private String name;

    private List<String> titleList = new ArrayList<>();

    private MutableInt count = new MutableInt(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void addTitle(String title) {
        this.titleList.add(title);
    }

    public MutableInt getCount() {
        return count;
    }

    public void incCount() {
        this.count.increment();
    }
}
