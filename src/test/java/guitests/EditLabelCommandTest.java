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
 * Test cases for editing of a label
 */
public class EditLabelCommandTest extends AddressBookGuiTest {

    @Test
    public void editLabel_LabelDoesNotExist_ReturnTrue() {
        TestPerson[] currentList = td.getTypicalPersons();
        commandBox.runCommand("editlabel nonexistentlabel newlabel");

        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_invalidCommands() {
        TestPerson[] currentList = td.getTypicalPersons();
        commandBox.runCommand("editlabel notEnoughArguments");
        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));

        commandBox.runCommand("editlabel");
        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));

        commandBox.runCommand("editlabel !@#asdajn newLabel");
        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));

        commandBox.runCommand("editlabel friends !@#!@sdfs");
        //No change should occur
        assertTrue(personListPanel.isListMatching(currentList));
    }

    @Test
    public void editLabel_EditLabelValid_ReturnTrue() throws IllegalValueException {
        Label labelToChange = new Label("friends");
        Label newLabel = new Label("allies");

        TestPerson[] currentList = td.getTypicalPersons();
        for (TestPerson person : currentList) {
            UniqueLabelList labels = person.getLabels();
            if (labels.contains(labelToChange)) {
                Set<Label> labelSet = labels.toSet();
                labelSet.remove(labelToChange);
                labelSet.add(newLabel);
                person.setLabels(new UniqueLabelList(labelSet));
            }
        }


        commandBox.runCommand("editlabel friends allies");

        assertTrue(personListPanel.isListMatching(currentList));
    }
}
