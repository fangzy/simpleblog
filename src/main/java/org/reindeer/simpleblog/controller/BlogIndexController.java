package org.reindeer.simpleblog.controller;

import org.reindeer.simpleblog.Constant;
import org.reindeer.simpleblog.core.Repositories.BlogRepository;
import org.reindeer.simpleblog.core.model.PageView;
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
public class BlogIndexController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlogRepository repository;

    @RequestMapping(value = {"/", "index*"})
    public String getIndex(Model model) {
        logger.debug("visited index");
        PageView view = repository.getPageView(1, Constant.PAGE_SIZE);
        model.addAttribute("view", view);
        return "home";
    }

    @RequestMapping(value = "/page/{id}")
    public String getPage(@PathVariable int id, Model model) {
        logger.debug("visited page:" + id);
        PageView view = repository.getPageView(id, Constant.PAGE_SIZE);
        model.addAttribute("view", view);
        return "home";
    }

}
