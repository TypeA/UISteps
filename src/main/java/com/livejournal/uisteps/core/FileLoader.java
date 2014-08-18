package com.livejournal.uisteps.core;

import java.io.File;
import org.openqa.selenium.WebElement;

/**
 *
 * @author ASolyankin
 */
public class FileLoader {
    
    public void upload(File file, WebElement element) {
        element.sendKeys(file.getAbsolutePath());       
    }   
}
