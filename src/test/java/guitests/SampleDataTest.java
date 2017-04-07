//package guitests;
//
//import static org.junit.Assert.assertTrue;
//
//import org.junit.Test;
//
//import project.taskcrusher.model.UserInbox;
//import project.taskcrusher.model.task.Task;
//import project.taskcrusher.model.util.SampleDataUtil;
//import project.taskcrusher.testutil.TestUtil;
//
//public class SampleDataTest extends AddressBookGuiTest {
//    @Override
//    protected UserInbox getInitialData() {
//        // return null to force test app to load data from file only
//        return null;
//    }
//
//    @Override
//    protected String getDataFileLocation() {
//        // return a non-existent file location to force test app to load sample data
//        return TestUtil.getFilePathInSandboxFolder("SomeFileThatDoesNotExist1234567890.xml");
//    }
//
//    @Test
//    public void addressBook_dataFileDoesNotExist_loadSampleData() throws Exception {
//        Task[] expectedList = SampleDataUtil.getSampleTasks();
//        assertTrue(userInboxPanel.isListMatching(expectedList));
//    }
//}
