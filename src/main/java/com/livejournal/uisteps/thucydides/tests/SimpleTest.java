package com.livejournal.uisteps.thucydides.tests;

import com.livejournal.uisteps.core.BaseTest;
import com.livejournal.uisteps.thucydides.ThucydidesBaseStepListener;
import com.livejournal.uisteps.thucydides.ThucydidesTestActionsFactory;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;

/**
 *
 * @author ASolyankin
 */
public class SimpleTest extends BaseTest {

    public SimpleTest() {
        super(new ThucydidesTestActionsFactory());
        ThucydidesBaseStepListener stepListener = new ThucydidesBaseStepListener(getActions());
        ThucydidesUtils.registerListner(stepListener);
    }

    public void openBrowser() {
        openBrowser(ThucydidesUtils.getSupportedDriver().name());
    }

}
