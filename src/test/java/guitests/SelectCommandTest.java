package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import typetask.model.task.ReadOnlyTask;

public class SelectCommandTest extends AddressBookGuiTest {


    @Test
    public void selectTaskNonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first person in the list
        int personCount = td.getTypicalTasks().length;
        assertSelectionSuccess(personCount); // last person in the list
        int middleIndex = personCount / 2;
        assertSelectionSuccess(middleIndex); // a person in the middle of the list

        assertSelectionInvalid(personCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTaskEmptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(int index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: " + index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(int index) {
        assertEquals(personListPanel.getSelectedPersons().size(), 1);
        ReadOnlyTask selectedPerson = personListPanel.getSelectedPersons().get(0);
        assertEquals(personListPanel.getPerson(index - 1), selectedPerson);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(personListPanel.getSelectedPersons().size(), 0);
    }

}
