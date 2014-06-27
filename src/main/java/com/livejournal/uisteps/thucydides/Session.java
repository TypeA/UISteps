package com.livejournal.uisteps.thucydides;

import net.thucydides.core.Thucydides;

/**
 *
 * @author ASolyankin
 */
public class Session {

    @SuppressWarnings("unchecked")
    public static void put(String key, Object value) {
        Thucydides.getCurrentSession().put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static Object get(Object key) {
        if (Thucydides.getCurrentSession().containsKey(key)) {
            return Thucydides.getCurrentSession().get(key);
        } else {
            throw new RuntimeException("Cannot get a value  by key \"" + key + "\" from session!");
        }
    }
}
