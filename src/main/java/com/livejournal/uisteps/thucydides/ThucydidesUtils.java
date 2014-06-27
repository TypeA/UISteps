package com.livejournal.uisteps.thucydides;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.StepFactory;
import net.thucydides.core.steps.StepListener;
import net.thucydides.core.webdriver.Configuration;
import net.thucydides.core.webdriver.SupportedWebDriver;
import net.thucydides.core.webdriver.WebdriverProxyFactory;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author Asolyankin
 */
public class ThucydidesUtils {

    public static <T> T getNewStepLibrary(Class<T> stepLibraryClass) {
        StepFactory stepFactory = new StepFactory();
        return stepFactory.instantiateNewStepLibraryFor(stepLibraryClass);
    }

    public static String getBaseUrl() {
        return getConfiguration().getBaseUrl();
    }

    public static SupportedWebDriver getSupportedDriver() {
        return getConfiguration().getDriverType();
    }

    public static WebDriver getNewDriver() {
        return WebdriverProxyFactory.getFactory().proxyDriver();
    }

    public static void resetDriver(WebDriver driver) {
        WebdriverProxyFactory.resetDriver(driver);
    }

    public static void registerListner(StepListener stepsListener) {
        StepEventBus.getEventBus().registerListener(stepsListener);
    }

    private static Configuration getConfiguration() {
        return Injectors.getInjector().getInstance(Configuration.class);
    }
}
