package com.livejournal.uisteps.thucydides.elements;

import com.livejournal.uisteps.core.BasePage;
import com.livejournal.uisteps.core.UIContainer;
import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.thucydides.NameConvertor;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import com.livejournal.uisteps.thucydides.UIActions;
import com.livejournal.uisteps.thucydides.UIContainerInitializer;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class Page extends PageObject implements BasePage {

    private Url url;
    private final UIContainerInitializer initializer;
    private final UIActions actions;

    public Page() {
        url = new Url();
        initializer = new UIContainerInitializer();
        actions = (UIActions) ThucydidesUtils.getFromSession("#UI_ACTIONS");
    }

    @Override
    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public void initElements(WebDriver driver) {
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
    public String toString() {
        return NameConvertor.humanize(getClass())
                .replace("dot", "\\.")
                + " by url <a href='" + getUrl().toString() + "'>" + getUrl().toString() + "</a>";
    }
}
