
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
package com.livejournal.uisteps.thucydides.elements;

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.thucydides.Databases;
import com.livejournal.uisteps.thucydides.Databases.BaseConnect;
import com.livejournal.uisteps.thucydides.DatabasesData;
import com.livejournal.uisteps.thucydides.NameConvertor;
import com.livejournal.uisteps.thucydides.RedisData;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.UrlFactory;
import net.thucydides.core.guice.Injectors;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class Page implements com.livejournal.uisteps.core.Page {

    private final Browser browser;
    private final Databases databases;
    private final DatabasesData databasesData;
    private final RedisData redisData;
    private final UrlFactory urlFactory;
    private Url url;
    private net.thucydides.core.webdriver.Configuration systemConfiguration;

    public Page() {
        databases = new Databases();
        databasesData = new DatabasesData();
        urlFactory = new UrlFactory();
        url = urlFactory.getDefaultUrlOfPage(this.getClass());
        redisData = new RedisData();
        browser = (Browser) ThucydidesUtils.getFromSession("#BROWSER#");
    }

    @Override
    public Url getDefaultUrl() {
        return urlFactory.getDefaultUrlOfPage(this.getClass());
    }

    @Override
    public Url getUrl() {
        return url;
    }

    @Override
    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return NameConvertor.humanize(getClass())
                .replace("dot", "\\.")
                + " by url <a href='" + getUrl() + "'>" + getUrl() + "</a>";
    }

    public <T extends Page> T onOpened(Class<T> pageClass) {
        return browser.onOpened(pageClass);
    }

    public <T extends Page> T open(Class<T> pageClass) {
        return browser.open(pageClass);
    }

    public <T extends Page> T open(Class<T> pageClass, Url url) {
        return browser.open(pageClass, url);
    }

    public BaseConnect workWithDB() {
        return databases.workWithDB();
    }

    public DatabasesData getDBDate() {
        return databasesData;
    }
     public  RedisData workWithRedis() {
        return redisData;
    }

    public void addCookie(String cookie, String value) {
        browser.addCookie(cookie, value);
    }

    public <T extends UIBlock> T onDisplayed(Class<T> blockClass) {
        return browser.onDisplayed(blockClass);
    }

    public WebDriver getDriver() {
        return browser.getDriver();
    }

    public Object startScript(String script) {
        return browser.startScript(script);
    }

    public net.thucydides.core.webdriver.Configuration getSystemConfiguration() {
        if (systemConfiguration == null) {
            systemConfiguration = Injectors.getInjector().getInstance(net.thucydides.core.webdriver.Configuration.class);
        }
        return systemConfiguration;
    }
}
