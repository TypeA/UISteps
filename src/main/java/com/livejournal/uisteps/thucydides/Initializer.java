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

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.Page;
import com.livejournal.uisteps.core.UIBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import junit.framework.Assert;
import net.thucydides.core.annotations.WhenPageOpens;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.annotations.Block;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

/**
 *
 * @author ASolyankin
 */
public class Initializer implements com.livejournal.uisteps.core.Initializer {

 //   void initialize(com.livejournal.uisteps.core2.Page page, Browser browser);
    //   void initialize(com.livejournal.uisteps.core2.UIBlock block, Browser browser);
    @Override
    public void initialize(Page page, Browser browser) {
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), 10);
        try {
            wait.until(new ExpectedCondition<Boolean>() {
                @Override
                public Boolean apply(WebDriver driver) {
                    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
                }
            });
        } catch (Exception ex) {
            Assert.fail("Page " + page + " is not displayed!\n" + ex);
        }
        HtmlElementLoader.populate(page, browser.getDriver());
        callMethodsWhenOpens(page);
    }

    @Override
    public void initialize(UIBlock block, Browser browser) {
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), 10);
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
            }
        });

        wait.until(new ExpectedCondition<Boolean>() {
            UIBlock b = block;
            @Override
            public Boolean apply(WebDriver driver) {
                return driver.findElement(
                        By.cssSelector(b.getClass().getSuperclass()
                                .getAnnotation(Block.class).value().css()))
                        .isDisplayed();
            }
        });
        HtmlElementLoader.populate(block, browser.getDriver());
        callMethodsWhenOpens(block);
    }

    private void callMethodsWhenOpens(Object uiObject) {
        Class<?> fieldClass = uiObject.getClass();
        if (fieldClass.getName().contains("$$")) {
            callWhenUIObjectOpensMethods(uiObject, fieldClass.getSuperclass());
        } else {
            callWhenUIObjectOpensMethods(uiObject, fieldClass);
        }
    }

    private void callWhenUIObjectOpensMethods(Object uiObject, Class<?> clazz) {
        if (!RootAnalizer.isRoot(clazz)) {
            callWhenUIObjectOpensMethods(uiObject, clazz.getSuperclass());
        }
        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
        Collections.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method a, Method b) {
                return a.getName().compareTo(b.getName());
            }
        });
        for (Method method : methods) {
            if (method.isAnnotationPresent(WhenPageOpens.class)) {
                try {
                    method.setAccessible(true);
                    method.invoke(uiObject);
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new RuntimeException("Cannot invoke method \"" + method.getName() + "\""
                            + " in page \"" + uiObject + "\""
                            + " annotated by @WhenPageOpens!\n" + ex);
                }
            }
        }
    }
}
