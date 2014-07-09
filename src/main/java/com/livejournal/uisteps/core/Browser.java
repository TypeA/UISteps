package com.livejournal.uisteps.core;

import com.livejournal.uisteps.thucydides.DefaultUrlFactory;
import org.openqa.selenium.WebDriver;

/**
 *
 * @author ASolyankin
 */
public class Browser {

    private final WebDriver driver;
    private final UIContainerFactory uiContainerFactory;
    private final BrowserActions actions;
    private final UIContainerAnalizer uiContainerAnalizer;
    private final UIContainerComparator uiContainerComparator;
    private String name;
    private BasePage currentPage;
    private BaseUIBlock currentBlock;

    public Browser(DriverFactory driverFactory,
            UIContainerFactory uiContainerFactory,
            BrowserActionsFactory browserActionsFactory,
            UIContainerComparator uiContainerComparator) {
        driver = driverFactory.instantiateDriver();
        actions = browserActionsFactory.getBrowserActions(this);
        this.uiContainerFactory = uiContainerFactory;
        uiContainerAnalizer = new UIContainerAnalizer();
        this.uiContainerComparator = uiContainerComparator;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void close() {
        actions.close();
    }

    public void openUrl(String url) {
        actions.openUrl(url);
    }

    public void openUrl(Url url) {
        actions.openUrl(url);
    }

    @SuppressWarnings("unchecked")
    public <T extends UIContainer> T on(Class<T> uiContainerClass) {
        T uiContainerCandidate = getIfCurrent(uiContainerClass);
        if (uiContainerCandidate != null) {
            return actions.onOpened(uiContainerCandidate);
        }
        uiContainerCandidate = uiContainerFactory.instantiateUIContainer(uiContainerClass);
        if (uiContainerAnalizer.isPage(uiContainerCandidate)) {
            DefaultUrlFactory defaultUrlFactory = new DefaultUrlFactory();
            defaultUrlFactory.setDefaultUrlToPage((BasePage) uiContainerCandidate);
        }
        return on(uiContainerCandidate, false);

    }

    @SuppressWarnings("unchecked")
    public <T extends UIContainer> T on(Class<T> rootClass, String uiContainerClassName) {
        Class<?> klass = null;
        try {
            klass = Class.forName(uiContainerClassName);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("Cannot find class with such name: " + uiContainerClassName);
        }
        if (rootClass.isAssignableFrom(klass)) {
            return on((Class<T>) klass);
        }
        throw new NotUIContainerException(klass.getName());
    }

    @SuppressWarnings("unchecked")
    public <T extends UIContainer> T on(T uiContainer) {
        return on(uiContainer, true);
    }

    @SuppressWarnings("unchecked")
    private <T extends UIContainer> T on(T uiContainer, boolean needCheckCurrent) {
        if (needCheckCurrent) {
            T uiContainerCandidate = (T) getIfCurrent(uiContainer.getClass());
            if (uiContainerCandidate != null) {
                return actions.onOpened(uiContainerCandidate);
            }
        }
        if (uiContainerAnalizer.isPage(uiContainer)) {
            return (T) actions.open((BasePage) uiContainer);
        }
        if (uiContainerAnalizer.isBlock(uiContainer)) {
            return actions.onOpened(uiContainer);
        }
        throw new NotUIContainerException(uiContainer.getClass().getName());
    }

    void setCurrent(UIContainer uiContainer) {
        if (uiContainerAnalizer.isPage(uiContainer)) {
            currentPage = (BasePage) uiContainer;
        } else if (uiContainerAnalizer.isBlock(uiContainer)) {
            currentBlock = (BaseUIBlock) uiContainer;
        } else {
            throw new NotUIContainerException(uiContainer.getClass().getName());
        }
    }

    public boolean isCurrentPage(Class<? extends UIContainer> klass) {
        return currentPage != null && uiContainerComparator.compare(currentPage.getClass(), klass);
    }

    public boolean isCurrentBlock(Class<? extends UIContainer> klass) {
        return currentBlock != null && uiContainerComparator.compare(currentBlock.getClass(), klass);
    }

    public boolean isCurrent(Class<? extends UIContainer> klass) {
        return isCurrentPage(klass) || isCurrentBlock(klass);
    }

    private static int i = 0;

    public boolean isCurrent(UIContainer uiContainer) {
        return isCurrent(uiContainer.getClass());
    }

    @SuppressWarnings("unchecked")
    <T extends UIContainer> T getIfCurrent(Class<T> klass) {
        if (isCurrentPage(klass)) {
            return (T) currentPage;
        }
        if (isCurrentBlock(klass)) {
            return (T) currentBlock;
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }
}
