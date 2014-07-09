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
        getActions().typeInto(this, keys);
    }

    public void clear() {
        getActions().clear(this);
    }

    public void enter(CharSequence... text) {
        getActions().enterInto(this, text);
    }

    public String getText() {
        return getActions().getTextFrom(this);
    }
}
