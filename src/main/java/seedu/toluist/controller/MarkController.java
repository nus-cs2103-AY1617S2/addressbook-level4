//@@author A0131125Y
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.atteo.evo.inflector.English;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.CollectionUtil;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Mark Controller is responsible for marking task complete or incomplete
 */
public class MarkController extends Controller {
    private static final String RESULT_MESSAGE_COMPLETED_SUCCESS = "%s %s marked completed";
    private static final String RESULT_MESSAGE_INCOMPLETE_SUCCESS = "%s %s marked incomplete";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*mark(\\s+(complete|incomplete))?"
            + "(\\s+(?<index>.*))?(\\s+(complete|incomplete))?.*";
    private static final String COMMAND_WORD = "mark";

    private static final String PARAMETER_INDEX = "index";
    private static final String PARAMETER_MARK_COMPLETE = "complete";
    private static final String PARAMETER_MARK_INCOMPLETE = "incomplete";
    private static final Logger logger = LogsCenter.getLogger(MarkController.class);

    //@@author A0162011A
    private static final String HELP_DETAILS = "Marks a task to be complete or incomplete.";
    private static final String HELP_FORMAT = "mark [complete/incomplete] INDEX(ES)";
    private static final String[] HELP_COMMENTS = { "Using complete as a parameter will mark the selected "
                                                        + "task(s) as complete.",
                                                    "Using incomplete as a parameter will mark the selected "
                                                        + "task(s) as incomplete.",
                                                    "Using neither will default the command to mark as complete.",
                                                    "Supports marking of multiple indexes in a single command." };
    private static final String[] HELP_EXAMPLES = { "`mark 1`\nMarks the task at index 1 complete.",
                                                    "`mark incomplete 2`\nMarks the task at index 2 incomplete.",
                                                    "`mark complete 3`\nMarks the task at index 3 complete.",
                                                    "`mark -4`\nMarks the tasks up to index 4 complete.",
                                                    "`mark 5-`\nMarks the tasks starting from index 5 complete.",
                                                    "`mark 1, 6`\nMarks the tasks at index 1 and 6 complete." };

    //@@author A0131125Y
    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().toString() + " will handle command");
        String indexToken = tokens.get(PARAMETER_INDEX);
        boolean isMarkComplete = !tokens.keySet().contains(PARAMETER_MARK_INCOMPLETE);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, uiStore.getShownTasks().size());

        validateInvalidIndexes(indexes);
        mark(isMarkComplete, indexes);
    }

    private void validateInvalidIndexes(List<Integer> indexes) throws InvalidCommandException {
        if (indexes.isEmpty()) {
            throw new InvalidCommandException(Messages.MESSAGE_INVALID_TASK_INDEX);
        }
    }

    private void mark(boolean isMarkComplete, List<Integer> indexes) throws InvalidCommandException {
        CommandResult commandResult = mark(indexes, isMarkComplete);

        TodoList todoList = TodoList.getInstance();
        if (!todoList.save()) {
            throw new InvalidCommandException(Messages.MESSAGE_SAVING_FAILURE);
        }
        uiStore.setTasks(todoList.getTasks());
        uiStore.setCommandResult(commandResult);
    }

    private CommandResult mark(List<Integer> taskIndexes, boolean isCompleted) {
        ArrayList<Task> tasks = uiStore.getShownTasks(taskIndexes);
        for (Task task : tasks) {
            if (task.canUpdateToNextRecurringTask()) {
                task.updateToNextRecurringTask();
            } else {
                task.setCompleted(isCompleted);
            }
        }
        String indexString = CollectionUtil.getStringRepresentation(StringUtil.COMMA_DELIMITER, taskIndexes);
        String messageTemplate = isCompleted
                ? RESULT_MESSAGE_COMPLETED_SUCCESS
                : RESULT_MESSAGE_INCOMPLETE_SUCCESS;
        return new CommandResult(String.format(messageTemplate,
                English.plural(StringUtil.capitalize(StringUtil.WORD_TASK), taskIndexes.size()), indexString));
    }

    public Map<String, String> tokenize(String command) {
        Map<String, String> tokens = super.tokenize(command);
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        tokens.put(PARAMETER_INDEX, matcher.group(PARAMETER_INDEX));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public Map<String, String[]> getCommandKeywordMap() {
        String[] keywords = new String[] { PARAMETER_MARK_COMPLETE, PARAMETER_MARK_INCOMPLETE };
        HashMap<String, String[]> keywordMap = new HashMap<>();
        for (String keyword : keywords) {
            keywordMap.put(keyword, new String[0]);
        }
        return keywordMap;
    }

    public String[][][] getConflictingKeywordsList() {
        return new String[][][] { new String[][] {
            new String[] { PARAMETER_MARK_INCOMPLETE },
            new String[] { PARAMETER_MARK_COMPLETE }
        }};
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
