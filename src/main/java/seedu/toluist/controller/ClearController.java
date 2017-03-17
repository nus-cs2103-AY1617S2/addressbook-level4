package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.dispatcher.CommandResult;
import seedu.toluist.model.Task;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.Ui;
import seedu.toluist.ui.UiStore;

/**
 * ListController is responsible for rendering the initial UI
 */
public class ClearController extends Controller {
    private static final String RESULT_MESSAGE = "All tasks cleared.";
    private static final String COMMAND_WORD = "clear";
    private static final String COMMAND_REGEX = "^clear *";

    private final Logger logger = LogsCenter.getLogger(getClass());

    public ClearController(Ui renderer) {
        super(renderer);
    }

    public CommandResult execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.load();
        todoList.setTasks(new ArrayList<Task>());
        todoList.save();

        UiStore.getInstance().setTask(todoList.getTasks());
        renderer.render();

        return new CommandResult(RESULT_MESSAGE);
    }

    public HashMap<String, String> tokenize(String command) {
        return null; // not used
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_REGEX);
    }

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }
}
