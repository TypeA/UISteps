package com.livejournal.uisteps.thucydides;

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
public class WebTest {

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
        openBrowser();
        return browser.open(page);
    }

    public <T extends UIContainer> T onOpened(T uiContainer) {
        openBrowser();
        return browser.onOpened(uiContainer);
    }

    public void openUrl(String url) {
        openBrowser();
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

    public Browser getCurrentBrowser() {
        return browser;
    }

}
