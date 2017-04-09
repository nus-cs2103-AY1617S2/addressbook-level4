package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.whatsleft.logic.commands.AddCommand;
import seedu.whatsleft.logic.commands.CalendarRefreshCommand;
import seedu.whatsleft.logic.commands.CalendarViewCommand;
import seedu.whatsleft.logic.commands.ClearCommand;
import seedu.whatsleft.logic.commands.DeleteCommand;
import seedu.whatsleft.logic.commands.EditCommand;
import seedu.whatsleft.logic.commands.ExitCommand;
import seedu.whatsleft.logic.commands.FindCommand;
import seedu.whatsleft.logic.commands.FinishCommand;
import seedu.whatsleft.logic.commands.ListCommand;
import seedu.whatsleft.logic.commands.ReadCommand;
import seedu.whatsleft.logic.commands.RecurCommand;
import seedu.whatsleft.logic.commands.RedoCommand;
import seedu.whatsleft.logic.commands.SaveCommand;
import seedu.whatsleft.logic.commands.SelectCommand;
import seedu.whatsleft.logic.commands.ShowCommand;
import seedu.whatsleft.logic.commands.UndoCommand;

//@@author A0148038A
/*
* GUI test for HelpCommand
*/
public class HelpCommandTest extends WhatsLeftGuiTest {

    @Test
    public void helpWithoutCommandWord() {
        commandBox.runCommand("help");
        assertTrue(mainMenu.openHelpWindowUsingAccelerator().isWindowOpen());
        mainMenu.openHelpWindowUsingAccelerator().closeWindow();
    }

    @Test
    public void helpAdd() {
        commandBox.runCommand("help add");
        assertResultMessage(AddCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpEdit() {
        commandBox.runCommand("help edit");
        assertResultMessage(EditCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpSelect() {
        commandBox.runCommand("help select");
        assertResultMessage(SelectCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpDelete() {
        commandBox.runCommand("help delete");
        assertResultMessage(DeleteCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpClear() {
        commandBox.runCommand("help clear");
        assertResultMessage(ClearCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpShow() {
        commandBox.runCommand("help show");
        assertResultMessage(ShowCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpFind() {
        commandBox.runCommand("help find");
        assertResultMessage(FindCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpFinish() {
        commandBox.runCommand("help finish");
        assertResultMessage(FinishCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpRedo() {
        commandBox.runCommand("help redo");
        assertResultMessage(RedoCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpSave() {
        commandBox.runCommand("help save");
        assertResultMessage(SaveCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpRead() {
        commandBox.runCommand("help read");
        assertResultMessage(ReadCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpNext() {
        commandBox.runCommand("help next");
        assertResultMessage(CalendarViewCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpList() {
        commandBox.runCommand("help list");
        assertResultMessage(ListCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpExit() {
        commandBox.runCommand("help exit");
        assertResultMessage(ExitCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpUndo() {
        commandBox.runCommand("help undo");
        assertResultMessage(UndoCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpRefresh() {
        commandBox.runCommand("help refresh");
        assertResultMessage(CalendarRefreshCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpRecur() {
        commandBox.runCommand("help recur");
        assertResultMessage(RecurCommand.MESSAGE_USAGE);
    }

}
