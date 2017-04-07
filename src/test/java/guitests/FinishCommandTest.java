package guitests;

import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

public class FinishCommandTest extends WhatsLeftGuiTest {
    public void test() {
        TestEvent[] currentEventList = te.getTypicalEvents();
        currentEventList = TestUtil.filterExpectedTestEventList(currentEventList);
        TestTask[] currentTaskList = tt.getTypicalTasks();
        currentTaskList = TestUtil.filterExpectedTestTaskList(currentTaskList);
        TestEvent eventToRedo = te.consultation;
    }
}
