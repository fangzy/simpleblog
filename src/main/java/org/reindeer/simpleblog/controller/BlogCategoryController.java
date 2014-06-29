package org.reindeer.simpleblog.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping("/category*")
    public void index(Model model) {
//        model.addAttribute("content","category");
        logger.info("visited category index");
    }

    @RequestMapping("/category/{id}")
    public void category(@PathVariable("id") String id, Model model) {
        model.addAttribute("content", "category");
        model.addAttribute("categoryId", id);
        logger.info("visited category " + id);
    }

}
