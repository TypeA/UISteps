package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.UIContainer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.thucydides.core.annotations.WhenPageOpens;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

import  ru.yandex.qatools.htmlelements.utils.HtmlElementUtils;

/**
 *
 * @author ASolyankin
 */
public class UIContainerInitializer {

    public void initializeUIContainer(UIContainer uiContainer, WebDriver driver) {
        HtmlElementLoader.populate(uiContainer, driver);
    }

    public void callMethodsWhenOpens(UIContainer uiContainer) {
        Class<?> fieldClass = uiContainer.getClass();
        if (fieldClass.getName().contains("$$")) {
            callWhenUIObjectOpensMethods(uiContainer, fieldClass.getSuperclass());
        } else {
            callWhenUIObjectOpensMethods(uiContainer, fieldClass);
        }
    }

    private void callWhenUIObjectOpensMethods(UIContainer uiContainer, Class<?> clazz) {
        if (!RootAnalizer.isRoot(clazz)) {
            callWhenUIObjectOpensMethods(uiContainer, clazz.getSuperclass());
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
                    method.invoke(uiContainer);
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    throw new RuntimeException("Cannot invoke method \"" + method.getName() + "\""
                            + " in page \"" + uiContainer + "\""
                            + " annotated by @WhenPageOpens!\n" + ex);
                }
            }
        }
    }
}
