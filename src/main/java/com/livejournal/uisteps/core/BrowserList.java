package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public interface BrowserList {

    void add(Browser browser);

    void removeCurrent();

    boolean isEmpty();

    boolean hasNext();

    boolean hasPrevious();

    Browser getCurrent();

    void setCurrentByIndex(int index);

    boolean next();

    boolean previous();
}
