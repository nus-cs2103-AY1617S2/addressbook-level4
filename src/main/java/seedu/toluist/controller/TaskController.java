package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * UpdateTaskController is responsible for updating a task
 */
public class TaskController extends Controller {

    private static final String COMMAND_TEMPLATE = "^(?<command>(update))\\s*"
                + "(\\s+(?<index>\\d+))?"
                + "(\\s+(?<description>.+))?\\s*";

    private static final String TASK_VIEW_INDEX = "index";
    private static final String TASK_DESCRIPTION = "description";

    protected Logger logger = LogsCenter.getLogger(getClass());

    public TaskController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + "will handle command");

        TodoList todoList = TodoList.load();
        CommandResult commandResult = new CommandResult("");

        HashMap<String, String> tokens = tokenize(command);

        String description = tokens.get(TASK_DESCRIPTION);
        String indexToken = tokens.get(TASK_VIEW_INDEX);
        int index = indexToken != null ? Integer.parseInt(indexToken) - 1 : -1;
        Task task = indexToken != null
                ? UiStore.getInstance().getTasks().get(index)
                : null;


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
        tokens.put(TASK_DESCRIPTION, matcher.group(TASK_DESCRIPTION));
        tokens.put(TASK_VIEW_INDEX, matcher.group(TASK_VIEW_INDEX));
        return tokens;
    }

    @Override
    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }
}
