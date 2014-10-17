package com.livejournal.uisteps.thucydides.elements;

import org.openqa.selenium.WebElement;

/**
 *
 * @author ASolyankin
 */
public class Link extends UIElement {

    public Link(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public String getText() {
        return browser.getTextFrom(this);
    }

}
