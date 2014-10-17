/*
 * Copyright 2014 ASolyankin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
        ThucydidesSystemProperties properties = ThucydidesSystemProperties.getProperties();
        return properties.getIntegerValue(ThucydidesSystemProperty.WEBDRIVER_TIMEOUTS_IMPLICITLYWAIT, 100000);
    }
    
    public static Integer getImplementTimeoutInSec() {
        return getImplementTimeout() / 1000;
    }
    
    public static String getDriver() {
        return ThucydidesSystemProperties.getProperties().getValue(ThucydidesSystemProperty.WEBDRIVER_DRIVER);
    }
}
