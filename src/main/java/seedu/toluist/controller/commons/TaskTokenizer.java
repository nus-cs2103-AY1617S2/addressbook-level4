package seedu.toluist.controller.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Task;
import seedu.toluist.ui.UiStore;

/**
 * Tokenize task into index, description, start date and/or end date.
 */
public class TaskTokenizer {
    private String commandTemplate;

    public static final int START_INDEX = 0;
    public static final int INVALID_INDEX = -1;

    public static final String TASK_VIEW_INDEX = "index";
    public static final String TASK_VIEW_INDEXES = "indexes";
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

    public List<Integer> splitIndexes(String stringIndexes, int todoListSize) {
        // Prepare stringIndexes in the correct format to be processed
        // Correct format example: ["2", "-", "5", "7", "11", "-", "13", "15"]
        String processedStringIndexes = stringIndexes.replaceAll("-", " - ");
        String[] splittedStringIndexes = processedStringIndexes.split(" |\\,");
        splittedStringIndexes = Arrays.
                stream(splittedStringIndexes).
                filter(s -> !s.isEmpty()).
                toArray(String[]::new);
        for (String splittedStringIndex: splittedStringIndexes) {
            System.out.print(" " + splittedStringIndex);
        }
        System.out.println("");

        // Process formatted stringIndexes
        List<Integer> indexes = new ArrayList<Integer>();
        int i = 0;
        while (i < splittedStringIndexes.length) {
            String splittedStringIndex = splittedStringIndexes[i];

            if (StringUtil.isUnsignedInteger(splittedStringIndex)) {

                int index = Integer.valueOf(splittedStringIndex);
                if (index <= todoListSize) {
                    indexes.add(Integer.valueOf(splittedStringIndex));
                    i += 1;
                } else {
                    // Invalid state, early termination
                    return indexes;
                }

            } else if (splittedStringIndex.equals("-")) {

                // If stringIndexes starts with "-", the startIndex will be 0;
                int startIndex = (indexes.isEmpty()) ? 0 : indexes.get(indexes.size() - 1);
                // If stringIndexes ends with "-", the endIndex will be todoListSize
                int endIndex = todoListSize;
                if (i + 1 < splittedStringIndexes.length
                        && StringUtil.isUnsignedInteger(splittedStringIndexes[i + 1])) {
                    endIndex = Integer.valueOf(splittedStringIndexes[i + 1]);
                } else if (i + 1 > splittedStringIndexes.length) {
                    // Invalid state, early termination
                    return indexes;
                }
                for (int value = startIndex + 1; value <= Integer.min(endIndex, todoListSize); value++) {
                    indexes.add(value);
                }
                i += 2;

            } else {
                // Invalid state, early termination
                return indexes;
            }
        }
        return indexes;
    }

    public List<Task> getTasks(List<Integer> indexes) {
        List<Task> tasks = new ArrayList<Task>();
        for (int index: indexes) {
            System.out.print(" " + index);
            tasks.add(UiStore.getInstance().getTasks().get(index - 1));
        }
        System.out.println("");
        return tasks;
    }

    public Task getTask(String indexToken) {
        int index = indexToken == null ? INVALID_INDEX : Integer.parseInt(indexToken) - 1;
        Task task = indexToken == null ? null : UiStore.getInstance().getTasks().get(index);
        return task;
    }
}
