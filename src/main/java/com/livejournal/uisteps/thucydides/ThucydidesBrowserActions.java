package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.BrowserActions;
import com.livejournal.uisteps.core.UIContainer;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesBrowserActions extends BrowserActions {

    @Step
    @Override
    public <T extends UIContainer> T onOpened(T uiContainer) {
        return super.onOpened(uiContainer);
    }

    @Step
    @Override
    public <T extends BasePage> T open(T page) {
        return super.open(page);
    }

    @Override
    public void close() {
        Browser browser = getBrowser();
        WebDriver driver = browser.getDriver();
        ThucydidesUtils.resetDriver(driver);
    }

}
