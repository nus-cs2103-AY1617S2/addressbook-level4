package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditLabelCommand;
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
<<<<<<< HEAD
        commandBox.runCommand(EditLabelCommand.COMMAND_WORD + " nonexistentlabel newlabel");
=======
        commandBox.runCommand("editlabel nonexistentlabel newlabel");
>>>>>>> V0.3-james

        //No change should occur
        assertTrue(taskListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_invalidCommands() {
        TestTask[] currentList = td.getTypicalTasks();
        //No change should occur for any of these commands
<<<<<<< HEAD
        runAndAssertTrue(EditLabelCommand.COMMAND_WORD + " notEnoughArguments", currentList);
        runAndAssertTrue(EditLabelCommand.COMMAND_WORD + "", currentList);
        runAndAssertTrue(EditLabelCommand.COMMAND_WORD + " !@#asdajn newLabel", currentList);
        runAndAssertTrue(EditLabelCommand.COMMAND_WORD + " friends !@#!@sdfs", currentList);
=======
        runAndAssertTrue("editlabel notEnoughArguments", currentList);
        runAndAssertTrue("editlabel", currentList);
        runAndAssertTrue("editlabel !@#asdajn newLabel", currentList);
        runAndAssertTrue("editlabel friends !@#!@sdfs", currentList);
>>>>>>> V0.3-james
    }

    @Test
    public void editLabel_EditLabelValid_ReturnTrue() throws IllegalValueException {
        //Changes all task with the label friends to allies
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

<<<<<<< HEAD
        runAndAssertTrue(EditLabelCommand.COMMAND_WORD + " friends allies", currentList);
=======
        runAndAssertTrue("editlabel friends allies", currentList);
>>>>>>> V0.3-james
    }

    /**
     * Runs a command on the GUI text field and asserts whether the displayed list is equal to the provided list
     */
    private void runAndAssertTrue(String command, TestTask[] currentList) {
        commandBox.runCommand(command);
        assertTrue(taskListPanel.isListMatching(currentList));
    }
}
