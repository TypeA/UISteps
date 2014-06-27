package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.UIContainerComparator;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesUIContainerComparator implements UIContainerComparator {

    @Override
    public boolean compare(Class<?> klass1, Class<?> klass2) {
        return trim(klass1).equals(trim(klass2));
    }

    private String trim(Class<?> klass) {
        return klass.getName().replaceAll("\\$\\$.*", "").trim();
    }

}
