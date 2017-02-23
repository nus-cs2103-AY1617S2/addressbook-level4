package seedu.address.controller;

import java.util.HashMap;

import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

/**
 * AddTaskController is responsible for creating a new task
 */
public class AddTaskController extends Controller {

    private static final String COMMAND_WORD = "add";
    private static final String RESULT_MESSAGE = "New task added";
    private static final String TASK_DESCRIPTION = "description";

    public CommandResult execute(String command) {
        final TodoList todoList = TodoList.getInstance();
        final HashMap<String, String> tokens = tokenize(command);
        Task newTask;
        newTask = new Task(tokens.get(TASK_DESCRIPTION));
        todoList.addTask(newTask);
        renderer.render(todoList);
        return new CommandResult(RESULT_MESSAGE);
    }

    @Override
    public HashMap<String, String> tokenize(String command) {
        final HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_DESCRIPTION, command);
        return tokens;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
