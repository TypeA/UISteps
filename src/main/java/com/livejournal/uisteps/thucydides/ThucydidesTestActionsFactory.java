package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.TestActions;
import com.livejournal.uisteps.core.TestActionsFactory;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesTestActionsFactory implements TestActionsFactory {

    @Override
    public TestActions instantiateTestActions() {
        return ThucydidesUtils.getNewStepLibrary(ThucydidesTestActions.class);
    }

}
