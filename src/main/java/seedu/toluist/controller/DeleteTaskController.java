//@@author A0127545A
package seedu.toluist.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.controller.commons.IndexParser;
import seedu.toluist.controller.commons.TaskTokenizer;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.commons.CommandResult;

/**
 * DeleteTaskController is responsible for deleting a task
 */
public class DeleteTaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*delete"
            + "(\\s+(?<index>.+))?\\s*";

    private static final String COMMAND_DELETE_TASK = "delete";

    private static final String RESULT_MESSAGE_DELETE_TASK = "Deleted %s: %s";
    private static final String RESULT_MESSAGE_ERROR_NO_VALID_INDEX_PROVIDED = "No valid index found.";

    //@@author A0162011A
    private static final String HELP_DETAILS = "Deletes the specified task from the todo list.";
    private static final String HELP_FORMAT = "delete INDEX(ES)";
    private static final String[] HELP_COMMENTS = { "Related commands: `add`, `update`",
                                                    "Supports deletion of multiple indexes in a single command.",
                                                    "The `undo` command can undo this action." };
    private static final String[] HELP_EXAMPLES = { "`delete 1`\nDeletes the task at index 1.",
                                                    "`delete -2`\nDeletes the tasks up to index 2.",
                                                    "`delete 3-`\nDeletes the tasks starting from index 3.",
                                                    "`delete 1, 4`\nDeletes the tasks at index 1 and 4." };

    //@@author A0127545A
    private static final Logger logger = LogsCenter.getLogger(DeleteTaskController.class);

    public void execute(Map<String, String> tokens) throws InvalidCommandException  {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        CommandResult commandResult;

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, todoList.getTasks().size());
        try {
            validateIndexIsPresent(indexes);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidCommandException(RESULT_MESSAGE_ERROR_NO_VALID_INDEX_PROVIDED);
        }
        List<Task> tasks = uiStore.getShownTasks(indexes);
        commandResult = delete(todoList, tasks);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        uiStore.setCommandResult(commandResult);
    }

    public Map<String, String> tokenize(String command) {
        return TaskTokenizer.tokenize(COMMAND_TEMPLATE, command, true, false);
    }

    private void validateIndexIsPresent(List<Integer> indexes) throws IllegalArgumentException {
        if (indexes == null || indexes.isEmpty()) {
            throw new IllegalArgumentException(RESULT_MESSAGE_ERROR_NO_VALID_INDEX_PROVIDED);
        }
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
        if (task.canUpdateToNextRecurringTask()) {
            task.updateToNextRecurringTask();
        } else {
            todoList.remove(task);
        }
        String taskType = task.isEvent() ? "Event" : "Task";
        return new CommandResult(String.format(RESULT_MESSAGE_DELETE_TASK, taskType, task.getDescription()));
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_DELETE_TASK };
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_FORMAT, HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
