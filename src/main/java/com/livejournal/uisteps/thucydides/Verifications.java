package com.livejournal.uisteps.thucydides;

import com.livejournal.uisteps.core.Browser;
import com.livejournal.uisteps.core.Url;
import com.livejournal.uisteps.thucydides.elements.Page;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.thucydides.core.annotations.Step;
import org.junit.Assert;

/**
 *
 * @author ASolyankin
 */
public class Verifications {

    private Browser browser;

    public void init(Browser browser) {
        this.browser = browser;
    }

    public class Condition {

        public final boolean value;
        public final String expectedDescription;
        public final String actualDescription;

        public Condition(boolean value, String expectedDescription, String actualDescription) {
            this.value = value;
            this.expectedDescription = expectedDescription;
            this.actualDescription = actualDescription;
        }
    }

    public That verify() {
        return new That(new ArrayList<>());
    }

    public class That {

        private final List<Condition> conditions;

        public That(List<Condition> conditions) {
            this.conditions = conditions;
        }

        public ExpectedResult that(boolean condition) {
            return new ExpectedResult(conditions, condition);
        }

        public And thatIsOn(Class<? extends Page> pageClass) {
            String currentUrl = browser.getCurrentUrl();
            boolean condition = browser.isOn(pageClass);
            conditions.add(new Condition(condition, NameConvertor.humanize(pageClass) + " is opened",
                    "Unexpected page by url <a href='" + currentUrl + "'>" + currentUrl + "</a> is opened."));
            return new And(conditions);
        }

    }

    public class ExpectedResult {

        private final List<Condition> conditions;
        private final boolean condition;

        public ExpectedResult(List<Condition> conditions, boolean condition) {
            this.conditions = conditions;
            this.condition = condition;

        }

        public ActualResult ifResultIsExpected(String expectedDescription) {
            return new ActualResult(conditions, condition, expectedDescription);
        }
    }

    public class ActualResult {

        private final List<Condition> conditions;
        private final boolean condition;
        private final String expectedDescription;

        public ActualResult(List<Condition> conditions, boolean condition, String expectedDescription) {
            this.conditions = conditions;
            this.condition = condition;
            this.expectedDescription = expectedDescription;
        }

        public And ifElse(String actualDescription) {
            conditions.add(new Condition(condition, expectedDescription, actualDescription));
            return new And(conditions);
        }
    }

    public class And {

        private final List<Condition> conditions;

        public And(List<Condition> conditions) {
            this.conditions = conditions;
        }

        public That and() {
            return new That(conditions);
        }

        public void finish() {
            boolean resultCondition = true;
            String resultMessage = "<table border='1' cellpadding='2'>"
                    + "<tr>"
                    + "<th>Expected result</th>"
                    + "<th class='actual-result'>Actual result</th>"
                    + "<th>Status</th></tr>";
            for (Condition condition : conditions) {
                boolean expectedCondition = condition.value;
                resultCondition &= expectedCondition;
                resultMessage += "<tr><td>" + condition.expectedDescription + "</td><td class='actual-result'>";
                if (expectedCondition) {
                    resultMessage += "</td><td>SUCCESS</td></tr>";
                } else {
                    resultMessage += condition.actualDescription + "</td><td>FAILURE</td></tr>";
                }
            }
            resultMessage += "</table>"
                    + "<script>"
                    + " var flag = true;"
                    + " var resultsTable = $('table').last();"
                    + " resultsTable.find('td.actual-result').each(function() {"
                    + "     if( $(this).text() !== '') {"
                    + "         flag = false;"
                    + "     }"
                    + "     return flag;"
                    + " });"
                    + "     if(flag) {"
                    + "     resultsTable.find('.actual-result').css('display','none');"
                    + "     }"
                    + "</script>";

            verifications(new ResultCondition(resultCondition, resultMessage));
        }
    }

    public class ResultCondition {

        private final boolean resultCondition;
        private final String resultMessage;

        public ResultCondition(boolean resultCondition, String resultMessage) {
            this.resultCondition = resultCondition;
            this.resultMessage = resultMessage;
        }

        @Override
        public String toString() {
            return resultMessage;
        }

    }

    @Step
    public void verifications(ResultCondition resultCondition) {
        Assert.assertTrue(resultCondition.resultCondition);
    }
}
