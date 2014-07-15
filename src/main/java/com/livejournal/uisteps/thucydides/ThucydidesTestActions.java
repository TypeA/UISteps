package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BrowserStorage;
import com.livejournal.uisteps.core.TestActions;
import com.livejournal.uisteps.core.WebBrowserList;
import java.lang.reflect.Field;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.BaseStepListener;
import net.thucydides.core.steps.StepEventBus;
import org.openqa.selenium.WebDriver;

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
        setDriverToBaseStepsListener();
    }

    @Step
    @Override
    public void switchToNextBrowser() {
        super.switchToNextBrowser();
        setDriverToBaseStepsListener();
    }

    @Step
    @Override
    public void openBrowser(String name) {
        super.openBrowser(name);
        setDriverToBaseStepsListener();
    }

    @Override
    public void closeCurrentBrowser() {
        super.closeCurrentBrowser();
        setDriverToBaseStepsListener();
    }

    private void setDriverToBaseStepsListener() {
        BaseStepListener baseStepsListener = getBaseStepListener();
        WebDriver currentDriver = null;
        try {
            currentDriver = getCurrentBrowser().getDriver();
        } catch (Exception ex) {
        }
        baseStepsListener.setDriver(currentDriver);
    }

    private BaseStepListener getBaseStepListener() {
        try {
            StepEventBus eventBus = StepEventBus.getEventBus();
            Field field = eventBus.getClass().getDeclaredField("baseStepListener");
            field.setAccessible(true);
            return (BaseStepListener) field.get(eventBus);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
            throw new RuntimeException("Cannot get BaseStepListener!\n " + ex);
        }
    }
}
