package com.livejournal.uisteps.thucydides;

/**
 *
 * @author ASolyankin
 */
public class RootAnalizer {

    public static boolean isRoot(Class<?> klass) {
        return klass.isAnnotationPresent(Root.class) || klass == Object.class;
    }
}
