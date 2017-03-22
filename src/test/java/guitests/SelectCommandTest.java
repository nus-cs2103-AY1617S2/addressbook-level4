package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SelectCommandTest extends TaskListGuiTest {


    @Test
    public void selectPerson_nonEmptyList() {

        assertNoPersonSelected();
        assertNoTasksFoundWithTerm("elephant"); // no tasks of 'elephant' found

        assertTasksFoundWithTerm("Alice"); // task found of 'Alice' found

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectPerson_emptyList() {
        assertTasksFoundWithTerm("Alice"); // task found of 'Alice' found
        commandBox.runCommand("clear");
        assertListSize(0);
        assertNoTasksFoundWithTerm("Alice"); // no tasks of 'Alice' found
    }

    private void assertNoTasksFoundWithTerm(String term) {
        commandBox.runCommand("find " + term);
        assertResultMessage("0 tasks listed!");
    }

    private void assertTasksFoundWithTerm(String term) {
        commandBox.runCommand("find " + term);
        assertResultMessage("1 tasks listed!");
    }

    private void assertNoPersonSelected() {
        assertEquals(personListPanel.getSelectedPersons().size(), 0);
    }

}
