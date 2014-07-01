package org.reindeer.simpleblog.core.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fzy on 2014/7/1.
 */
public class CategoryView extends AbstractView {

    private List<CategoryData> categoryDataList = new ArrayList<>();

    public List<CategoryData> getCategoryDataList() {
        return categoryDataList;
    }

    public void addCategory(CategoryData categoryData) {
        this.categoryDataList.add(categoryData);
    }
}
