package com.livejournal.uisteps.thucydides;

import net.thucydides.core.Thucydides;
import net.thucydides.core.ThucydidesSystemProperties;
import net.thucydides.core.ThucydidesSystemProperty;
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

    public static void putToSession(String key, Object value) {
        Thucydides.getCurrentSession().put(key, value);
    }

    public static Object getFromSession(String key) {
        return Thucydides.getCurrentSession().get(key);
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

    public static void registerListener(StepListener stepsListener) {
        StepEventBus.getEventBus().registerListener(stepsListener);
    }

    private static Configuration getConfiguration() {
        return Injectors.getInjector().getInstance(Configuration.class);
    }
    
    public static Integer getImplementTimeout() {
        return Integer.valueOf(ThucydidesSystemProperties.getProperties().getValue(ThucydidesSystemProperty.WEBDRIVER_TIMEOUTS_IMPLICITLYWAIT));
    }
    
    public static String getHost() {
        return ThucydidesSystemProperties.getProperties().getValue(ThucydidesSystemProperty.WEBDRIVER_BASE_URL);
    }
    
    public static String getDriver() {
        return ThucydidesSystemProperties.getProperties().getValue(ThucydidesSystemProperty.WEBDRIVER_DRIVER);
    }
}
