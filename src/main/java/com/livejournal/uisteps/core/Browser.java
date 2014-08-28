package com.livejournal.uisteps.core;

import com.livejournal.uisteps.thucydides.DefaultUrlFactory;
import com.livejournal.uisteps.thucydides.ThucydidesStepListener;
import com.livejournal.uisteps.thucydides.ThucydidesUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 *
 * @author ASolyankin
 */
public class Browser extends ScenarioSteps {

    private UIContainerFactory uiContainerFactory;
    private UIContainerAnalizer uiContainerAnalizer;
    private UIContainerComparator uiContainerComparator;
    private String name;
    private BasePage currentPage;
    private BaseUIBlock currentBlock;
    private boolean opened;
    private final WindowList windowList;

    public Browser() {
        ThucydidesStepListener listener = new ThucydidesStepListener(this);
        ThucydidesUtils.registerListener(listener);
        windowList = new WindowList(this);
    }

    @Step
    public void switchToNextWindow() {
        windowList.switchToNextWindow();
    }

    @Step
    public void switchToPreviousWindow() {
        windowList.switchToPreviousWindow();
    }

    @Step
    public void switchToDefaultWindow() {
        windowList.switchToWindowByIndex(0);
    }

    @Step
    public void switchToWindowByIndex(int index) {
        windowList.switchToWindowByIndex(index);
    }

    @Step
    public void refreshCurrentPage() {
        getDriver().navigate().refresh();
    }

    public void clearCache() {
        currentPage = null;
        currentBlock = null;
        if (windowList.getCountOfWindows() > 1) {
            switchToDefaultWindow();
        }
        getDriver().manage().deleteAllCookies();
    }

    public boolean isOpened() {
        return opened;
    }

    public void setOpened() {
        opened = true;
    }

    public void init(
            UIContainerFactory uiContainerFactory,
            UIContainerComparator uiContainerComparator,
            UIContainerAnalizer uiContainerAnalizer) {
        this.uiContainerFactory = uiContainerFactory;
        this.uiContainerComparator = uiContainerComparator;
        this.uiContainerAnalizer = uiContainerAnalizer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Step
    public void openUrl(String url) {
        getDriver().get(url);
    }

    public void openUrl(Url url) {
        String urlString = url.toString();
        openUrl(urlString);
    }

    private void openPageUrl(Url url) {
        String urlString = url.toString();
        getDriver().get(urlString);
    }

    @SuppressWarnings("unchecked")
    public <T extends UIContainer> T on(Class<T> uiContainerClass) {
        return on(getUIContainerCandidate(uiContainerClass), null);
    }

    @SuppressWarnings("unchecked")
    public <T extends BasePage> T on(Class<T> pageClass, Url url) {
        return on(getUIContainerCandidate(pageClass), url);

    }

    private <T extends UIContainer> T getUIContainerCandidate(Class<T> uiContainerClass) {
        T uiContainerCandidate = getIfCurrent(uiContainerClass);
        if (uiContainerCandidate != null) {
            return uiContainerCandidate;
        } else {
            uiContainerCandidate = uiContainerFactory.instantiateUIContainer(uiContainerClass);
            return uiContainerCandidate;
        }

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
    public <T extends UIContainer> T on(T uiContainer, Url url) {
        if (url != null) {
            Url pageUrl = ((BasePage) uiContainer).getUrl();
            pageUrl.prependPrefix(url.getPrefix())
                    .appendPostfix(url.getPostfix());
            String urlHost = url.getHost();
            if (!urlHost.equals("")) {
                pageUrl.setHost(urlHost);
            }
            Integer urlPort = url.getPort();
            if (urlPort != -1) {
                pageUrl.setPort(urlPort);
            }
        }
        T uiContainerCandidate = (T) getIfCurrent(uiContainer.getClass());
        if (uiContainerCandidate != null) {
            if (!uiContainerCandidate.isInitialized()) {
                return (T) open((BasePage) uiContainer);
            }
            return uiContainerCandidate;
        }
        if (uiContainerAnalizer.isPage(uiContainer)) {
            DefaultUrlFactory defaultUrlFactory = new DefaultUrlFactory();
            defaultUrlFactory.setDefaultUrlToPage((BasePage) uiContainer);
            return (T) open((BasePage) uiContainer);
        }
        if (uiContainerAnalizer.isBlock(uiContainer)) {
            return onOpened(uiContainer);
        }
        throw new NotUIContainerException(uiContainer.getClass().getName());
    }

    @Step
    public <T extends UIContainer> T onOpened(T uiContainer) {
        uiContainer.initialize(getDriver());
        setCurrent(uiContainer);
        return uiContainer;
    }

    @Step
    public <T extends BasePage> T open(T page) {
        openPageUrl(page.getUrl());
        page.initialize(getDriver());
        setCurrent(page);
        return page;
    }

    public void close() {
        getDriver().quit();
    }

    private void setCurrent(UIContainer uiContainer) {
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

    public boolean isOn(Class<? extends UIContainer> klass) {
        if (isCurrent(klass)) {
            return true;
        } else {
            DefaultUrlFactory defaultUrlFactory = new DefaultUrlFactory();
            Url pageUrl = defaultUrlFactory.getDefaultUrlOfPage(klass);
            String currentUrl = getCurrentUrl();
            Pattern pattern = Pattern.compile(pageUrl.getProtocol() + "(.*)" + pageUrl.getPrefix() + "(.*)" + pageUrl.getPostfix());
            Matcher matcher = pattern.matcher(currentUrl);
            return matcher.find();
        }
    }

    public String getCurrentUrl() {
        return getDriver().getCurrentUrl();
    }

    public String getCurrentTitle() {
        return getDriver().getTitle();
    }

    @Override
    public String toString() {
        return name;
    }
    
    
}
