package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.DriverFactory;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesDriverFactory implements DriverFactory {

    @Override
    public WebDriver instantiateDriver() {
        return ThucydidesUtils.getNewDriver();
    }

}
