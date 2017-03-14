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
 * Test cases for editing of a label
 */
public class EditLabelCommandTest extends TaskManagerGuiTest {

    @Test
    public void editLabel_LabelDoesNotExist_ReturnTrue() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("EDITLABEL nonexistentlabel newlabel");

        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_invalidCommands() {
        TestTask[] currentList = td.getTypicalTasks();
        commandBox.runCommand("EDITLABEL notEnoughArguments");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("EDITLABEL");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("EDITLABEL !@#asdajn newLabel");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));

        commandBox.runCommand("EDITLABEL friends !@#!@sdfs");
        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_EditLabelValid_ReturnTrue() throws IllegalValueException {
        Label labelToChange = new Label("friends");
        Label newLabel = new Label("allies");

        TestTask[] currentList = td.getTypicalTasks();
        for (TestTask task : currentList) {
            UniqueLabelList labels = task.getLabels();
            if (labels.contains(labelToChange)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToChange);
                labelSet.add(newLabel);
                task.setLabels(new UniqueLabelList(labelSet));
            }
        }


        commandBox.runCommand("EDITLABEL friends allies");

        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
