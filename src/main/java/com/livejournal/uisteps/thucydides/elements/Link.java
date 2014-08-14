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
        return getActions().getTextFrom(this);
    }

    @Override
    public Object click() {
        WebElement webElement = getWrappedElement();
        String attrTarget = webElement.getAttribute("target");
        boolean needToSwitch = attrTarget != null && !attrTarget.equals("") && !attrTarget.equals("_self");
        Object returnObject = super.click();
        if(needToSwitch) {
            switchToNextWindow();
        }
        return returnObject;
    }

}
