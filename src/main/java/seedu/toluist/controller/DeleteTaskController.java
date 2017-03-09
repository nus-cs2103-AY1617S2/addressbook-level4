package seedu.toluist.controller;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

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

        HashMap<String, String> tokens = tokenize(command);

        String indexToken = tokens.get(TASK_VIEW_INDEX);
        int index = indexToken != null ? Integer.parseInt(indexToken) - 1 : -1;
        Task task = indexToken != null
                ? UiStore.getInstance().getTasks().get(index)
                : null;

        commandResult = delete(todoList, task);

        if (todoList.save()) {
            uiStore.setTask(todoList.getTasks());
            renderer.render();
        }

        return commandResult;
    }

    @Override
    public HashMap<String, String> tokenize(String commandArgs) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(commandArgs.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        return tokens;
    }

    private CommandResult delete(TodoList todoList, Task task) {
        todoList.remove(task);
        return new CommandResult(RESULT_MESSAGE_DELETE_TASK);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_DELETE_TASK };
    }
}
