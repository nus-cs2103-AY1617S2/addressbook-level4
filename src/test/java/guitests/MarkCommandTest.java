package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.MarkCommand;


//@@author A0097141H
public class MarkCommandTest extends AddressBookGuiTest {
	@Test
	public void mark() {
		//mark undone task as done
		commandBox.runCommand("mark 1 done");
		assertResultMessage("Marked Task as done");
        assertTrue(taskListPanel.getTask(0).isDone()); //zero-based task list
        
        //mark done task as done
        commandBox.runCommand("mark 1 done");
        assertResultMessage(String.format(MarkCommand.MESSAGE_DUPLICATE_MARKING,"done"));
        
        //mark done task as undone
        commandBox.runCommand("mark 1 undone");
		assertResultMessage("Marked Task as undone");
		assertTrue(!taskListPanel.getTask(0).isDone());
		
		//mark undone task as undone
		commandBox.runCommand("mark 1 undone");
        assertResultMessage(String.format(MarkCommand.MESSAGE_DUPLICATE_MARKING,"undone"));
        
        //mark invalid task as done
        commandBox.runCommand("mark 233 done");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //mark invalid task as undone
        commandBox.runCommand("mark 466 undone");
        assertResultMessage(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        
        //mark command invalid format
        commandBox.runCommand("mark ");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,MarkCommand.MESSAGE_NOT_MARKED));
	}

}
