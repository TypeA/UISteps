package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.thucydides.elements.Link;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.WrapsElement;

/**
 *
 * @author ASolyankin
 */
public class UIActions {

    private Browser browser;

    public void init(Browser browser) {
        this.browser = browser;
    }

    public <T extends UIContainer> T on(Class<T> UIContainerClass) {
        return browser.on(UIContainerClass);
    }

    public <T extends BasePage> T on(Class<T> pageClass, Url url) {
        return browser.on(pageClass, url);
    }

    public <T extends UIContainer> T on(T uiContainer) {
        return browser.on(uiContainer, null);
    }

    public <T extends BasePage> T on(T page, Url url) {
        return browser.on(page, url);
    }

    public <T extends UIContainer> T on(Class<T> rootClass, String uiContainerClassName) {
        return browser.on(rootClass, uiContainerClassName);
    }

    public void switchToNextWindow() {
        browser.switchToNextWindow();
    }

    public void switchToPreviousWindow() {
        browser.switchToPreviousWindow();
    }

    public void switchToDefaultWindow() {
        browser.switchToWindowByIndex(0);
    }

    public void switchToWindowByIndex(int index) {
        browser.switchToWindowByIndex(index);
    }

    @Step
    public void click(WrapsElement element) {
        WebElement webElement = element.getWrappedElement();
        if (element instanceof Link) {
            String attrTarget = webElement.getAttribute("target");
            boolean needToSwitch = attrTarget != null && !attrTarget.equals("") && !attrTarget.equals("_self");
            webElement.click();
            if (needToSwitch) {
                switchToNextWindow();
            }
        } else {
            webElement.click();
        }
    }

    @Step
    public void clickOnPoint(WrapsElement element, int x, int y) {
        Actions actions = new Actions(browser.getDriver());
        actions.moveToElement(element.getWrappedElement(), x, y).click().build().perform();
    }

    @Step
    public void moveMouseOver(WrapsElement element) {
        Actions actions = new Actions(browser.getDriver());
        actions.moveToElement(element.getWrappedElement()).build().perform();
        try {
            Thread.sleep(300);
        } catch (InterruptedException ex) {
            throw new RuntimeException("Cannot move mouse over " + this + "\n" + ex);
        }
    }

    @Step
    public void typeInto(WrapsElement input, CharSequence... keys) {
        input.getWrappedElement().sendKeys(keys);
    }

    @Step
    public void clear(WrapsElement input) {
        input.getWrappedElement().clear();
    }

    @Step
    public void enterInto(WrapsElement input, CharSequence... text) {
        input.getWrappedElement().clear();
        input.getWrappedElement().sendKeys(text);
    }

    public String getTextFrom(WrapsElement input) {
        return input.getWrappedElement().getText();
    }
}
