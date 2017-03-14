package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
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
        commandBox.runCommand("DELETE nonexistentlabel");

        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_invalidCommands() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("DELETE");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("DELETE !@#asdajn");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_EditLabelValid_ReturnTrue() throws IllegalValueException {
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


        commandBox.runCommand("DELETE friends");

        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
