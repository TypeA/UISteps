package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BrowserStorage;
import com.livejournal.uisteps.core.TestActions;
import com.livejournal.uisteps.core.WebBrowserList;
import net.thucydides.core.annotations.Step;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesTestActions extends TestActions {

    public ThucydidesTestActions() {
        super(new BrowserStorage(new WebBrowserList(), new ThucydidesBrowserFactory()));
    }

    @Step
    @Override
    public void openUrl(String url) {
        super.openUrl(url);
    }

    @Step
    @Override
    public void switchToPreviousBrowser() {
        super.switchToPreviousBrowser();
    }

    @Step
    @Override
    public void switchToNextBrowser() {
        super.switchToNextBrowser();
    }

    @Step
    @Override
    public void openBrowser(String name) {
        super.openBrowser(name);
    }

}
