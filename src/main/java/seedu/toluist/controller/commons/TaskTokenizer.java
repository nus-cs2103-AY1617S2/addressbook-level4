//@@author Melvin
package seedu.toluist.controller.commons;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Tokenize task into index, description, start date and/or end date.
 */
public class TaskTokenizer {
    public static final String TASK_VIEW_INDEX = "index";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_DEADLINE_KEYWORD = "by/";
    public static final String EVENT_START_DATE_KEYWORD = "from/";
    public static final String EVENT_END_DATE_KEYWORD = "to/";
    public static final String TASK_TAGS_KEYWORD = "tags/";
    public static final String TASK_FLOATING_KEYWORD = "floating/";
    public static final String TASK_PRIORITY_KEYWORD = "priority/";

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
            String description = matcher.group(TASK_DESCRIPTION);
            HashMap<String, String> descriptionTokens = KeywordTokenizer.tokenize(
                                                            description,
                                                            TASK_DESCRIPTION,
                                                            EVENT_START_DATE_KEYWORD,
                                                            EVENT_END_DATE_KEYWORD,
                                                            TASK_DEADLINE_KEYWORD,
                                                            TASK_TAGS_KEYWORD,
                                                            TASK_FLOATING_KEYWORD,
                                                            TASK_PRIORITY_KEYWORD);
            tokens.putAll(descriptionTokens);
        }
        return tokens;
    }
}
