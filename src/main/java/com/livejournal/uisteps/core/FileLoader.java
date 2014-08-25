package com.livejournal.uisteps.core;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

/**
 *
 * @author ASolyankin
 */
public class FileLoader {

    public void uploadToFileInput(String path, WebElement element, WebDriver driver) {
        uploadToFileInput(new File(path), element, driver);
    }

    public void uploadToFileInput(File file, WebElement element, WebDriver driver) {
        if (driver instanceof JavascriptExecutor) {
            String script = "var element = arguments[0];"
                    + "element.style.display='block';";
            ((JavascriptExecutor) driver).executeScript(script, element);
        } else {
            throw new AssertionError("Current driver cannot execute javascript!");
        }
        element.sendKeys(file.getAbsolutePath());
    }
    /*
     public void uploadToFileInput(String path, By locator, String l) {
     uploadToFileInput(new File(path), locator, l);
     }

     public void uploadToFileInput(File file, By locator, String l) {
     WebElement element = driver.findElement(locator);
     if (driver instanceof JavascriptExecutor) {

     //locator.getClass().getDeclaredField("selector").get(locator)
     ((JavascriptExecutor) driver).executeScript("jQuery('" + l + "').css('display', 'blok')");
     } else {
     throw new AssertionError("Current driver cannot execute javascript!");
     }
     element.sendKeys(file.getAbsolutePath());
     }

     public void uploadToFileInput(String path, By locator, String l) {
     uploadToFileInput(new File(path), locator, l);
     }
     */
}
