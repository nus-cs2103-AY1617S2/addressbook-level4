//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * UnknownCommandController is responsible for rendering the initial UI
 */
public class ClearController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(ClearController.class);
    private static final String RESULT_MESSAGE = "All tasks cleared.";
    private static final String COMMAND_WORD = "clear";
    private static final String COMMAND_REGEX = "(?iu)^\\s*clear\\s*";

    public void execute(String command) {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        todoList.setTasks(new ArrayList<>());
        todoList.save();

        UiStore.getInstance().setTasks(todoList.getTasks());

        uiStore.setCommandResult(new CommandResult(RESULT_MESSAGE));
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
