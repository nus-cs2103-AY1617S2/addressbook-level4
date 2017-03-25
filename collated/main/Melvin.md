# Melvin
###### /java/seedu/toluist/commons/util/DateTimeUtil.java
``` java
    public static LocalDateTime parseDateString(String stringDate) {
        if (stringDate == null) {
            return null;
        }
        Parser parser = new Parser();
        List<DateGroup> dateGroups = parser.parse(stringDate);
        if (dateGroups.isEmpty()) {
            return null;
        }
        DateGroup dateGroup = dateGroups.get(0);
        List<Date> dates = dateGroup.getDates();
        if (dates.isEmpty()) {
            return null;
        }
        Date date = dates.get(0);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

```
###### /java/seedu/toluist/controller/AddTaskController.java
``` java
package seedu.toluist.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * AddTaskController is responsible for adding a task (and event)
 */
public class AddTaskController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(AddTaskController.class);

    private static final String COMMAND_TEMPLATE = "^add"
            + "(\\s+(?<description>.+))?";

    private static final String COMMAND_ADD_TASK = "add";

    private static final String RESULT_MESSAGE_ADD_TASK = "New task added";

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String startDateToken = tokens.get(TaskTokenizer.TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.parseDateString(startDateToken);

        String endDateToken = tokens.get(TaskTokenizer.TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.parseDateString(endDateToken);

        String tagsToken = tokens.get(TaskTokenizer.TASK_TAGS_KEYWORD);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        commandResult = add(todoList, description, startDateTime, endDateTime, tags);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        return commandResult;
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, false, true);
    }

    private CommandResult add(TodoList todoList, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Tag> tags) {
        Task task = new Task(description, startDateTime, endDateTime);
        task.replaceTags(tags);
        todoList.add(task);
        return new CommandResult(RESULT_MESSAGE_ADD_TASK);
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_ADD_TASK };
    }
}
```
###### /java/seedu/toluist/controller/commons/IndexParser.java
``` java
package seedu.toluist.controller.commons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import seedu.toluist.commons.util.StringUtil;

/**
 * Parse string of indexes into a list of integer index.
 */
public class IndexParser {
    private static final String HYPHEN = "-";

    /**
     * Split a string of unprocessed indexes into a list.
     * This function splits the list by comma (","), then parse each substring to get a list of index.
     * @return the list of unique indexes in sorted order (which is the union of all the parsed substring)
     */
    public static List<Integer> splitStringToIndexes(String indexToken, int maxIndex) {
        String[] splittedStringIndexes = indexToken.split(",");
        Set<Integer> indexes = new TreeSet<Integer>();
        for (String splittedStringIndex : splittedStringIndexes) {
            List<Integer> splittedIndexes = parseIndexes(splittedStringIndex, maxIndex);
            for (int splittedIndex : splittedIndexes) {
                indexes.add(splittedIndex);
            }
        }
        return new ArrayList<Integer>(indexes);
    }

    /**
     * Splits a string of indexes into a list.
     *   Examples:
     *   splitIndexes(" - 3",           8) -> [1, 2, 3]
     *   splitIndexes(" 3 -",           8) -> [3, 4, 5, 6, 7, 8]
     *   splitIndexes("3 4  5",         8) -> [3, 4, 5]
     *   splitIndexes("3 - 5",          8) -> [3, 4, 5]
     *   splitIndexes("- 3 5 7 - 12",   8) -> [1, 2, 3, 5, 7, 8]
     * This function is robust enough to handle excessive white spaces (" ").
     * @param stringIndexes, the unprocessed string of indexes from user's input
     * @param maxIndex, the maximum possible index number
     * @return a list of integer containing all valid indexes
     */
    private static List<Integer> parseIndexes(String stringIndexes, int maxIndex) {
        // Prepare stringIndexes in the correct format to be processed
        // Correct format example: ["2", "-", "5", "7", "11", "-", "13", "15"]
        String processedStringIndexes = stringIndexes.replaceAll(HYPHEN, " - ");
        String[] splittedStringIndexes = processedStringIndexes.split(" ");
        splittedStringIndexes = Arrays.
                stream(splittedStringIndexes).
                filter(s -> !s.isEmpty()).
                toArray(String[]::new);

        // Process formatted stringIndexes
        List<Integer> indexes = new ArrayList<Integer>();
        int i = 0;
        while (i < splittedStringIndexes.length) {
            String splittedStringIndex = splittedStringIndexes[i];
            if (!StringUtil.isPositiveInteger(splittedStringIndex) && !splittedStringIndex.equals(HYPHEN)) {
                // Invalid state, early termination
                return indexes;
            }
            if (StringUtil.isPositiveInteger(splittedStringIndex)) {
                int index = Integer.valueOf(splittedStringIndex);
                if (index > maxIndex) {
                    // Invalid state, early termination
                    return indexes;
                }
                indexes.add(Integer.valueOf(splittedStringIndex));
                i++;
            } else if (splittedStringIndex.equals(HYPHEN)) {
                // If stringIndexes starts with "-", the startIndex will be 0
                int startIndex = (indexes.isEmpty()) ? 0 : indexes.get(indexes.size() - 1);
                // If stringIndexes ends with "-", the endIndex will be maxIndex
                int endIndex = maxIndex;
                if (i + 1 > splittedStringIndexes.length
                    || (i + 1 < splittedStringIndexes.length
                    && !StringUtil.isPositiveInteger(splittedStringIndexes[i + 1]))) {
                    // Invalid state, early termination
                    return indexes;
                }
                // Valid states: Negation of the above if-statement, one of the following 2 cases must be true.
                // If (i + 1 == splittedStringIndexes.length) is true, let endIndex = maxIndex
                // If (next splittedStringIndex) is a positive integer, let endIndex = next splittedStringIndex.
                if (i + 1 < splittedStringIndexes.length) {
                    endIndex = Integer.valueOf(splittedStringIndexes[i + 1]);
                }
                for (int value = startIndex + 1; value <= Integer.min(endIndex, maxIndex); value++) {
                    indexes.add(value);
                }
                i += 2;
            }
        }
        return indexes;
    }

}
```
###### /java/seedu/toluist/controller/commons/KeywordTokenizer.java
``` java
package seedu.toluist.controller.commons;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import edu.emory.mathcs.backport.java.util.Collections;
import javafx.util.Pair;
import seedu.toluist.commons.util.StringUtil;

/**
 * Tokenize string of description by keywords
 */
public class KeywordTokenizer {
    public static final int INVALID_INDEX = -1;
    public static final int START_INDEX = 0;

    /**
     * Tokenize a string description into their respective keywords (by best effort matching)
     * @param description is the full text supplied by the user to be tokenized
     * @param defaultKeyword is for the rest of the text that did not get tokenized by any user-specified keywords
     * @param keywords is the list of keywords to find and to tokenize
     * @return a HashMap of keyword-token pairs
     */
    public static HashMap<String, String> tokenize(String description, String defaultKeyword, String... keywords) {
        HashMap<String, String> tokens = new HashMap<>();
        if (!StringUtil.isPresent(description)) {
            // Early termination, no description means there is nothing to tokenize.
            return tokens;
        }

        ArrayList<Pair<Integer, String>> indexKeywordPairs = new ArrayList<>();
        String[] nonNullKeywords = keywords == null ? new String[] {} : keywords;
        if (defaultKeyword != null) {
            // Everything that is not matched (guaranteed to be at the left) will be tokenized to the default keyword
            indexKeywordPairs.add(new Pair<>(START_INDEX, defaultKeyword));
        }

        for (String keyword : nonNullKeywords) {
            int index = description.lastIndexOf(keyword);
            if (index != INVALID_INDEX) {
                // Index in indexKeywordPairs refers to the index behind the last character of the keyword.
                Pair<Integer, String> indexKeywordPair = new Pair<>(index + keyword.length(), keyword);
                indexKeywordPairs.add(indexKeywordPair);
            }
        }

        Collections.sort(indexKeywordPairs, Comparator.comparing(pair -> ((Pair<Integer, String>) pair).getKey()));

        for (int i = 0; i < indexKeywordPairs.size(); i++) {
            Pair<Integer, String> currentIndexKeywordPair = indexKeywordPairs.get(i);
            int startIndex = currentIndexKeywordPair.getKey();
            // Generally, we match the text to the index before the first character of the next keyword.
            // For last pair of currentIndexKeywordPair, we simply match the text to the end of the description.
            int endIndex = i + 1 < indexKeywordPairs.size()
                    ? indexKeywordPairs.get(i + 1).getKey() - indexKeywordPairs.get(i + 1).getValue().length()
                    : description.length();
            String keyword = currentIndexKeywordPair.getValue();
            String token = description.substring(startIndex, endIndex).trim();
            tokens.put(keyword, token);
        }

        return tokens;
    }
}
```
###### /java/seedu/toluist/controller/commons/TagParser.java
``` java
package seedu.toluist.controller.commons;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.toluist.model.Tag;

/**
 * Parse string of tags into a set of tags.
 */
public class TagParser {
    private static final String TAGS_SEPARATOR_REGEX = " ";

    public static Set<Tag> parseTags(String tagsString) {
        String[] tagStrings = tagsString == null ? new String[] {} : tagsString.split(TAGS_SEPARATOR_REGEX);
        List<String> tagList = Arrays.asList(tagStrings);
        Set<Tag> tags = (Set<Tag>) tagList
                .stream()
                .filter(tagString -> !tagString.isEmpty())
                .map(tagString -> new Tag((String) tagString))
                .collect(Collectors.toSet());
        return tags;
    }
}
```
###### /java/seedu/toluist/controller/commons/TaskTokenizer.java
``` java
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
    public static final String TASK_START_DATE_KEYWORD = "startdate/";
    public static final String TASK_END_DATE_KEYWORD = "enddate/";
    public static final String TASK_TAGS_KEYWORD = "tags/";

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
                                                            TASK_START_DATE_KEYWORD,
                                                            TASK_END_DATE_KEYWORD,
                                                            TASK_TAGS_KEYWORD);
            tokens.putAll(descriptionTokens);
        }
        return tokens;
    }
}
```
###### /java/seedu/toluist/controller/DeleteTaskController.java
``` java
package seedu.toluist.controller;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * DeleteTaskController is responsible for deleting a task
 */
public class DeleteTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^delete"
            + "(\\s+(?<index>.+))?\\s*";

    private static final String COMMAND_DELETE_TASK = "delete";

    private static final String RESULT_MESSAGE_DELETE_TASK = "Deleted %s: %s";

    private static final Logger logger = LogsCenter.getLogger(DeleteTaskController.class);

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, todoList.getTasks().size());
        List<Task> tasks = uiStore.getShownTasks(indexes);
        commandResult = delete(todoList, tasks);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        return commandResult;
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, false);
    }

    private CommandResult delete(TodoList todoList, List<Task> tasks) {
        List<String> messages = tasks.
                                stream().
                                map(task -> delete(todoList, task).
                                        getFeedbackToUser()).
                                collect(Collectors.toList());
        return new CommandResult(String.join("\n", messages));
    }

    private CommandResult delete(TodoList todoList, Task task) {
        todoList.remove(task);
        String taskType = task.isEvent() ? "Event" : "Task";
        return new CommandResult(String.format(RESULT_MESSAGE_DELETE_TASK, taskType, task.getDescription()));
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_DELETE_TASK };
    }
}
```
###### /java/seedu/toluist/controller/UpdateTaskController.java
``` java
package seedu.toluist.controller;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.util.DateTimeUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.controller.commons.TagParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^update"
            + "(\\s+(?<index>\\d+))?"
            + "(\\s+(?<description>.+))?\\s*";

    private static final String COMMAND_UPDATE_TASK = "update";

    private static final String RESULT_MESSAGE_UPDATE_TASK = "Task updated";

    private static final Logger logger = LogsCenter.getLogger(UpdateTaskController.class);

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TaskTokenizer.TASK_DESCRIPTION);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, uiStore.getShownTasks().size());
        Task task = uiStore.getShownTasks(indexes).get(0);

        String startDateToken = tokens.get(TaskTokenizer.TASK_START_DATE_KEYWORD);
        LocalDateTime startDateTime = DateTimeUtil.parseDateString(startDateToken);

        String endDateToken = tokens.get(TaskTokenizer.TASK_END_DATE_KEYWORD);
        LocalDateTime endDateTime = DateTimeUtil.parseDateString(endDateToken);

        String tagsToken = tokens.get(TaskTokenizer.TASK_TAGS_KEYWORD);
        Set<Tag> tags = TagParser.parseTags(tagsToken);

        commandResult = update(task, description, startDateTime, endDateTime, tags);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        return commandResult;
    }

    public HashMap<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, true);
    }

    private CommandResult update(Task task, String description,
            LocalDateTime startDateTime, LocalDateTime endDateTime, Set<Tag> tags) {
        if (StringUtil.isPresent(description)) {
            task.setDescription(description);
        }
        if (endDateTime != null) {
            task.setEndDateTime(endDateTime);
            if (startDateTime != null) {
                task.setStartDateTime(startDateTime);
            }
        }
        if (!tags.isEmpty()) {
            task.replaceTags(tags);
        }
        return new CommandResult(RESULT_MESSAGE_UPDATE_TASK);
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_UPDATE_TASK };
    }
}
```
###### /java/seedu/toluist/model/Task.java
``` java
    public Task(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.setDescription(description.trim());
        this.setStartDateTime(startDateTime);
        this.setEndDateTime(endDateTime);
        validate();
    }

    public void validate() {
        if (!validateDescriptionMustNotBeEmpty()) {
            throw new IllegalArgumentException("Description must not be empty.");
        }
        if (!validateStartDateMustBeBeforeEndDate()) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
        if (!validateTaskIsFloatingIsEventOrHasDeadline()) {
            throw new IllegalArgumentException("Task must be floating, must be an event, or has deadline,");
        }
    }

    public boolean validateDescriptionMustNotBeEmpty() {
        return description != null && !description.isEmpty();
    }

    public boolean validateStartDateMustBeBeforeEndDate() {
        if (startDateTime != null && endDateTime != null) {
            return startDateTime.isBefore(endDateTime);
        }
        return true;
    }

    public boolean validateTaskIsFloatingIsEventOrHasDeadline() {
        return startDateTime == null || endDateTime != null;
    }

```
###### /java/seedu/toluist/model/Task.java
``` java
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

```
