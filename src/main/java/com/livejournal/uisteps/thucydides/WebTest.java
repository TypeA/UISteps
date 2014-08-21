package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.UIContainerAnalizer;
import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.thucydides.Verifications.That;
import net.thucydides.core.annotations.Steps;
import net.thucydides.jbehave.ThucydidesJUnitStory;

/**
 *
 * @author ASolyankin
 */
public class WebTest extends ThucydidesJUnitStory {

    @Steps
    Browser browser;
    @Steps
    UIActions uiActions;
    @Steps
    Verifications verifications;

    public WebTest() {
        ThucydidesStepListener listener = new ThucydidesStepListener(browser);
        ThucydidesUtils.registerListener(listener);
    }

    public void openBrowser() {
        if (!browser.isOpened()) {
            ThucydidesUIContainerFactory uiContainerFactory = new ThucydidesUIContainerFactory();
            ThucydidesUIContainerComparator uiContainerComparator = new ThucydidesUIContainerComparator();
            UIContainerAnalizer uiContainerAnalizer = new UIContainerAnalizer();
            browser.init(uiContainerFactory, uiContainerComparator, uiContainerAnalizer);
            uiActions.init(browser);
            ThucydidesUtils.putToSession("#UI_ACTIONS", uiActions);
            browser.setOpened();
        }
    }

    public void openUrl(String url) {
        browser.openUrl(url);
    }

    public <T extends UIContainer> T on(T uiContainer) {
        openBrowser();
        return browser.on(uiContainer);
    }

    public <T extends UIContainer> T on(Class<T> uiContainerClass) {
        openBrowser();
        return browser.on(uiContainerClass);
    }

    public <T extends BasePage> T on(Class<T> pageClass, Url url) {
        return browser.on(pageClass, url);
    }

    public String getCurrentUrl() {
        return browser.getCurrentUrl();
    }

    public String getCurrentTitle() {
        return browser.getCurrentTitle();
    }

    public That verify() {
        return verifications.verify();
    }

    public void switchToNextWindow() {
        browser.switchToNextWindow();
    }

    public void switchToPreviousWindow() {
        browser.switchToPreviousWindow();
    }

    public void switchToDefaultWindow() {
        browser.switchToWindowByIndex(0);
    }

    public void switchToWindowByIndex(int index) {
        browser.switchToWindowByIndex(index);
    }

    public Browser getCurrentBrowser() {
        return browser;
    }

}
