package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.Url;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.thucydides.core.annotations.DefaultUrl;

/**
 *
 * @author ASolyankin
 */
public class DefaultUrlFactory {

    
     public Url getDefaultUrlOfPage(Class<?> pageClass) {
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
    
    
    public void setDefaultUrlToPage(BasePage page) {
        Url url = page.getUrl();
        if (url.getHost().equals("")) {
            String baseUrl = ThucydidesUtils.getBaseUrl();
            url.setHost(baseUrl);
        }
        setUrlTo(page, getPageClass(page.getClass()));
    }

    private void setUrlTo(BasePage page, Class<?> clazz) {
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
