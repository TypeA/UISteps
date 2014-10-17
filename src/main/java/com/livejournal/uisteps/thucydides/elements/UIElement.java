package com.livejournal.uisteps.thucydides.elements;


import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.htmlelements.annotations.Block;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

/**
 *
 * @author ASolyankin
 */
public class UIElement extends TypifiedElement {

    protected final Browser browser;

    public UIElement(WebElement wrappedElement) {
        super(wrappedElement);
        browser = (Browser) ThucydidesUtils.getFromSession("#BROWSER#");
    }

    public Object click() {
        UIElement elem = this;
        WebDriverWait wait = new WebDriverWait(browser.getDriver(), 10);    
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return elem.isDisplayed();
            }
        });
        browser.click(this);
        return null;
    }

    public Object moveMouseOver() {
        browser.moveMouseOver(this);
        return null;
    }

    public Object clickOnPoint(int x, int y) {
        browser.clickOnPoint(this, x, y);
        return null;
    }

    public <T extends Page> T onOpened(Class<T> pageClass) {
        return browser.onOpened(pageClass);
    }
    
    public <T extends UIBlock> T onDisplayed(Class<T> blockClass) {
        return browser.onDisplayed(blockClass);
    }
    
    public WebDriver getDriver() {
        return browser.getDriver();
    }
}
