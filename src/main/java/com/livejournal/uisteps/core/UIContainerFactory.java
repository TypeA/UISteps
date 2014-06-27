package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public interface UIContainerFactory {

    <T extends UIContainer> T instantiateUIContainer(Class<T> uiContainerClass);
}
