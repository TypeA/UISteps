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

import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.core.Page;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.thucydides.core.annotations.DefaultUrl;

/**
 *
 * @author ASolyankin
 */
public class UrlFactory implements com.livejournal.uisteps.core.UrlFactory {

    @Override
    public Url getDefaultUrlOfPage(Class<? extends Page> pageClass) {
        Url url = new Url();
        if (url.getHost().equals("")) {
            String baseUrl = ThucydidesUtils.getBaseUrl();
            url.setHost(baseUrl);
        }
        getUrlOf(url, getPageClass(pageClass));
        return url;
    }

    private void getUrlOf(Url url, Class<?> clazz) {
        if (!RootAnalizer.isRoot(clazz)) {
            getUrlOf(url, clazz.getSuperclass());
        }
        if (clazz.isAnnotationPresent(DefaultUrl.class)) {
            String defaultUrl = clazz.getAnnotation(DefaultUrl.class).value();
            if (defaultUrl.contains("#HOST")) {
                Pattern pattern = Pattern.compile("(.*)#HOST(.*)");
                Matcher matcher = pattern.matcher(defaultUrl);
                if (matcher.find()) {
                    String prefix = matcher.group(1);
                    String postfix = matcher.group(2);
                    if (prefix != null) {
                        url.prependPrefix(prefix);
                    }
                    if (postfix != null) {
                        url.appendPostfix(postfix);
                    }
                }
            } else {
                url.appendPostfix(defaultUrl);
            }
        }
    }
   
    @Override
    public void setDefaultUrlTo(Page page) {
        Url url = page.getUrl();
        if (url.getHost().equals("")) {
            String baseUrl = ThucydidesUtils.getBaseUrl();
            url.setHost(baseUrl);
        }
        setUrlTo(page, getPageClass(page.getClass()));
    }

    private void setUrlTo(Page page, Class<?> clazz) {
        if (!RootAnalizer.isRoot(clazz)) {
            setUrlTo(page, clazz.getSuperclass());
        }
        if (clazz.isAnnotationPresent(DefaultUrl.class)) {
            String defaultUrl = clazz.getAnnotation(DefaultUrl.class).value();
            Url url = page.getUrl();
            if (defaultUrl.contains("#HOST")) {
                Pattern pattern = Pattern.compile("(.*)#HOST(.*)");
                Matcher matcher = pattern.matcher(defaultUrl);
                if (matcher.find()) {
                    String prefix = matcher.group(1);
                    String postfix = matcher.group(2);
                    if (prefix != null) {
                        url.prependPrefix(prefix);
                    }
                    if (postfix != null) {
                        url.appendPostfix(postfix);
                    }
                }
            } else {
                url.appendPostfix(defaultUrl);
            }
        }
    }

    private Class<?> getPageClass(Class<?> clazz) {
        if (clazz.getName().contains("$$")) {
            return getPageClass(clazz.getSuperclass());
        } else {
            return clazz;
        }
    }
}
