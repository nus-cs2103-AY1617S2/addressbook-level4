//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
import seedu.toluist.commons.exceptions.InvalidCommandException;
import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.TodoList;
import seedu.toluist.ui.UiStore;
import seedu.toluist.ui.commons.CommandResult;

/**
 * Responsible for redo-related task
 */
public class RedoController extends Controller {
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*redo(\\s+(?<number>\\d+))?\\s*";
    private static final String COMMAND_WORD = "redo";
    private static final String PARAMETER_REDO_TIMES = "number";
    private static final String MESSAGE_RESULT_TEMPLATE = "Your last %s %s re-applied.";
    private static final int SINGLE_REDO = 1;

    //@@author A0162011A
    private static final String HELP_DETAILS = "Redo previously undone commands by the user.";
    private static final String HELP_FORMAT = "redo [NUMBER]";
    private static final String[] HELP_COMMENTS = { "Related commands: `undo`",
                                                    "Redo commands which were undone by the `undo` command.",
                                                    "If a number is entered, will redo that amount of `undo` commands.",
                                                    "Commands which can be undone: `add`, `update`, `delete`, "
                                                        + "`clear`, `tag`, `untag`, `mark`",
                                                    "Warning: After undo, once a new command "
                                                        + "which can be undone is entered, redo cannot be used." };
    private static final String[] HELP_EXAMPLES = { "`redo`\nRedo the latest undo command.",
                                                    "`redo 5`\nRedo the latest 5 undo commands." };

    //@@author A0131125Y
    private static final Logger logger = LogsCenter.getLogger(RedoController.class);

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass() + "will handle command");
        String redoTimesToken = tokens.get(PARAMETER_REDO_TIMES);
        int redoTimes = redoTimesToken != null ? Integer.parseInt(redoTimesToken) : SINGLE_REDO;
        redo(redoTimes);
    }

    private void redo(int redoTimes) {
        Pair<TodoList, Integer> redoResult = TodoList.getInstance().getStorage().redo(redoTimes);
        TodoList todoList = TodoList.getInstance();
        UiStore uiStore = UiStore.getInstance();
        todoList.setTasks(redoResult.getKey().getTasks());
        int actualRedoTimes = redoResult.getValue();

        uiStore.setTasks(todoList.getTasks());
        uiStore.setCommandResult(new CommandResult(String.format(MESSAGE_RESULT_TEMPLATE,
                StringUtil.nounWithCount(StringUtil.WORD_CHANGE, actualRedoTimes),
                actualRedoTimes == SINGLE_REDO ? StringUtil.WORD_WAS : StringUtil.WORD_WERE)));
    }

    public Map<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_REDO_TIMES, matcher.group(PARAMETER_REDO_TIMES));
        return tokens;
    }

    public boolean matchesCommand(String command) {
        return command.matches(COMMAND_TEMPLATE);
    }

    public String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    //@@author A0162011A
    public String[] getBasicHelp() {
        return new String[] { String.join(StringUtil.FORWARD_SLASH, getCommandWords()), HELP_FORMAT,
            HELP_DETAILS };
    }

    public String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
