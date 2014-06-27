package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public abstract class BrowserActionsFactory {

    public BrowserActions getBrowserActions(Browser browser) {
        BrowserActions actions = instantiateBrowserActions();
        actions.setBrowser(browser);
        return actions;
    }

    public abstract BrowserActions instantiateBrowserActions();
}
