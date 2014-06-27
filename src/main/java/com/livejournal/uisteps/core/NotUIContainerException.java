package com.livejournal.uisteps.core;

/**
 *
 * @author ASolyankin
 */
public class NotUIContainerException extends RuntimeException {

    public NotUIContainerException(String objectName) {
        super(objectName + " is not a page or a block!");
    }
    
}
