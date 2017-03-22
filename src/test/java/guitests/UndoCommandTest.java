package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.logic.commands.UndoCommand;
import seedu.task.testutil.TestTask;

public class UndoCommandTest extends TaskListGuiTest {
    @Test
    public void undoSingle() throws Exception {
        //undo while no history is present
        commandBox.runCommand("undo");
        assertResultMessage(UndoCommand.MESSAGE_NO_HISTORY);

        //undo add
        TestTask[] originalList = td.getTypicalPersons();
        commandBox.runCommand(td.hoon.getAddCommand());
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(originalList));

        //undo edit
        String detailsToEdit = "Bobby t/husband";
        int taskListIndex = 1;
        commandBox.runCommand("edit " + taskListIndex + " " + detailsToEdit);
        commandBox.runCommand("undo");
        assertTrue(personListPanel.isListMatching(originalList));
    }

}
