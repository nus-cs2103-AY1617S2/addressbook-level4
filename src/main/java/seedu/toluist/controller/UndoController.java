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
import seedu.toluist.ui.commons.CommandResult;

/**
 * Responsible for storage-related task
 */
public class UndoController extends Controller {
    private static final String COMMAND_TEMPLATE = "(?iu)^\\s*undo(\\s+(?<number>\\d+))?\\s*";
    private static final String COMMAND_WORD = "undo";
    private static final String PARAMETER_UNDO_TIMES = "number";
    private static final String MESSAGE_RESULT_TEMPLATE = "Your last %s to the data %s undone.";
    private static final int SINGLE_UNDO = 1;

    //@@author A0162011A
    private static final String HELP_DETAILS = "Undoes previous commands by the user.";
    private static final String HELP_FORMAT = "undo [NUMBER]";
    private static final String[] HELP_COMMENTS = { "Related commands: `undo`",
                                                    "Undo commands which `morph` data in the program.",
                                                    "If a number is entered, will undo that amount of commands.",
                                                    "Commands which can be undone: `add`, "
                                                        + "`update`, `delete`, `clear`, `tag`, `untag`, `mark`" };
    private static final String[] HELP_EXAMPLES = { "`undo`\nUndo the latest morphing command.",
                                                    "`undo 5`\nUndo the latest 5 morphing commands." };

    //@@author A0131125Y
    private static final Logger logger = LogsCenter.getLogger(UndoController.class);

    public void execute(Map<String, String> tokens) throws InvalidCommandException {
        logger.info(getClass() + "will handle command");

        String undoTimesToken = tokens.get(PARAMETER_UNDO_TIMES);
        int undoTimes = undoTimesToken != null ? Integer.parseInt(undoTimesToken) : SINGLE_UNDO;

        undo(undoTimes);
    }

    private void undo(int undoTimes) {
        Pair<TodoList, Integer> undoResult = TodoList.getInstance().getStorage().undo(undoTimes);
        TodoList todoList = TodoList.getInstance();
        todoList.setTasks(undoResult.getKey().getTasks());
        int actualUndoTimes =  undoResult.getValue();

        uiStore.setTasks(todoList.getTasks());

        uiStore.setCommandResult(new CommandResult(String.format(MESSAGE_RESULT_TEMPLATE,
                StringUtil.nounWithCount (StringUtil.WORD_CHANGE, actualUndoTimes),
                actualUndoTimes == SINGLE_UNDO ? StringUtil.WORD_WAS : StringUtil.WORD_WERE)));
    }

    public Map<String, String> tokenize(String command) {
        Pattern pattern = Pattern.compile(COMMAND_TEMPLATE);
        Matcher matcher = pattern.matcher(command.trim());
        matcher.find();
        HashMap<String, String> tokens = new HashMap<>();
        tokens.put(PARAMETER_UNDO_TIMES, matcher.group(PARAMETER_UNDO_TIMES));
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
