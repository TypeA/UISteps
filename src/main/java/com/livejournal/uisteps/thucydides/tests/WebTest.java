package com.livejournal.uisteps.thucydides.tests;

import com.livejournal.uisteps.thucydides.ThucydidesStepListener;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;

/**
 *
 * @author ASolyankin
 */
public class WebTest extends SimpleTest {

    public WebTest() {
        ThucydidesStepListener stepListener = new ThucydidesStepListener(this);
        ThucydidesUtils.registerListner(stepListener);
    }

}
