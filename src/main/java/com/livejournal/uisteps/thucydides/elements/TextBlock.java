package com.livejournal.uisteps.thucydides.elements;

import org.openqa.selenium.WebElement;

/**
 *
 * @author ASolyankin
 */
public class TextBlock extends UIElement {

    public TextBlock(WebElement wrappedElement) {
        super(wrappedElement);
    }

    public String getText() {
        return browser.getTextFrom(this);
    }
}
