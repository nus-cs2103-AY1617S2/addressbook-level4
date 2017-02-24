package seedu.address.controller.task;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.controller.Controller;
import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class UpdateTaskController extends Controller {

    private static final String COMMAND_WORD = "update";
    private static final String RESULT_MESSAGE = "Task updated";
    private static final String TASK_VIEW_INDEX = "index";
    private static final String TASK_DESCRIPTION = "description";
    private static final Pattern ARGUMENT_FORMAT =
            Pattern.compile("(?<index>.+)"
                    + " (?<description>.+)");

    public CommandResult execute(String commandArgs) {
        final TodoList todoList = TodoList.getInstance();
        final HashMap<String, String> tokens = tokenize(commandArgs);
        final int viewIndex = Integer.parseInt(tokens.get(TASK_VIEW_INDEX)) - 1;
        final String description = tokens.get(TASK_DESCRIPTION);
        final Task toBeUpdated = todoList.getLastViewedTasks().get(viewIndex);
        todoList.updateTask(toBeUpdated, description);
        renderer.render(todoList);
        return new CommandResult(RESULT_MESSAGE);
    }

    @Override
    public HashMap<String, String> tokenize(String commandArgs) {
        final Matcher matcher = ARGUMENT_FORMAT.matcher(commandArgs.trim());
        matcher.find();
        final HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_DESCRIPTION, matcher.group(TASK_DESCRIPTION));
        tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        return tokens;
    }

    @Override
    public String getCommandWord() {
        return COMMAND_WORD;
    }
}
