//@@author A0139961U
package guitests;

import static seedu.tache.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.tache.commons.core.Messages;
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
import seedu.tache.logic.commands.NextCommand;
import seedu.tache.logic.commands.PrevCommand;
import seedu.tache.logic.commands.SaveCommand;
import seedu.tache.logic.commands.SelectCommand;
import seedu.tache.logic.commands.UndoCommand;
import seedu.tache.logic.commands.ViewCommand;

public class HelpCommandTest extends TaskManagerGuiTest {

    @Test
    public void help_addCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + AddCommand.COMMAND_WORD);
        assertResultMessage(AddCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_clearCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ClearCommand.COMMAND_WORD);
        assertResultMessage(ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_completeCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + CompleteCommand.COMMAND_WORD);
        assertResultMessage(CompleteCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_deleteCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + DeleteCommand.COMMAND_WORD);
        assertResultMessage(DeleteCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_editCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + EditCommand.COMMAND_WORD);
        assertResultMessage(EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_exitCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ExitCommand.COMMAND_WORD);
        assertResultMessage(ExitCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_findCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + FindCommand.COMMAND_WORD);
        assertResultMessage(FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_listCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ListCommand.COMMAND_WORD);
        assertResultMessage(ListCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_loadCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + LoadCommand.COMMAND_WORD);
        assertResultMessage(LoadCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_saveCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + SaveCommand.COMMAND_WORD);
        assertResultMessage(SaveCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_selectCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + SelectCommand.COMMAND_WORD);
        assertResultMessage(SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_undoCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + UndoCommand.COMMAND_WORD);
        assertResultMessage(UndoCommand.MESSAGE_USAGE);
    }

    //@@author A0142255M
    @Test
    public void help_prevCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + PrevCommand.COMMAND_WORD);
        assertResultMessage(PrevCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_nextCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + NextCommand.COMMAND_WORD);
        assertResultMessage(NextCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_viewCommand_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " " + ViewCommand.COMMAND_WORD);
        assertResultMessage(ViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_openUserGuide_success() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD);
        assertResultMessage(HelpCommand.SHOWING_HELP_MESSAGE);
    }

    @Test
    public void help_shortCommand_success() {
        commandBox.runCommand(HelpCommand.SHORT_COMMAND_WORD + " load");
        assertResultMessage(LoadCommand.MESSAGE_USAGE);
    }

    @Test
    public void help_invalidHelpCommand_failure() {
        commandBox.runCommand("helpppppp");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    //@@author A0139961U
    @Test
    public void help_invalidCommand_failure() {
        commandBox.runCommand(HelpCommand.COMMAND_WORD + " INVALID COMMAND");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
    }
}
