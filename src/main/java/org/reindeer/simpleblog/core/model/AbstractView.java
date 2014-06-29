package org.reindeer.simpleblog.core.model;

/**
 * Created by fzy on 2014/6/28.
 */
public abstract class AbstractView {

    protected String[] randomTitles;

    public String[] getRandomTitles() {
        return randomTitles;
    }

    public void setRandomTitles(String[] randomTitles) {
        this.randomTitles = randomTitles;
    }
}
