//@@author A0127545A
package seedu.toluist.controller;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.toluist.commons.core.LogsCenter;
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

    private static final Logger logger = LogsCenter.getLogger(DeleteTaskController.class);

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        CommandResult commandResult;

        HashMap<String, String> tokens = tokenize(command);

        String indexToken = tokens.get(TaskTokenizer.TASK_VIEW_INDEX);
        List<Integer> indexes = IndexParser.splitStringToIndexes(indexToken, todoList.getTasks().size());
        if (indexes == null || indexes.isEmpty()) {
            uiStore.setCommandResult(new CommandResult(RESULT_MESSAGE_ERROR_NO_VALID_INDEX_PROVIDED));
            return;
        }
        List<Task> tasks = uiStore.getShownTasks(indexes);
        commandResult = delete(todoList, tasks);

        if (todoList.save()) {
            uiStore.setTasks(todoList.getTasks());
        }

        uiStore.setCommandResult(commandResult);
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

    public static String[] getCommandWords() {
        return new String[] { COMMAND_DELETE_TASK };
    }
}
