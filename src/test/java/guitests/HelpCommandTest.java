//@@author A0139961U
package guitests;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.ClearCommand;
import seedu.tache.logic.commands.CompleteCommand;
import seedu.tache.logic.commands.DeleteCommand;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.ExitCommand;
import seedu.tache.logic.commands.FindCommand;
import seedu.tache.logic.commands.HelpCommand;
import seedu.tache.logic.commands.ListCommand;
import seedu.tache.logic.commands.LoadCommand;
import seedu.tache.logic.commands.SaveCommand;
import seedu.tache.logic.commands.SelectCommand;
import seedu.tache.logic.commands.UndoCommand;

public class HelpCommandTest extends TaskManagerGuiTest {

    @Test
    public void helpAdd() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD);
        assertResultMessage(AddCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpClear() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ClearCommand.COMMAND_WORD);
        assertResultMessage(ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpComplete() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + CompleteCommand.COMMAND_WORD);
        assertResultMessage(CompleteCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpDelete() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD);
        assertResultMessage(DeleteCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpEdit() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + EditCommand.COMMAND_WORD);
        assertResultMessage(EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpExit() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ExitCommand.COMMAND_WORD);
        assertResultMessage(ExitCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpFind() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD);
        assertResultMessage(FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpList() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ListCommand.COMMAND_WORD);
        assertResultMessage(ListCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpLoad() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + LoadCommand.COMMAND_WORD);
        assertResultMessage(LoadCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpSave() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + SaveCommand.COMMAND_WORD);
        assertResultMessage(SaveCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpSelect() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + SelectCommand.COMMAND_WORD);
        assertResultMessage(SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpUndo() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpInvalidCommandFailure() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " INVALID COMMAND");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
}
