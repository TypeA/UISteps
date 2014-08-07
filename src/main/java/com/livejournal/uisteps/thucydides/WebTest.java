package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.UIContainerAnalizer;
import com.livejournal.uisteps.thucydides.Verifications.ExpectedResults;
import com.livejournal.uisteps.thucydides.Verifications.SingleExpectedResult;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.ManagedPages;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.BaseStepListener;
import net.thucydides.jbehave.ThucydidesJUnitStory;
import org.openqa.selenium.WebDriver;

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

    public void openBrowser() {
        ThucydidesUIContainerFactory uiContainerFactory = new ThucydidesUIContainerFactory();
        ThucydidesUIContainerComparator uiContainerComparator = new ThucydidesUIContainerComparator();
        UIContainerAnalizer uiContainerAnalizer = new UIContainerAnalizer();
        browser.init(uiContainerFactory, uiContainerComparator, uiContainerAnalizer);
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

    public SingleExpectedResult verifyExpectedResult(String description, boolean condition) {
        return verifications.verifyExpectedResult(description, condition);
    }

    public ExpectedResults verify() {
        return verifications.verify();
    }

}
