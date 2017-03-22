package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.testutil.TestTask;

//@@author A0140042A
/**
 * Test cases for deletion of a label in tasks
 */
public class DeleteLabelCommandTest extends TaskManagerGuiTest {

    @Test
    public void editLabel_LabelDoesNotExist_ReturnTrue() {
        TestTask[] currentList = td.getTypicalTasks();
        //No change should occur
        runAndAssertTrue(DeleteCommand.COMMAND_WORD + " nonexistentlabel", currentList);
        runAndAssertTrue("delete nonexistentlabel", currentList);
    }

    @Test
    public void editLabel_invalidCommands() {
        TestTask[] currentList = td.getTypicalTasks();
        //No change should occur
        runAndAssertTrue(DeleteCommand.COMMAND_WORD, currentList);
        runAndAssertTrue(DeleteCommand.COMMAND_WORD + " !@#asdajn", currentList);
    }

    @Test
    public void editLabel_EditLabelValid_ReturnTrue() throws IllegalValueException {
        //Deletes all labels with the label name 'friends' from all tasks
        Label labelToDelete = new Label("friends");

        TestTask[] currentList = td.getTypicalTasks();
        for (TestTask task : currentList) {
            UniqueLabelList labels = task.getLabels();
            if (labels.contains(labelToDelete)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToDelete);
                task.setLabels(new UniqueLabelList(labelSet));
            }
        }

        runAndAssertTrue("delete friends", currentList);
    }

    /**
     * Runs a command on the GUI text field and asserts whether the displayed list is equal to the provided list
     */
    private void runAndAssertTrue(String command, TestTask[] currentList) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
