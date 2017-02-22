package seedu.address.controller;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;
import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

import java.util.HashMap;

/**
 * Created by louis on 21/2/17.
 */
public class AddTaskController extends Controller {

    private static String COMMAND_WORD = "add";
    private static String RESULT_MESSAGE = "New task added";
    private static String TASK_DESCRIPTION = "description";

    public CommandResult execute(String command) {
        final TodoList todoList = TodoList.getInstance();
        final HashMap<String, String> tokens = tokenize(command);
        Task newTask;
        try {
            newTask = new Task(tokens.get(TASK_DESCRIPTION));
            todoList.addTask(newTask);
            renderer.render(todoList);
        } catch (IllegalValueException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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
