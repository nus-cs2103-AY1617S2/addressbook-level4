package guitests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.label.Label;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.testutil.TestPerson;

//@@author A0140042A
/**
 * Test cases for deletion of a label in tasks
 */
public class DeleteLabelCommandTest extends AddressBookGuiTest {

    @Test
    public void editLabel_LabelDoesNotExist_ReturnTrue() {
        TestPerson[] currentList = td.getTypicalPersons();
        commandBox.runCommand("deletelabel nonexistentlabel");

        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_invalidCommands() {
        TestPerson[] currentList = td.getTypicalPersons();
        commandBox.runCommand("deletelabel");
        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));

        commandBox.runCommand("editlabel !@#asdajn");
        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_EditLabelValid_ReturnTrue() throws IllegalValueException {
        Label labelToDelete = new Label("friends");

        TestPerson[] currentList = td.getTypicalPersons();
        for (TestPerson person : currentList) {
            UniqueLabelList labels = person.getLabels();
            if (labels.contains(labelToDelete)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToDelete);
                person.setLabels(new UniqueLabelList(labelSet));
            }
        }


        commandBox.runCommand("deletelabel friends");

        assertTrue(personListPanel.isListMatching(currentList));
    }
}
