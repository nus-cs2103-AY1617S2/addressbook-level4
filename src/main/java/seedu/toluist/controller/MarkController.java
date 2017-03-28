//@@author A0131125Y
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.atteo.evo.inflector.English;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.core.Messages;
import seedu.toluist.commons.util.CollectionUtil;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Mark Controller is responsible for marking task complete or incomplete
 */
public class MarkController extends Controller {
    private static final String RESULT_MESSAGE_COMPLETED_SUCCESS = "%s %s marked completed";
    private static final String RESULT_MESSAGE_INCOMPLETE_SUCCESS = "%s %s marked incomplete";
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*mark(\\s+(?<markType>(complete|incomplete)))" +
            "?(?<index>.*)?\\s*";
    private static final String COMMAND_WORD = "mark";

    private static final String PARAMETER_MARK = "markType";
    private static final String PARAMETER_INDEX = "index";
    private static final String PARAMETER_MARK_COMPLETE = "complete";
    private static final String PARAMETER_MARK_INCOMPLETE = "incomplete";
    private static final Logger logger = LogsCenter.getLogger(MarkController.class);

    public void execute(String command) {
        logger.info(getClass().toString() + " will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String indexToken = tokens.get(PARAMETER_INDEX);
        String markTypeToken = tokens.get(PARAMETER_MARK);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken,
                UiStore.getInstance().getShownTasks().size());

        if (indexes.isEmpty()) {
            uiStore.setCommandResult(new CommandResult(Messages.MESSAGE_INVALID_TASK_INDEX));
            return;
        }

        CommandResult commandResult;
        if (Objects.equals(markTypeToken, PARAMETER_MARK_INCOMPLETE)) {
            commandResult = mark(indexes, false);
        } else {
            commandResult = mark(indexes, true);
        }

        TodoList todoList = TodoList.getInstance();
        if (!todoList.save()) {
            uiStore.setCommandResult(new CommandResult(Messages.MESSAGE_SAVING_FAILURE));
        }
        UiStore.getInstance().setTasks(todoList.getTasks());
        uiStore.setCommandResult(commandResult);
    }

    private CommandResult mark(List<Integer> taskIndexes, boolean isCompleted) {
        ArrayList<Task> tasks = UiStore.getInstance().getShownTasks(taskIndexes);
        for (Task task : tasks) {
            if (task.canUpdateToNextRecurringTask()) {
                task.updateToNextRecurringTask();
            } else {
                task.setCompleted(isCompleted);
            }
        }
        String indexString = CollectionUtil.toString(", ", taskIndexes);
        String messageTemplate = isCompleted
                ? RESULT_MESSAGE_COMPLETED_SUCCESS
                : RESULT_MESSAGE_INCOMPLETE_SUCCESS;
        return new CommandResult(String.format(messageTemplate,
                English.plural("Task", taskIndexes.size()), indexString));
    }

    public HashMap<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_MARK, matcher.group(PARAMETER_MARK));
        tokens.put(PARAMETER_INDEX, matcher.group(PARAMETER_INDEX));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
