package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.WebBrowserList;
import net.thucydides.core.Thucydides;
import net.thucydides.core.steps.BaseStepListener;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesWebBrowserList extends WebBrowserList {

    @Override
    public boolean previous() {
        if (super.previous()) {
            setCurrenetDriverToBaseStepListener();
            return true;
        } else {
            return false;
        }

    }

    @Override
    public boolean next() {
        if (super.next()) {
            setCurrenetDriverToBaseStepListener();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setCurrentByIndex(int index) {
        super.setCurrentByIndex(index);
        setCurrenetDriverToBaseStepListener();
    }

    @Override
    public void removeCurrent() {
        super.removeCurrent();
        setCurrenetDriverToBaseStepListener();
    }

    @Override
    public void add(Browser browser) {
        super.add(browser);
        setCurrenetDriverToBaseStepListener();
    }

    private void setCurrenetDriverToBaseStepListener() {
        BaseStepListener baseStepListener = (BaseStepListener) Thucydides.getStepListener();
        WebDriver currentDriver = getCurrent().getDriver();
        baseStepListener.setDriver(currentDriver);
    }
}
