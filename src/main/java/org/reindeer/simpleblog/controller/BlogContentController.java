package org.reindeer.simpleblog.controller;

import org.reindeer.simpleblog.core.model.BlogView;
import org.reindeer.simpleblog.core.repositories.BlogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by fzy on 2014/6/28.
 */
@Controller
@RequestMapping("/blog")
public class BlogContentController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlogRepository repository;

    @RequestMapping(value = "{title:.*}", method = RequestMethod.GET)
    public String getBlog(@PathVariable String title, Model model) {
        logger.debug("Request:" + title);
        BlogView view = repository.getBlogView(title);
        model.addAttribute("view", view);
        return "blog";
    }
}
