package com.livejournal.uisteps.core;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASolyankin
 */
public class WebBrowserList implements BrowserList {

    private final List<Browser> browserList = new ArrayList<>();
    private int currentIndex = -1;

    @Override
    public void add(Browser browser) {
        browserList.add(browser);
        currentIndex = browserList.size() - 1;
    }

    @Override
    public void removeCurrent() {
        browserList.remove(currentIndex);
        if (currentIndex >= browserList.size()) {
            currentIndex--;
        }
    }

    @Override
    public boolean isEmpty() {
        return browserList.isEmpty();
    }

    @Override
    public boolean hasNext() {
        return currentIndex < browserList.size() - 1;
    }

    @Override
    public boolean hasPrevious() {
        return currentIndex > 0;
    }

    @Override
    public Browser getCurrent() {
        try {
            return browserList.get(currentIndex);
        } catch (IndexOutOfBoundsException ex) {
            throw new IndexOutOfBoundsException("Cannot get current browser!");
        }
    }

    @Override
    public void setCurrentByIndex(int index) {
        if (index >= 0 && index < browserList.size() - 1) {
            currentIndex = index;
        } else {
            throw new IndexOutOfBoundsException("Cannot get browser by index " + index + "!");
        }
    }

    @Override
    public boolean next() {
        if (hasNext()) {
            currentIndex++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean previous() {
        if (hasPrevious()) {
            currentIndex--;
            return true;
        } else {
            return false;
        }

    }
}
