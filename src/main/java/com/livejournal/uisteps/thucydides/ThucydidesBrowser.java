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
import com.livejournal.uisteps.core.Initializer;
import com.livejournal.uisteps.core.Page;
import com.livejournal.uisteps.core.StepLibraryFactory;
import com.livejournal.uisteps.core.UIBlock;
import com.livejournal.uisteps.core.WindowList;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.internal.WrapsElement;

/**
 *
 * @author ASolyankin
 */
public class ThucydidesBrowser extends Browser {

    private final Browser browser;
    private boolean isOpened;

    public ThucydidesBrowser() {
        browser = new Browser();
    }

    public ThucydidesBrowser(WebDriver driver, StepLibraryFactory pageFactory, Initializer initializer) {
        super(driver, pageFactory, initializer);
        browser = new Browser();
        browser.setDriver(driver);
        browser.setStepLibraryFactory(pageFactory);
        browser.setInitializer(initializer);
        browser.setCache(cache);
        browser.setWindowList(windowList);
    }

    public Browser getBrowserWithoutSteps() {
        return browser;
    }

    @Override
    public final void setInitializer(Initializer initializer) {
        super.setInitializer(initializer);
        browser.setInitializer(initializer);
    }

    @Override
    public final void setStepLibraryFactory(StepLibraryFactory stepLibraryFactory) {
        super.setStepLibraryFactory(stepLibraryFactory);
        browser.setStepLibraryFactory(stepLibraryFactory);
    }

    @Override
    public final void setDriver(WebDriver driver) {
        super.setDriver(driver);
        browser.setDriver(driver);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
        browser.setName(name);
    }

    @Override
    public void setWindowList(WindowList windowList) {
        super.setWindowList(windowList);
        browser.setWindowList(windowList);
    }

    @Override
    public void setCache(Cache cache) {
        super.setCache(cache);
        browser.setCache(cache);
    }

    @Step
    @Override
    public void enterInto(WrapsElement input, CharSequence... text) {
        super.enterInto(input, text);
    }

    @Step
    @Override
    public void clear(WrapsElement input) {
        super.clear(input);
    }

    @Step
    @Override
    public void typeInto(WrapsElement input, CharSequence... keys) {
        super.typeInto(input, keys);
    }

    @Step
    @Override
    public void clickOnPoint(WrapsElement element, int x, int y) {
        super.clickOnPoint(element, x, y);
    }

    @Step
    @Override
    public void click(WrapsElement element) {
        super.click(element);
    }

    @Step
    @Override
    public void deleteCookies() {
        super.deleteCookies();
    }

    @Step
    @Override
    public void refreshCurrentPage() {
        super.refreshCurrentPage();
    }

    @Override
    public <T extends Page> T open(Class<T> pageClass) {
        return super.open(pageClass);
    }

    @Step
    @Override
    public <T extends Page> T open(T page) {
        return super.open(page);
    }

    @Override
    public <T extends Page> T onOpened(Class<T> klass) {
        return super.onOpened(klass);
    }

    @Override
    public <T extends UIBlock> T onDisplayed(Class<T> blockClass) {
        return super.onDisplayed(blockClass);
    }

    @Step
    @Override
    public <T extends UIBlock> T onDisplayed(T block) {
        return super.onDisplayed(block);
    }

    @Step
    @Override
    public void openUrl(String url) {
        super.openUrl(url);
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void isOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

}
