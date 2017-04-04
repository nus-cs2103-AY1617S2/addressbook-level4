//package guitests;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
////@@author A0148038A
///*
// * GUI test for ClearCommand
// */
//public class ClearCommandTest extends WhatsLeftGuiTest {
//
//    @Test
//    public void clearEvent() {
//        //verify a non-empty event list can be cleared
//        assertTrue(eventListPanel.isListMatching(te.getTypicalEvents()));
//        assertClearEventCommandSuccess();
//
//        //verify other commands can work after a clear command
//        commandBox.runCommand(te.consultation.getAddCommand());
//        assertTrue(eventListPanel.isListMatching(te.consultation));
//        commandBox.runCommand("delete ev 1");
//        assertEventListSize(0);
//
//        //verify clear command works when the list is empty
//        assertClearEventCommandSuccess();
//    }
//
//    @Test
//    public void clearTask() {
//        //verify a non-empty task list can be cleared
//        assertTrue(taskListPanel.isListMatching(tt.getTypicalTasks()));
//        assertClearTaskCommandSuccess();
//
//        //verify other commands can work after a clear command
//        commandBox.runCommand(tt.homework.getAddCommand());
//        assertTrue(taskListPanel.isListMatching(tt.homework));
//        commandBox.runCommand("delete ts 1");
//        assertTaskListSize(0);
//
//        //verify clear command works when the list is empty
//        assertClearTaskCommandSuccess();
//    }
//
//    @Test
//    public void clearAll() {
//        //verify a non-empty WhatsLeft can be cleared
//        assertTrue(eventListPanel.isListMatching(te.getTypicalEvents()));
//        assertClearEventCommandSuccess();
//        assertTrue(taskListPanel.isListMatching(tt.getTypicalTasks()));
//        assertClearTaskCommandSuccess();
//
//        //verify other commands can work after a clear command
//        commandBox.runCommand(te.consultation.getAddCommand());
//        assertTrue(eventListPanel.isListMatching(te.consultation));
//        commandBox.runCommand("delete ev 1");
//        assertEventListSize(0);
//        commandBox.runCommand(tt.homework.getAddCommand());
//        assertTrue(taskListPanel.isListMatching(tt.homework));
//        commandBox.runCommand("delete ts 1");
//        assertTaskListSize(0);
//
//        //verify clear command works when the list is empty
//        assertClearAllCommandSuccess();
//    }
//
//    private void assertClearEventCommandSuccess() {
//        commandBox.runCommand("clear ev");
//        assertEventListSize(0);
//        assertResultMessage("Event list in WhatsLeft has been cleared!");
//    }
//
//    private void assertClearTaskCommandSuccess() {
//        commandBox.runCommand("clear ts");
//        assertTaskListSize(0);
//        assertResultMessage("Task list in WhatsLeft has been cleared!");
//    }
//
//    private void assertClearAllCommandSuccess() {
//        commandBox.runCommand("clear");
//        assertEventListSize(0);
//        assertTaskListSize(0);
//        assertResultMessage("WhatsLeft has been cleared!");
//    }
//}
