package org.reindeer.simpleblog.core.Repositories;

import freemarker.template.Configuration;
import freemarker.template.TemplateModelException;
import org.reindeer.simpleblog.core.model.BlogData;
import org.reindeer.simpleblog.core.model.BlogView;
import org.reindeer.simpleblog.core.model.CategoryView;
import org.reindeer.simpleblog.core.model.PageView;

import java.util.List;

/**
 * Created by fzy on 2014/6/27.
 */
public interface BlogRepository {

    void init(List<BlogData> blogDataCollection);

    BlogView getBlogView(String title);

    PageView getPageView(int index, int pageSize);

    CategoryView getCategoryView(String id);

    CategoryView getCategoryView();

    CategoryView getArchiveView(String format);

    CategoryView getArchiveView();

    void setFreeMarkerVariables(Configuration configuration) throws TemplateModelException;
}
