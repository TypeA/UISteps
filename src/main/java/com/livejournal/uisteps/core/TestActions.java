package com.livejournal.uisteps.core;

/**
 *
 * @author Asolyankin
 */
public class TestActions {

    private final BrowserStorage browserStorage;

    public TestActions(BrowserStorage browserStorage) {
        this.browserStorage = browserStorage;
    }

    public void openBrowser(String name) {
        browserStorage.open(name);
    }

    public void closeCurrentBrowser() {
        browserStorage.closeCurrent();
    }

    public void closeAllBrowsers() {
        browserStorage.closeAll();
    }

    public boolean isAnyBrowserIsOpened() {
        return browserStorage.isEmpty();
    }

    public boolean isNextBrowserIsOpened() {
        return browserStorage.hasNext();
    }

    public boolean isPreviousBrowserIsOpened() {
        return browserStorage.hasPrevious();
    }

    public void switchToNextBrowser() {
        browserStorage.next();
    }

    public void switchToPreviousBrowser() {
        browserStorage.previous();
    }

    public void openUrl(String url) {
        getCurrentBrowser().openUrl(url);
    }

    public Browser getCurrentBrowser() {
        return browserStorage.getCurrent();
    }

    public <T extends UIContainer> T on(Class<T> UIContainerClass) {
        return getCurrentBrowser().on(UIContainerClass);
    }

    public <T extends UIContainer> T on(T uiContainer) {
        return getCurrentBrowser().on(uiContainer);
    }

    public <T extends UIContainer> T on(Class<T> rootClass, String uiContainerClassName) {
        return getCurrentBrowser().on(rootClass, uiContainerClassName);
    }
}
