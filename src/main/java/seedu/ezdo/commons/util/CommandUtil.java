package seedu.ezdo.commons.util;

import java.util.Arrays;

import seedu.ezdo.logic.commands.AddCommand;
import seedu.ezdo.logic.commands.AliasCommand;
import seedu.ezdo.logic.commands.ClearCommand;
import seedu.ezdo.logic.commands.DoneCommand;
import seedu.ezdo.logic.commands.EditCommand;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.HelpCommand;
import seedu.ezdo.logic.commands.KillCommand;
import seedu.ezdo.logic.commands.ListCommand;
import seedu.ezdo.logic.commands.QuitCommand;
import seedu.ezdo.logic.commands.RedoCommand;
import seedu.ezdo.logic.commands.SaveCommand;
import seedu.ezdo.logic.commands.SelectCommand;
import seedu.ezdo.logic.commands.SortCommand;
import seedu.ezdo.logic.commands.UndoCommand;

/**
 * A class for command related utility functions.
 */
public class CommandUtil {

    public static final String[] EXISTING_COMMAND_WORDS = {AddCommand.COMMAND_WORD, AddCommand.SHORT_COMMAND_WORD,
                                                           ClearCommand.COMMAND_WORD, ClearCommand.SHORT_COMMAND_WORD,
                                                           DoneCommand.COMMAND_WORD, DoneCommand.SHORT_COMMAND_WORD,
                                                           EditCommand.COMMAND_WORD, EditCommand.SHORT_COMMAND_WORD,
                                                           FindCommand.COMMAND_WORD, FindCommand.SHORT_COMMAND_WORD,
                                                           HelpCommand.COMMAND_WORD, HelpCommand.SHORT_COMMAND_WORD,
                                                           KillCommand.COMMAND_WORD, KillCommand.SHORT_COMMAND_WORD,
                                                           ListCommand.COMMAND_WORD, ListCommand.SHORT_COMMAND_WORD,
                                                           QuitCommand.COMMAND_WORD, QuitCommand.SHORT_COMMAND_WORD,
                                                           RedoCommand.COMMAND_WORD, RedoCommand.SHORT_COMMAND_WORD,
                                                           SortCommand.COMMAND_WORD, SortCommand.SHORT_COMMAND_WORD,
                                                           UndoCommand.COMMAND_WORD, UndoCommand.SHORT_COMMAND_WORD,
                                                           AliasCommand.COMMAND_WORD,
                                                           SaveCommand.COMMAND_WORD,
                                                           SelectCommand.COMMAND_WORD};


    /**
     * Checks if a given word a recognised command of ezDo.
     */
    public static boolean isExistingCommand(String word) {
        return Arrays.asList(EXISTING_COMMAND_WORDS).contains(word);
    }

}
