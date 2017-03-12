package seedu.toluist.controller.commons;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenize task into index, description, start date and/or end date.
 */
public class TaskTokenizer {
    private String commandTemplate;

    public static final int START_INDEX = 0;
    public static final int INVALID_INDEX = -1;

    public static final String TASK_VIEW_INDEX = "index";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_START_DATE_KEYWORD = "startdate/";
    public static final String TASK_END_DATE_KEYWORD = "enddate/";

    public TaskTokenizer(String commandTemplate) {
        this.commandTemplate = commandTemplate;
    }

    public HashMap<String, String> tokenize(String commandArgs, boolean hasIndex, boolean hasDescription) {
        Pattern pattern = Pattern.compile(commandTemplate);
        Matcher matcher = pattern.matcher(commandArgs.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        if (hasIndex) {
            tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        }
        if (hasDescription) {
            String description = matcher.group(TASK_DESCRIPTION);
            // TODO: Allow any ordering to still work, by extracting tokens more generally.
            int startDateIndex = description.lastIndexOf(TASK_START_DATE_KEYWORD);
            int endDateIndex = description.lastIndexOf(TASK_END_DATE_KEYWORD);
            String startDate = null;
            String endDate = null;
            if (endDateIndex != INVALID_INDEX) {
                // This is a task with deadline
                endDate = description.substring(endDateIndex + TASK_END_DATE_KEYWORD.length());
                description = description.substring(START_INDEX, endDateIndex);
                if (startDateIndex != INVALID_INDEX) {
                    // This is an event
                    startDate = description.substring(startDateIndex + TASK_START_DATE_KEYWORD.length());
                    description = description.substring(START_INDEX, startDateIndex);
                }
            } // Else this is a floating task
            // Note: We are not dealing with tasks that have only a start date.

            tokens.put(TASK_DESCRIPTION, description);
            tokens.put(TASK_START_DATE_KEYWORD, startDate);
            tokens.put(TASK_END_DATE_KEYWORD, endDate);
        }
        return tokens;
    }
}
