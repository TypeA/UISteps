package com.livejournal.uisteps.core;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public interface UIContainer {

    default void initialize(WebDriver driver) {
        initElements(driver);
        callMethodsWhenOpens();
    }

    void initElements(WebDriver driver);

    void callMethodsWhenOpens();

}
