package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.UIContainerAnalizer;
import com.livejournal.uisteps.thucydides.elements.UIBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import junit.framework.Assert;
import net.thucydides.core.annotations.WhenPageOpens;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

/**
 *
 * @author ASolyankin
 */
public class UIContainerInitializer {

    private final UIContainerAnalizer uiContainerAnalizer = new UIContainerAnalizer();

    public void initializeUIContainer(UIContainer uiContainer, WebDriver driver) {
        HtmlElementLoader.populate(uiContainer, driver);
        if (uiContainerAnalizer.isBlock(uiContainer)) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            try {
                wait.until(new ExpectedCondition<Boolean>() {
                    @Override
                    public Boolean apply(WebDriver d) {
                        return ((UIBlock) uiContainer).isDisplayed();
                    }
                });
            } catch (Exception ex) {
                Assert.fail("The block " + uiContainer + " is not displayed!\n" + ex);
            }
        }
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
