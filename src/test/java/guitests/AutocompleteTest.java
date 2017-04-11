package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.opus.logic.commands.AddCommand;
import seedu.opus.logic.commands.ClearCommand;
import seedu.opus.logic.commands.DeleteCommand;
import seedu.opus.logic.commands.EditCommand;
import seedu.opus.logic.commands.ExitCommand;
import seedu.opus.logic.commands.FindCommand;
import seedu.opus.logic.commands.HelpCommand;
import seedu.opus.logic.commands.ListCommand;
import seedu.opus.logic.commands.MarkCommand;
import seedu.opus.logic.commands.RedoCommand;
import seedu.opus.logic.commands.SaveCommand;
import seedu.opus.logic.commands.ScheduleCommand;
import seedu.opus.logic.commands.SortCommand;
import seedu.opus.logic.commands.SyncCommand;
import seedu.opus.logic.commands.UndoCommand;

//@@author A0124368A
public class AutocompleteTest extends TaskManagerGuiTest {

    private static final String[] COMMANDS_ONE_TAB = {
        AddCommand.COMMAND_WORD,
        DeleteCommand.COMMAND_WORD,
        EditCommand.COMMAND_WORD,
        MarkCommand.COMMAND_WORD,
        SaveCommand.COMMAND_WORD,
        ListCommand.COMMAND_WORD,
        HelpCommand.COMMAND_WORD,
        FindCommand.COMMAND_WORD,
        UndoCommand.COMMAND_WORD,
        RedoCommand.COMMAND_WORD,
        ClearCommand.COMMAND_WORD
    };

    private static final String[] COMMANDS_TWO_TAB = {
        ScheduleCommand.COMMAND_WORD,
        ExitCommand.COMMAND_WORD
    };

    private static final String[] COMMANDS_THREE_TAB = {
        SyncCommand.COMMAND_WORD
    };

    private static final String[] COMMANDS_FOUR_TAB = {
        SortCommand.COMMAND_WORD
    };

    @Test
    public void testAutocompleteEmptyCommand() {
        // Empty command should not autocomplete
        assertCommandAutocompleted("", "", 1);
    }

    @Test
    public void testInvalidCommand() {
        // Invalid commands should not autocomplete
        assertCommandAutocompleted("invalid", "invalid", 1);
    }

    @Test
    public void testAutocompleteCommands() {
        for (String command : COMMANDS_ONE_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 1);
        }

        for (String command : COMMANDS_TWO_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 2);
        }

        for (String command : COMMANDS_THREE_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 3);
        }

        for (String command : COMMANDS_FOUR_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 4);
        }
    }

    private void assertCommandAutocompleted(String input, String expected, int times) {
        commandBox.autocompleteInput(input, times);
        assertEquals(commandBox.getCommandInput(), expected);
    }

}
//@@author
