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
        public final ArrayList<String> important;
        public final ArrayList<String> other;

        public Condition(String page, ArrayList<String> important, ArrayList<String> other) {
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

        public OtherErrors importantErrors(ArrayList<String> important) {
            return new OtherErrors(conditions, page, important);
        }
    }

    public class OtherErrors {

        private final List<Condition> conditions;
        private final String page;
        private final ArrayList<String> important;

        public OtherErrors(List<Condition> conditions, String page, ArrayList<String> important) {
            this.conditions = conditions;
            this.page = page;
            this.important = important;
        }

        public Result otherErrors(ArrayList<String> other) {
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
                int size = condition.important.size();
                resultMessage += "<table border='1' cellpadding='2'>"
                        + "<tr>"
                        + "<th>SEVERE</th>";

                if (condition.other != null) {
                    resultMessage += "<th width='350'>WARNING</th></tr>";
                    if (size < condition.other.size()) {
                        size = condition.other.size();
                    }
                }

                resultMessage += "<caption>Page " + condition.page + "</caption>";

                for (int i = 0; i < size; i++) {
                   
                    if (i < condition.important.size()) {
                        resultMessage += "<tr><td>" + condition.important.get(i) + "</td>";
                    } else {
                        resultMessage += "<tr><td></td>";
                    }
                    
                    if (condition.other != null) {
                        if (i < condition.other.size()) {
                            resultMessage += "<td width='350'>" + condition.other.get(i) + "</td></tr>";
                        } else {
                            resultMessage += "<td width='350'></td></tr>";
                        }
                    } else {
                        resultMessage += "</tr>";
                    }
                }
                resultMessage += "</table>";
            }

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
