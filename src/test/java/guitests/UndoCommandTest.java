package guitests;

import org.junit.Test;

import seedu.taskit.logic.commands.Command;
import seedu.taskit.logic.commands.UndoCommand.*;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.testutil.TaskBuilder;
import seedu.taskit.testutil.TestTask;
import static seedu.taskit.logic.commands.UndoCommand.MESSAGE_NO_PREVIOUS_STATE;
import static seedu.taskit.logic.commands.UndoCommand.MESSAGE_UNDO_SUCCESS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

//@@author A0141011J
public class UndoCommandTest extends AddressBookGuiTest{

    @Test
    public void undo_noPreviousCommand_error() {
        //relaunch the app
        commandBox.runCommand("undo");
        assertResultMessage(MESSAGE_NO_PREVIOUS_STATE);
    }

    @Test
    public void undo_lastOneModification_success() {
        TestTask[] currentList = td.getTypicalTasks();

        //undo add command
        TestTask taskToAdd = td.assignment;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertListSize(currentList.length + 1);
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));

        //undo delete comand
        commandBox.runCommand("delete 1");
        assertListSize(currentList.length - 1);
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));

        //undo clear command
        commandBox.runCommand("clear");
        assertListSize(0);
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));

        //undo mark command
        commandBox.runCommand("mark 1 done");
        assertListSize(currentList.length - 1);
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));

        //undo find command
        commandBox.runCommand("find HW");
        assertListSize(2);
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));

        //undo list command
        commandBox.runCommand("list all");
        assertListSize(currentList.length);
        commandBox.runCommand("undo");
        assertListSize(currentList.length);
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    public void undo_multipleModifications_success() {
        TestTask[] currentList = td.getTypicalTasks();

        /* Integration tests:
         * Sequentially execute the following command:
         * - add
         * - delete
         * - mark
         * - list
         * - clear
         *
         *
         **/

        //add a task
        TestTask taskToAdd = td.assignment;
        commandBox.runCommand(taskToAdd.getAddCommand());
        assertListSize(currentList.length + 1);

        //delete a task
        commandBox.runCommand("delete 1");
        assertListSize(currentList.length);

        //mark a task as done
        commandBox.runCommand("mark 1 done");
        assertListSize(currentList.length - 1);

        //list done task
        commandBox.runCommand("list done");
        assertListSize(1);

        //clear the task manager
        commandBox.runCommand("clear");
        assertListSize(0);

        //undo
        commandBox.runCommand("undo");
        assertListSize(currentList.length);

        //undo
        commandBox.runCommand("undo");
        //assertListSize();

        //undo

        //undo


    }

}
