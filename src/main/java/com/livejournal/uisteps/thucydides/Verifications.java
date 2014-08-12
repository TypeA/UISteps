package com.livejournal.uisteps.thucydides;

import java.util.ArrayList;
import java.util.List;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

/**
 *
 * @author ASolyankin
 */
public class Verifications {

    private ExpectedResults expectedResults;

    public SingleExpectedResult verifyExpectedResult(String description, boolean condition) {
        return new SingleExpectedResult(description, condition);
    }

    public ExpectedResults verify() {
        expectedResults = new ExpectedResults();
        return expectedResults;
    }

    @Step
    public void verifyExpectedResult(SingleExpectedResult expectedResult) {
        Assert.assertTrue("Expected result doesn't match actual result: " + expectedResult.actualDescription,
                expectedResult.condition);
    }

    @Step
    public void verifications(String resultMessage) {
        Assert.assertTrue(expectedResults.resultCondition);
    }

    public class ExpectedResult {

        protected final String expectedDescription;
        protected String actualDescription;
        protected final boolean condition;

        public ExpectedResult(String description, boolean condition) {
            expectedDescription = description;
            this.condition = condition;
        }

        @Override
        public String toString() {
            return expectedDescription;
        }

    }

    public class SingleExpectedResult extends ExpectedResult {

        public SingleExpectedResult(String description, boolean condition) {
            super(description, condition);
        }

        public void showMessageIfVerificationFailed(String description) {
            actualDescription = description;
            verifyExpectedResult(this);
        }

    }

    public class OneOfExpectedResults extends ExpectedResult {

        public OneOfExpectedResults(String description, boolean condition) {
            super(description, condition);
        }

        public And showMessageIfVerificationFailed(String description) {
            actualDescription = description;
            return new And();
        }

    }

    public class ExpectedResults {

        private final List<OneOfExpectedResults> expectedResultList = new ArrayList<>();
        private boolean resultCondition = true;
        private final String resultMessage = "<table border='1' cellpadding='2'>"
                + "<tr>"
                + "<th>Expected result</th>"
                + "<th class='actual-result'>Actual result</th>"
                + "<th>Status</th></tr>";

        public OneOfExpectedResults expectedResult(String description, boolean condition) {
            OneOfExpectedResults expectedResult = new OneOfExpectedResults(description, condition);
            expectedResultList.add(expectedResult);
            return expectedResult;
        }
    }

    public class And {

        public ExpectedResults and() {
            return expectedResults;
        }

        public void finish() {
            String resultMessage = expectedResults.resultMessage;
            for (OneOfExpectedResults expectedResult : expectedResults.expectedResultList) {
                boolean expectedCondition = expectedResult.condition;
                expectedResults.resultCondition &= expectedCondition;
                String expectedDescription = expectedResult.expectedDescription;
                if (expectedCondition) {
                    resultMessage += "<tr><td>" + expectedDescription + "</td><td class='actual-result'></td><td>SUCCESS</td></tr>";
                } else {
                    resultMessage += "<tr><td>" + expectedDescription + "</td><td class='actual-result'>" + expectedResult.actualDescription + "</td><td>FAILURE</td></tr>";
                }
            }
            resultMessage += "</table>"
                    + "<script>"
                    + " var flag = true;"
                    + " var resultsTable = $('table').last();"
                    + " resultsTable.find('td.actual-result').each(function() {"
                    + "     if( $(this).text() !== '') {"
                    + "     flag = false;"
                    + "     }"
                    + "     return flag;"
                    + " });"
                    + " if(flag) {"
                    + "     resultsTable.find('.actual-result').css('display','none');"
                    + " }"
                    + "</script>";

            verifications(resultMessage);
        }
    }

}
