package org.reindeer.simpleblog.controller;

import org.reindeer.simpleblog.core.model.CategoryView;
import org.reindeer.simpleblog.core.repositories.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by fzy on 2014/6/23.
 */
@Controller
public class BlogCategoryController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlogRepository repository;

    @RequestMapping("/category*")
    public void getIndex(Model model) {
        logger.debug("visited category index");
        CategoryView view = repository.getCategoryView();
        model.addAttribute("view", view);
    }

    @RequestMapping("/category/{id}")
    public String getCategory(@PathVariable("id") String id, Model model) {
        logger.debug("visited category " + id);
        CategoryView view = repository.getCategoryView(id);
        model.addAttribute("view", view);
        return "category";
    }

}
