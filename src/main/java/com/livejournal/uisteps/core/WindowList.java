package com.livejournal.uisteps.core;

import java.util.Set;
import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author ASolyankin
 */
public class WindowList {

    private int currentHandleIndex;
    private final Browser browser;

    public WindowList(Browser browser) {
        this.browser = browser;
    }

    public void switchToNextWindow() {
        switchToWindowByIndex(currentHandleIndex + 1);
    }

    public void switchToPreviousWindow() {
        switchToWindowByIndex(currentHandleIndex - 1);
    }

    public void switchToDefaultWindow() {
        switchToWindowByIndex(0);
    }

    public void switchToWindowByIndex(int index) {
        WebDriver driver = browser.getDriver();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        Set<String> setHandles = driver.getWindowHandles();
        if (index < 0 || index >= setHandles.size()) {
            Assert.fail("Cannot switch to window by index: " + index + "!\n");
        }
        currentHandleIndex = index;
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver d) {
                Set<String> setHandles = driver.getWindowHandles();
                Object[] handles = setHandles.toArray();
                return !handles[handles.length - 1].equals("");
            }
        });
        setHandles = driver.getWindowHandles();
        Object[] handles = setHandles.toArray();
        driver.switchTo().window((String) handles[index]);
    }
}
