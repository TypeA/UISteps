package com.livejournal.uisteps.thucydides;

import java.util.ArrayList;
import java.util.List;
import net.thucydides.core.annotations.Step;

/**
 *
 * @author m.prytkova
 */
public class AddTables {

    public class Condition {

        public final String page;
        public final String important;
        public final String other;

        public Condition(String page, String important, String other) {
            this.page = page;
            this.important = important;
            this.other = other;
        }
    }

    public PageOpen addTable() {
        return new PageOpen(new ArrayList<>());
    }

    public class PageOpen {

        private final List<Condition> conditions;

        public PageOpen(List<Condition> conditions) {
            this.conditions = conditions;
        }

        public ImportantErrors pageOpen(String page) {
            return new ImportantErrors(conditions, page);
        }

    }

    public class ImportantErrors {

        private final List<Condition> conditions;
        private final String page;

        public ImportantErrors(List<Condition> conditions, String page) {
            this.conditions = conditions;
            this.page = page;

        }

        public OtherErrors importantErrors(String important) {
            return new OtherErrors(conditions, page, important);
        }
    }

    public class OtherErrors {

        private final List<Condition> conditions;
        private final String page;
        private final String important;

        public OtherErrors(List<Condition> conditions, String page, String important) {
            this.conditions = conditions;
            this.page = page;
            this.important = important;
        }

        public Result otherErrors(String other) {
            conditions.add(new Condition(page, important, other));
            return new Result(conditions);
        }
    }

    public class Result {

        private final List<Condition> conditions;

        public Result(List<Condition> conditions) {
            this.conditions = conditions;
        }

        public PageOpen and() {
            return new PageOpen(conditions);
        }

        public void finish() {

            String resultMessage = "";
            for (Condition condition : conditions) {
                
                resultMessage = "<table border='1' cellpadding='2'>"
                        + "<caption>Page " + condition.page + "</caption>"
                        + "<tr>"
                        + "<th>SEVERE</th>"
                        + "<th>WARNING</th></tr>";

                resultMessage += "<tr><td>" + condition.important + "</td>";
                resultMessage += "<td>" + condition.other + "</td></tr>";

            }
            resultMessage += "</table>";
            /*  + "<script>"
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
             + "</script>";*/

            verifications(resultMessage);
        }
    }

    public class ResultCondition {

        private final String resultMessage;

        public ResultCondition(String resultMessage) {
            this.resultMessage = resultMessage;
        }

        @Override
        public String toString() {
            return resultMessage;
        }

    }

    @Step
    public void verifications(String resultMessage) {
        new ResultCondition(resultMessage);
    }

}
