package com.livejournal.uisteps.thucydides.elements;

import com.livejournal.uisteps.thucydides.UIActions;
import com.livejournal.uisteps.core.BaseUIBlock;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.thucydides.NameConvertor;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.UIContainerInitializer;
import junit.framework.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 *
 * @author ASolyankin
 */
public class UIBlock extends HtmlElement implements BaseUIBlock {

    private final UIContainerInitializer initializer;
    private final UIActions actions;
    private boolean isInitialized;
    private final WebDriver driver;

    public UIBlock() {
        initializer = new UIContainerInitializer();
        actions = (UIActions) ThucydidesUtils.getFromSession("#UI_ACTIONS");
        driver = actions.getBrowser().getDriver();
    }

    @Override
    public void initElements() {
        initializer.initializeUIContainer(this, driver);
    }

    @Override
    public void callMethodsWhenOpens() {
        initializer.callMethodsWhenOpens(this);
    }

    protected <T extends UIContainer> T on(Class<T> UIContainerClass) {
        return actions.on(UIContainerClass);
    }

    protected <T extends UIContainer> T on(T uiContainer) {
        return actions.on(uiContainer);
    }

    @Override
    public void click() {
        actions.click(this);
    }

    public void moveMouseOver() {
        actions.moveMouseOver(this);
    }

    @Override
    public String toString() {
        return NameConvertor.humanize(getClass());
    }

    public <T extends Object> T elem(T element) {
        if (element == null) {
            Assert.fail("Cannot get element!");
        }
        return element;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized;
    }

    @Override
    public void initialized() {
        isInitialized = true;
    }

    @Override
    public WebDriver getDriver() {
        return driver;
    }

    public void waitUntil(ExpectedCondition<Object> condition) {
        actions.waitUntil(condition);
    }

    public void waitUntil(Boolean condition) {
        actions.waitUntil(condition);
    }
}
