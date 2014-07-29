package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.UIContainerFactory;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesUIContainerFactory implements UIContainerFactory {

   

    public ThucydidesUIContainerFactory() {
    }
    
     @Override
    public <T extends UIContainer> T instantiateUIContainer(Class<T> uiContainerClass) {
        return ThucydidesUtils.getNewStepLibrary(uiContainerClass);
    }

}
