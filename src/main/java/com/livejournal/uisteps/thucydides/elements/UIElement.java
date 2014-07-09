package com.livejournal.uisteps.thucydides.elements;

import com.livejournal.uisteps.core.TestActions;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.thucydides.ThucydidesTestActionsFactory;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.UIActions;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.htmlelements.element.TypifiedElement;

/**
 *
 * @author ASolyankin
 */
public class UIElement extends TypifiedElement {

    private final UIActions uiActions;

    public UIElement(WebElement wrappedElement) {
        super(wrappedElement);
        uiActions = ThucydidesUtils.getNewStepLibrary(UIActions.class);
        TestActions testActions = new ThucydidesTestActionsFactory().instantiateTestActions();
        uiActions.setTestActions(testActions);
    }

    protected <T extends UIContainer> T on(Class<T> UIContainerClass) {
        return uiActions.on(UIContainerClass);
    }

    protected <T extends UIContainer> T on(T uiContainer) {
        return uiActions.on(uiContainer);
    }

    public void click() {
        uiActions.click(this);
    }

    public void moveMouseOver() {
        uiActions.moveMouseOver(this);
    }

    protected UIActions getActions() {
        return uiActions;
    }
}
