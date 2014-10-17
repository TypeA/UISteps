package com.livejournal.uisteps.thucydides.elements;

import org.openqa.selenium.WebElement;

/**
 *
 * @author ASolyankin
 */
public class TextField extends UIElement {

    public TextField(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public void sendKeys(CharSequence... keys) {
        type(keys);
    }

    public void type(CharSequence... keys) {
        browser.typeInto(this, keys);
    }

    public void clear() {
        browser.clear(this);
    }

    public void enter(CharSequence... text) {
        browser.enterInto(this, text);
    }

    public String getText() {
        return browser.getTextFrom(this);
    }
}
