package com.livejournal.uisteps.thucydides.tests;

import com.livejournal.uisteps.core.BaseTest;
import com.livejournal.uisteps.thucydides.BaseStepsListener;
import com.livejournal.uisteps.thucydides.Session;
import com.livejournal.uisteps.thucydides.SessionKeys;
import com.livejournal.uisteps.thucydides.ThucydidesTestActionsFactory;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.UIActions;

/**
 *
 * @author ASolyankin
 */
public class SimpleTest extends BaseTest {

    public SimpleTest() {
        super(new ThucydidesTestActionsFactory());
        BaseStepsListener stepsListener = new BaseStepsListener(getActions());
        ThucydidesUtils.registerListner(stepsListener);
        UIActions uiActions = ThucydidesUtils.getNewStepLibrary(UIActions.class);
        uiActions.setTestActions(getActions());
        Session.put(SessionKeys.UI_ACTIONS, uiActions);
    }

    public void openBrowser() {
        openBrowser(ThucydidesUtils.getSupportedDriver().name());
    }

}
