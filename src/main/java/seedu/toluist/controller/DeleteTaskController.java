package seedu.toluist.controller;
import java.util.HashMap;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;

/**
 * DeleteTaskController is responsible for deleting a task
 */
public class DeleteTaskController extends TaskController {

    private static final String COMMAND_TEMPLATE = "^delete"
            + "(\\s+(?<index>\\d+))?\\s*";

    private static final String COMMAND_DELETE_TASK = "delete";

    private static final String RESULT_MESSAGE_DELETE_TASK = "Task deleted";

    public DeleteTaskController(Ui renderer) {
        super(renderer, COMMAND_TEMPLATE);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command, true, false);

        String indexToken = tokens.get(TASK_VIEW_INDEX);
        Task task = getTask(indexToken);

        commandResult = delete(todoList, task);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    private CommandResult delete(TodoList todoList, Task task) {
        todoList.remove(task);
        return new CommandResult(RESULT_MESSAGE_DELETE_TASK);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_DELETE_TASK };
    }
}
