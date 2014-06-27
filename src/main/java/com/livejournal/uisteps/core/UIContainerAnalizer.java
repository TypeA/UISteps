package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public class UIContainerAnalizer {

    public boolean isPage(Object obj) {
        return isPage(obj.getClass());
    }

    public boolean isBlock(Object obj) {
        return isBlock(obj.getClass());
    }

    public boolean isPage(Class<?> klass) {
        return BasePage.class.isAssignableFrom(klass);
    }

    public boolean isBlock(Class<?> klass) {
        return BaseUIBlock.class.isAssignableFrom(klass);
    }
}
