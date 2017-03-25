package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.UndoCommand;
import project.taskcrusher.testutil.TestCard;
import project.taskcrusher.testutil.TypicalTestTasks;

public class UndoCommandTest extends AddressBookGuiTest {
    @Test
    public void undo() throws IllegalValueException {
        TestCard[] currentList = td.getTypicalTasks();
        int targetIndex = 1;
        
        // undo without previous command
        
        // undo add command
        assertUndoSuccess(TypicalTestTasks.Undochecker.getAddCommand(), currentList);
        
        // undo delete command
        assertUndoSuccess("delete " + targetIndex, currentList);
        
        
        
        // undo done command
        //assertUndoSuccess("done " + targetIndex, currentList);
    }
    
    private void assertUndoSuccess(String command, TestCard... originalList) {
        commandBox.runCommand(command);
        commandBox.runCommand("undo");
/*        assertTrue(personListPanel.isListMatching(originalList));*/
    }   
}