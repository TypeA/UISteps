package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public class BrowserStorage {

    private final BrowserList browserList;
    private final BrowserFactory browserFactory;

    public BrowserStorage(BrowserList browserList, BrowserFactory browserFactory) {
        this.browserList = browserList;
        this.browserFactory = browserFactory;
    }

    public Browser open(String browserName) {
        Browser browser = browserFactory.instantiateBrowser();
        browser.setName(browserName);
        browserList.add(browser);
        return browser;
    }

    public void closeCurrent() {
        getCurrent().close();
        browserList.removeCurrent();
    }

    public void closeAll() {
        while (!browserList.isEmpty()) {
            closeCurrent();
        }
    }

    public boolean isEmpty() {
        return browserList.isEmpty();
    }

    public boolean hasNext() {
        return browserList.hasNext();
    }

    public boolean hasPrevious() {
        return browserList.hasPrevious();
    }

    public Browser getCurrent() {
        return browserList.getCurrent();
    }

    public Browser getByIndex(int index) {
        browserList.setCurrentByIndex(index);
        return getCurrent();
    }

    public void next() {
        if (!browserList.next()) {
            throw new IndexOutOfBoundsException("Cannot get next browser!");
        }
    }

    public void previous() {
        if (!browserList.previous()) {
            throw new IndexOutOfBoundsException("Cannot get previous browser!");
        }
    }

}
