package org.reindeer.simpleblog.controller;

import org.reindeer.simpleblog.core.Repositories.BlogRepository;
import org.reindeer.simpleblog.core.model.CategoryView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by fzy on 2014/6/23.
 */
@Controller
public class BlogArchiveController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BlogRepository repository;

    @RequestMapping("/archives*")
    public String getIndex(Model model) {
        logger.debug("visited archives index");
        CategoryView view = repository.getArchiveView();
        model.addAttribute("view", view);
        return "category";
    }

    @RequestMapping("/archives/{year}/{month}")
    public String getArchive(@PathVariable("year") int year, @PathVariable("month") int month, Model model) {
        logger.debug("visited archives " + year + "-" + month);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("MMMMM yyyy", Locale.ENGLISH);
        CategoryView view = repository.getArchiveView(dateFormat.format(date));
        model.addAttribute("view", view);
        return "category";
    }

}
