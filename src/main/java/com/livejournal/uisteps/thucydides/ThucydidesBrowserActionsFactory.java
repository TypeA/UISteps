package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BrowserActions;
import com.livejournal.uisteps.core.BrowserActionsFactory;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesBrowserActionsFactory extends BrowserActionsFactory {

    
    
    @Override
    public BrowserActions instantiateBrowserActions() {
        return ThucydidesUtils.getNewStepLibrary(ThucydidesBrowserActions.class);
    }

}
