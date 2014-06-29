package org.reindeer.simpleblog.core.reader.impl;

import org.markdown4j.Markdown4jProcessor;
import org.reindeer.simpleblog.core.reader.MarkdownConvert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by fzy on 2014/6/27.
 */
public class Markdown4jConvert extends MarkdownConvert {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private Markdown4jProcessor processor = new Markdown4jProcessor();

    @Override
    public String convert(String input) {
        String out = "";
        try {
            out = processor.process(input);
        } catch (Exception e) {
            logger.error("Convert failed. Text:" + input, e);
        }
        return out;
    }
}
