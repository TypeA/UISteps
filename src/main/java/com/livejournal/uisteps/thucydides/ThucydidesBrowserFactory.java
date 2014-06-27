package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.BrowserFactory;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesBrowserFactory implements BrowserFactory {

    @Override
    public Browser instantiateBrowser() {
        ThucydidesDriverFactory driverFactory = new ThucydidesDriverFactory();
        ThucydidesUIContainerFactory uiContainerFactory = new ThucydidesUIContainerFactory();
        ThucydidesBrowserActionsFactory browserActionsFactory = new ThucydidesBrowserActionsFactory();
        ThucydidesUIContainerComparator uiContainerComparator = new ThucydidesUIContainerComparator();
        return new Browser(driverFactory, uiContainerFactory, browserActionsFactory, uiContainerComparator);
    }

}
