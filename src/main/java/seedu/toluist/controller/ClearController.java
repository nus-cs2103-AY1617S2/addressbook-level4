//@@author A0162011A
package seedu.toluist.controller;

import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Logger;

import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * UnknownCommandController is responsible for rendering the initial UI
 */
public class ClearController extends Controller {
    private static final Logger logger = LogsCenter.getLogger(ClearController.class);
    private static final String MESSAGE_RESULT = "All tasks cleared.";
    private static final String COMMAND_WORD = "clear";
    private static final String COMMAND_REGEX = "(?iu)^\\s*clear\\s*";

    private static final String HELP_DETAILS = "Clears all entries from the todo list.";
    private static final String HELP_FORMAT = "clear";
    private static final String[] HELP_COMMENTS = { "All entries from every list is affected, "
                                                        + "not just on the current task window.",
                                                    "The `undo` command can undo this action." };

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass().getName() + " will handle command");

        TodoList todoList = TodoList.getInstance();
        UiStore uiStore = UiStore.getInstance();
        todoList.setTasks(new ArrayList<>());
        todoList.save();

        uiStore.setTasks(todoList.getTasks());
        uiStore.setCommandResult(new CommandResult(MESSAGE_RESULT));
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_REGEX);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, null };
    }
}
