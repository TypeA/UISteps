package com.livejournal.uisteps.thucydides;

import net.thucydides.core.annotations.Step;
import org.junit.Assert;

/**
 *
 * @author ASolyankin
 */
public class Verification {

    private boolean condition;

    public ActualResult verifyThat(boolean condition) {
        this.condition = condition;
        return new ActualResult();
    }

    @Step
    public void actualResult(String errorMessage) {
        Assert.assertTrue(condition);
    }

    public class ActualResult {

        public void otherwiseShowMessage(String errorMessage) {
            if (!condition) {
                actualResult(errorMessage);
            }
        }
    }

}
