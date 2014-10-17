package com.livejournal.uisteps.thucydides;

import net.thucydides.core.util.NameConverter;

/**
 *
 * @author ASolyankin
 */
public class NameConvertor {

    public static String humanize(Class<?> klass) {
        return humanize(klass.getSimpleName());
    }

    public static String humanize(Object obj) {
        return humanize(obj.getClass());
    }

    public static String humanize(String string) {
        return NameConverter.humanize(string).replaceAll("\\$\\$.*", "").trim();
    }
}
