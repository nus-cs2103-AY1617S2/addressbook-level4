package guitests;

public class SelectCommandTest extends TaskManagerGuiTest {

    /* This file only serves as a reference since SELECT command is deprecated

    @Test
    public void selectTask_nonEmptyList() {

        assertSelectionInvalid(10); // invalid index
        assertNoTaskSelected();

        assertSelectionSuccess(1); // first task in the list
        int taskCount = td.getTypicalTasks().length;
        assertSelectionSuccess(taskCount); // last task in the list
        int middleIndex = taskCount / 2;
        assertSelectionSuccess(middleIndex); // a task in the middle of the list

        assertSelectionInvalid(taskCount + 1); // invalid index
        assertTaskSelected(middleIndex); // assert previous selection remains

        // Testing other invalid indexes such as -1 should be done when testing the SelectCommand 
    }

    @Test
    public void selectTask_emptyList() {
        commandBox.runCommand("clear");
        assertListSize(0);
        assertSelectionInvalid(1); //invalid index
    }

    private void assertSelectionInvalid(String index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("The task index provided is invalid");
    }

    private void assertSelectionSuccess(String index) {
        commandBox.runCommand("select " + index);
        assertResultMessage("Selected Task: " + index);
        assertTaskSelected(index);
    }

    private void assertTaskSelected(String index) {
        assertEquals(futureTaskListPanel.getSelectedTasks().size(), 1);
        ReadOnlyTask selectedTask = futureTaskListPanel.getSelectedTasks().get(0);
        assertEquals(futureTaskListPanel.getTask(index - 1), selectedTask);
        //TODO: confirm the correct page is loaded in the Browser Panel
    }

    private void assertNoTaskSelected() {
        assertEquals(futureTaskListPanel.getSelectedTasks().size(), 0);
    }
    
    */

}
