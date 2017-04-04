package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


import seedu.taskit.commons.core.Messages;
import seedu.taskit.logic.commands.MarkCommand;
import seedu.taskit.model.task.ReadOnlyTask;

//@@author A0097141H
public class MarkCommandTest extends AddressBookGuiTest {
	
	@Test
	public void mark() {
		//mark undone task as done
		ReadOnlyTask undoneTask = taskListPanel.getTask(0);
		commandBox.runCommand("mark 1 done");
		assertResultMessage("Marked Task as done");
        assertTrue(undoneTask.isDone()); //zero-based task list
        
        commandBox.runCommand("list done");
        //mark done task as done
        commandBox.runCommand("mark 1 done");
        assertResultMessage(String.format(MarkCommand.MESSAGE_DUPLICATE_MARKING,"done"));
        
        //mark done task as undone
        ReadOnlyTask doneTask = taskListPanel.getTask(0);
        commandBox.runCommand("mark 1 undone");
		assertResultMessage("Marked Task as undone");
		assertTrue(!doneTask.isDone());
		
		commandBox.runCommand("list undone");
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
