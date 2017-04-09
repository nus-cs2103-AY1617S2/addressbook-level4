package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.task.commons.core.Messages;
import seedu.task.logic.commands.UndoCommand;

public class UndoCommandTest extends TaskManagerGuiTest {
	@Test
	public void undo() {
		try {
			setup();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		commandBox.runCommand("clear");
		for (int i = 0; i < 11; i++) {
			assertTrue(i == taskListPanel.getNumberOfPeople());
			commandBox.runCommand("add " + i);
		}
		for (int i = 11; i > 1; i--) {
			assertTrue(i == taskListPanel.getNumberOfPeople());
			commandBox.runCommand("undo");
		}
		commandBox.runCommand("undo");
	     assertResultMessage(UndoCommand.MESSAGE_FAIL);
	}
}
