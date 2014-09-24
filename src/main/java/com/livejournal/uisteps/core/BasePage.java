package com.livejournal.uisteps.core;

/**
 *
 * @author Asolyankin
 */
public interface BasePage extends UIContainer {

    Url getUrl();

    void setUrl(Url url);
    
    boolean getConditionToOpen();
}
