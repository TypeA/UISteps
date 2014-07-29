package com.livejournal.uisteps.thucydides.tests;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.UIContainerAnalizer;
import com.livejournal.uisteps.thucydides.ThucydidesUIContainerComparator;
import com.livejournal.uisteps.thucydides.ThucydidesUIContainerFactory;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.UIActions;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class SimpleTest {

    @Managed
    WebDriver driver;

    @ManagedPages
    Pages pages;

    @Steps
    Browser browser;
    @Steps
    UIActions uiActions;

    public void openBrowser() {
        ThucydidesUIContainerFactory uiContainerFactory = new ThucydidesUIContainerFactory();
        ThucydidesUIContainerComparator uiContainerComparator = new ThucydidesUIContainerComparator();
        UIContainerAnalizer uiContainerAnalizer = new UIContainerAnalizer();
        browser.init(driver, uiContainerFactory, uiContainerComparator, uiContainerAnalizer);
        uiActions.init(browser);
        ThucydidesUtils.putToSession("#UI_ACTIONS", uiActions);

    }

    public <T extends BasePage> T open(T page) {
        return browser.open(page);
    }

    public <T extends UIContainer> T onOpened(T uiContainer) {
        return browser.onOpened(uiContainer);
    }

    public void openUrl(String url) {
        browser.openUrl(url);
    }

    public <T extends UIContainer> T on(T uiContainer) {
        return browser.on(uiContainer);
    }

    public <T extends UIContainer> T on(Class<T> uiContainerClass) {
        return browser.on(uiContainerClass);
    }

    public Browser getCurrentBrowser() {
        return browser;
    }

}
