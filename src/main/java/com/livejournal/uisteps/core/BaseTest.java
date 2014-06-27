package com.livejournal.uisteps.core;

/**
 *
 * @author Asolyankin
 */
public class BaseTest {
    
    private final TestActions actions;
    
    public BaseTest(TestActionsFactory testActionsFactory) {
        actions = testActionsFactory.instantiateTestActions();
    }
    
    public void openBrowser(String name) {
        actions.openBrowser(name);
    }
    
    public void closeCurrentBrowser() {
        actions.closeCurrentBrowser();
    }
    
    public void closeAllBrowsers() {
        actions.closeAllBrowsers();
    }
    
    public boolean isAnyBrowserIsOpened() {
        return actions.isAnyBrowserIsOpened();
    }
    
    public boolean isNextBrowserIsOpened() {
        return actions.isNextBrowserIsOpened();
    }
    
    public boolean isPreviousBrowserIsOpened() {
        return actions.isPreviousBrowserIsOpened();
    }
    
    public void switchToNextBrowser() {
        actions.switchToNextBrowser();
    }
    
    public void switchToPreviousBrowser() {
        actions.switchToPreviousBrowser();
    }
    
    public void openUrl(String url) {
        actions.openUrl(url);
    }
    
    protected Browser getCurrentBrowser() {
        return actions.getCurrentBrowser();
    }
    
    protected <T extends UIContainer> T on(Class<T> UIContainerClass) {
        return actions.on(UIContainerClass);
    }
    
    protected <T extends UIContainer> T on(T uiContainer) {
        return actions.on(uiContainer);
    }
    
    protected TestActions getActions() {
        return actions;
    }
    
    protected <T extends UIContainer> T on(Class<T> rootClass, String uiContainerClassName) {
        return actions.on(rootClass, uiContainerClassName);
    }
}
