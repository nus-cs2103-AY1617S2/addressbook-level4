//@@author A0131125Y
package seedu.toluist.controller;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.util.Pair;
import seedu.toluist.commons.core.LogsCenter;
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
    private static final String RESULT_MESSAGE_TEMPLATE = "Your last %s to the data %s undone.";

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

    public void execute(String command) {
        logger.info(getClass() + "will handle command");

        HashMap<String, String> tokens = tokenize(command);
        String undoTimesToken = tokens.get(PARAMETER_UNDO_TIMES);
        int undoTimes = undoTimesToken != null ? Integer.parseInt(undoTimesToken) : 1;

        Pair<TodoList, Integer> undoResult = TodoList.getInstance().getStorage().undo(undoTimes);
        TodoList todoList = TodoList.getInstance();
        todoList.setTasks(undoResult.getKey().getTasks());
        int actualUndoTimes =  undoResult.getValue();

        uiStore.setTasks(todoList.getTasks());

        uiStore.setCommandResult(new CommandResult(String.format(RESULT_MESSAGE_TEMPLATE,
                StringUtil.nounWithCount ("change", actualUndoTimes),
                actualUndoTimes == 1 ? "was" : "were")));
    }

    public HashMap<String, String> tokenize(String command) {
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

    public static String[] getCommandWords() {
        return new String[] { COMMAND_WORD };
    }

    //@@author A0162011A
    public static String[] getBasicHelp() {
        return new String[] { String.join("/", getCommandWords()), HELP_DETAILS, HELP_FORMAT };
    }

    public static String[][] getDetailedHelp() {
        return new String[][] { getBasicHelp(), HELP_COMMENTS, HELP_EXAMPLES };
    }
}
