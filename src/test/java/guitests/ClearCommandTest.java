//package guitests;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//public class ClearCommandTest extends AddressBookGuiTest {
//
//    @Test
//    public void clear() {
//
//        //verify a non-empty list can be cleared
//        assertTrue(userInboxPanel.isListMatching(td.getTypicalTasks()));
//        assertClearCommandSuccess();
//
//        //verify other commands can work after a clear command
//        commandBox.runCommand(td.assignment.getAddCommand());
//        assertTrue(userInboxPanel.isListMatching(td.assignment));
//        commandBox.runCommand("delete t 1");
//        assertListSize(0);
//
//        //verify clear command works when the list is empty
//        assertClearCommandSuccess();
//    }
//
//    private void assertClearCommandSuccess() {
//        commandBox.runCommand("clear");
//        assertListSize(0);
//        assertResultMessage("Active list has been cleared!");
//    }
//}
