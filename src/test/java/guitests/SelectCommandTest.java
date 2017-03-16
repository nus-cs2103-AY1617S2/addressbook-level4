package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SelectCommandTest extends ToDoListGuiTest {


    @Test
    public void selectTask_nonEmptyList() {
        assertNoTaskSelected();

        assertSelectionSuccess("t1"); // first to-do task in the list
        assertSelectionSuccess("e2"); // 2nd event task in the list
        assertSelectionSuccess("d3"); // last deadline task in the list
        
        assertSelectionInvalid("t100"); // invalid index
        assertSelectionInvalid("t4"); // invalid index

        /* Testing other invalid indexes such as -1 should be done when testing the SelectCommand */
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid("t1"); //invalid index
    }

    private void assertSelectionInvalid(String prefixIndex) {
        commandBox.runCommand("select " + prefixIndex);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(String prefixIndex) {
        commandBox.runCommand("select " + prefixIndex);
        String displayPrefixIndex = prefixIndex.substring(0,1).toUpperCase()
                                        + prefixIndex.substring(1);
        assertResultMessage("Selected Task: " + displayPrefixIndex);
        //assertTaskSelected(prefixIndex);
    }

/*    private void assertTaskSelected(String prefixIndex) {
        assertEquals(taskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = taskListPanel.getSelectedTasks().get(0);
        int index = Integer.parseInt(prefixIndex.substring(1));
        assertEquals(taskListPanel.getTask(index - 1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }*/

    private void assertNoTaskSelected() {
        assertEquals(taskListPanel.getSelectedTasks().size(), 0);
    }

}
