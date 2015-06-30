package com.livejournal.uisteps.thucydides.elements;

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.NameConvertor;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

/**
 *
 * @author ASolyankin
 */
public class UIBlock extends HtmlElement implements com.livejournal.uisteps.core.UIBlock {

    protected final Browser browser;

    public UIBlock() {
        browser = (Browser) ThucydidesUtils.getFromSession("#BROWSER#");
    }

    @Override
    public void click() {
        browser.click(this);
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

    @Override
    public String toString() {
        return NameConvertor.humanize(getClass());
    }

    public Object startScript(String script) {
        return browser.startScript(script);
    }
}
