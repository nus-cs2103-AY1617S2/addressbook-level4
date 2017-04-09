package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.jobs.commons.core.Messages;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;
import seedu.jobs.testutil.TestTask;

public class FindCommandTest extends TaskBookGuiTest {

    //@@author A0164440M
    //TODO add success cases such as find by name, desc, tag
    @Test
    public void find_nonEmptyList() throws IllegalArgumentException, IllegalTimeException {
        // no results
        assertFindResult("find Final exam");

        // single result found by name
        assertFindResult("find 7", td.CS3107);
        // multiple results found by names
        assertFindResult("find CS31", td.CS3101, td.CS3102,
                td.CS3103, td.CS3104, td.CS3105, td.CS3106, td.CS3107);

        // single result found by tag
        assertFindResult("find lecture", td.CS3102);
        // mutiple results found by tag
        assertFindResult("find 206", td.CS3101, td.CS3102);

        // single result found by descriptions
        assertFindResult("find chapter", td.CS3101);
        // multiple results found by descriptions
        assertFindResult("find deadline", td.CS3104, td.CS3105);

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find deadline", td.CS3105);

        // multiple results found by name, tag, descriptions
        assertFindResult("find 06", td.CS3101, td.CS3102, td.CS3103, td.CS3106);
    }
  //@@author

    @Test
    public void find_emptyList() throws IllegalArgumentException, IllegalTimeException {
        commandBox.runCommand("clear");
        assertFindResult("find ST2131"); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findFIN3131");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);
    }

    private void assertFindResult(String command, TestTask... expectedHits)
            throws IllegalArgumentException, IllegalTimeException {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!");
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }
}
