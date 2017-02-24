package seedu.address.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.dispatcher.CommandResult;
import seedu.address.model.TodoList;
import seedu.address.model.task.Task;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class TaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^(?<command>(add|delete|update)) "
                + "task( (?<index>\\d+))?"
                + "( (?<description>.+))?";

    private static final String TASK_COMMAND = "command";
    private static final String TASK_VIEW_INDEX = "index";
    private static final String TASK_DESCRIPTION = "description";

    private static final String ADD_TASK_COMMAND = "add";
    private static final String UPDATE_TASK_COMMAND = "update";
    private static final String DELETE_TASK_COMMAND = "delete";

    private static final String ADD_TASK_RESULT = "New task added";
    private static final String UPDATE_TASK_RESULT = "Task updated";
    private static final String DELETE_TASK_RESULT = "Task deleted";

    private final Logger logger = LogsCenter.getLogger(getClass());
    private final TodoList todoList = TodoList.getInstance();

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + "will handle command");

        final HashMap<String, String> tokens = tokenize(command);

        final String taskCommand = tokens.get(TASK_COMMAND);
        final String description = tokens.get(TASK_DESCRIPTION);
        final String indexToken = tokens.get(TASK_VIEW_INDEX);
        final int index = indexToken != null ? Integer.parseInt(indexToken) - 1 : -1;
        final Task task = indexToken != null ? todoList.getLastViewedTasks().get(index) : null;

        CommandResult commandResult = new CommandResult("");
        switch (taskCommand) {
        case ADD_TASK_COMMAND:
            commandResult = add(description);
            break;
        case UPDATE_TASK_COMMAND:
            commandResult = update(task, description);
            break;
        case DELETE_TASK_COMMAND:
            commandResult = delete(task);
            break;
        default:
            break;
        }

        renderer.render(todoList);

        return commandResult;
    }

    @Override
    public HashMap<String, String> tokenize(String commandArgs) {
        final Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        final Matcher matcher = pattern.matcher(commandArgs.trim());
        matcher.find();
        final HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_COMMAND, matcher.group(TASK_COMMAND));
        tokens.put(TASK_DESCRIPTION, matcher.group(TASK_DESCRIPTION));
        tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    private CommandResult update(Task task, String description) {
        todoList.updateTask(task, description);
        return new CommandResult(UPDATE_TASK_RESULT);
    }

    private CommandResult add(String description) {
        todoList.addTask(new Task(description));
        return new CommandResult(ADD_TASK_RESULT);
    }

    private CommandResult delete(Task task) {
        todoList.removeTask(task);
        return new CommandResult(DELETE_TASK_RESULT);
    }
}
