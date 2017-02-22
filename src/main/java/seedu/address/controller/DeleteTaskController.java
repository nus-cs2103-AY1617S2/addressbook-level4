package seedu.address.controller;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

import java.util.HashMap;

/**
 * Created by louis on 21/2/17.
 */
public class DeleteTaskController extends Controller {

    private static String COMMAND_WORD = "delete";
    private static String RESULT_MESSAGE = "Task removed";
    private static String TASK_VIEW_INDEX = "viewIndex";

    public CommandResult execute(String commandArgs) {
        final TodoList todoList = TodoList.getInstance();
        final HashMap<String, String> tokens = tokenize(commandArgs);
        final int viewIndex = Integer.parseInt(tokens.get(TASK_VIEW_INDEX)) - 1;
        final Task toBeRemoved = todoList.getLastViewedTasks().get(viewIndex);
        todoList.removeTask(toBeRemoved);
        renderer.render(todoList);
        return new CommandResult(RESULT_MESSAGE);
    }

    @Override
    public HashMap<String, String> tokenize(String commandArgs) {
        final HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_VIEW_INDEX, commandArgs);
        return tokens;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
