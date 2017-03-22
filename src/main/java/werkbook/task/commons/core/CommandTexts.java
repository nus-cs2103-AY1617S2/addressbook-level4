//@@author A0140462R

package werkbook.task.commons.core;

import java.util.ArrayList;
import java.util.Arrays;

/*
 * Container for command words used
 */

public class CommandTexts {

    public static final String ADD_COMMAND_WORD = "add";
    public static final String EDIT_COMMAND_WORD = "edit";
    public static final String SELECT_COMMAND_WORD = "select";
    public static final String DELETE_COMMAND_WORD = "delete";
    public static final String CLEAR_COMMAND_WORD = "clear";
    public static final String FIND_COMMAND_WORD = "find";
    public static final String LIST_COMMAND_WORD = "list";
    public static final String EXIT_COMMAND_WORD = "exit";
    public static final String HELP_COMMAND_WORD = "help";
    public static final String UNDO_COMMAND_WORD = "undo";
    public static final String REDO_COMMAND_WORD = "redo";
    public static final String MARK_COMMAND_WORD = "mark";
    public static final String SAVE_COMMAND_WORD = "save";
    public static final ArrayList<String> COMMAND_TEXT_LIST = new ArrayList<String>(
        Arrays.asList(ADD_COMMAND_WORD, EDIT_COMMAND_WORD, SELECT_COMMAND_WORD,
                      DELETE_COMMAND_WORD, CLEAR_COMMAND_WORD, FIND_COMMAND_WORD,
                      LIST_COMMAND_WORD, EXIT_COMMAND_WORD, HELP_COMMAND_WORD,
                      UNDO_COMMAND_WORD, REDO_COMMAND_WORD, MARK_COMMAND_WORD,
                      SAVE_COMMAND_WORD));
}
