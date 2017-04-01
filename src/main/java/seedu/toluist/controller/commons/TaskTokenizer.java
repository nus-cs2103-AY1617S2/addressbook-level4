//@@author A0127545A
package seedu.toluist.controller.commons;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenize task into index, description, start date and/or end date.
 */
public class TaskTokenizer {
    public static final String TASK_VIEW_INDEX = "index";
    public static final String PARAMETER_TASK_DESCRIPTION = "description";
    public static final String KEYWORD_TASK_DEADLINE = "/by";
    public static final String KEYWORD_EVENT_START_DATE = "/from";
    public static final String KEYWORD_EVENT_END_DATE = "/to";
    public static final String KEYWORD_TASK_TAGS = "/tags";
    public static final String KEYWORD_TASK_FLOATING = "/floating";
    public static final String KEYWORD_TASK_PRIORITY = "/priority";
    public static final String KEYWORD_TASK_RECURRING_FREQUENCY = "/repeat";
    public static final String KEYWORD_TASK_RECURRING_UNTIL_END_DATE = "/repeatuntil";
    public static final String KEYWORD_TASK_STOP_RECURRING = "/stoprepeating";

    public static HashMap<String, String> tokenize(String commandTemplate, String commandArgs,
            boolean hasIndex, boolean hasDescription) {
        Pattern pattern = Pattern.compile(commandTemplate);
        Matcher matcher = pattern.matcher(commandArgs.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        if (hasIndex) {
            tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        }
        if (hasDescription) {
            String description = matcher.group(PARAMETER_TASK_DESCRIPTION);
            HashMap<String, String> descriptionTokens = KeywordTokenizer.tokenize(
                    description,
                    PARAMETER_TASK_DESCRIPTION,
                    KEYWORD_EVENT_START_DATE,
                    KEYWORD_EVENT_END_DATE,
                    KEYWORD_TASK_DEADLINE,
                    KEYWORD_TASK_TAGS,
                    KEYWORD_TASK_FLOATING,
                    KEYWORD_TASK_PRIORITY,
                    KEYWORD_TASK_RECURRING_FREQUENCY,
                    KEYWORD_TASK_RECURRING_UNTIL_END_DATE,
                    KEYWORD_TASK_STOP_RECURRING);
            tokens.putAll(descriptionTokens);
        }
        return tokens;
    }
}
