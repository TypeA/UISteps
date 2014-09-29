package com.livejournal.uisteps.core;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

/**
 *
 * @author ASolyankin
 */
public interface UIContainer {

    default void initialize() {
        initElements();
        callMethodsWhenOpens();
        initialized();
    }

    void initElements();

    void callMethodsWhenOpens();

    boolean isInitialized();

    void initialized();

    WebDriver getDriver();

    void waitUntil(ExpectedCondition<Boolean> condition);


}
