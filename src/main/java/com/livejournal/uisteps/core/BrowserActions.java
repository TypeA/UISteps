package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public class BrowserActions {

    private Browser browser;

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public <T extends BasePage> T open(T page) {
        browser.setCurrent(page);
        openUrl(page.getUrl());
        page.initialize(browser.getDriver());
        return page;
    }

    public <T extends UIContainer> T onOpened(T uiContainer) {
        if (!browser.isCurrent(uiContainer)) {
            browser.setCurrent(uiContainer);
            uiContainer.initialize(browser.getDriver());
        }
        return uiContainer;
    }

    public void close() {
        browser.getDriver().quit();
    }

    public void openUrl(String url) {
        browser.getDriver().get(url);
    }

    public void openUrl(Url url) {
        String urlString = url.toString();
        browser.getDriver().get(urlString);
    }

    public Browser getBrowser() {
        return browser;
    }

}
