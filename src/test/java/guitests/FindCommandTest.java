//@@author A0148037E
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.geekeep.commons.core.Messages;
import seedu.geekeep.logic.commands.FindCommand;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.testutil.TestTask;

public class FindCommandTest extends GeeKeepGuiTest {

    @Test
    public void find_nonEmptyList() {
        assertFindResult("find Travel", "Travel", "", "", ""); //find with keyword, no results
        assertFindResult("find a/01-01-18 0000", "", "01-01-18 0000", "", ""); //find with earliest time, no result
        assertFindResult("find b/01-01-16 0000", "", "", "01-01-16 0000", ""); //find with latest time, no result
        assertFindResult("find t/CS3230", "", "", "", "CS3230"); //find with tag, no results

        assertFindResult("find Hackathon", "Hackathon", "", "", "", td.hackathon); //one result;
        assertFindResult("find Trip a/01-01-17 0000", "Trip",
                         "01-01-17 0000", "", "", td.japan, td.spain); // multiple results

        //find after deleting one result
        commandBox.runCommand("delete 1");
        assertFindResult("find Trip", "Trip", "", "", "", td.spain);
    }

    @Test
    public void find_emptyList() {
        commandBox.runCommand("clear");
        assertFindResult("find Fishing", "Fishing", "", "", ""); // no results
    }

    @Test
    public void find_invalidCommand_fail() {
        commandBox.runCommand("findevent");
        assertResultMessage(Messages.MESSAGE_UNKNOWN_COMMAND);

        commandBox.runCommand("find a/01-01-18 b/01-01-17");
        assertResultMessage(FindCommand.MESSAGE_TIME_CONSTRAINTS);

        commandBox.runCommand("find ");
        assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        commandBox.runCommand("find t/");
        assertResultMessage(Tag.MESSAGE_TAG_CONSTRAINTS);

        commandBox.runCommand("find a/ b/");
        assertResultMessage(DateTime.MESSAGE_DATETIME_CONSTRAINTS);
    }

    private void assertFindResult(String command, String keyword,
            String earliestTime, String latestTime,
            String tag, TestTask... expectedHits) {
        commandBox.runCommand(command);
        assertListSize(expectedHits.length);
        assertResultMessage(expectedHits.length + " tasks listed!"
                            + getDetailedSuccessMsg(keyword, earliestTime, latestTime, tag));
        assertTrue(taskListPanel.isListMatching(expectedHits));
    }

    private String getDetailedSuccessMsg(String keyword, String earliestTime,
            String latestTime, String tag) {
        String successMsg = " GeeKeep is showing all the tasks which:\n";
        if (!keyword.isEmpty()) {
            successMsg += "Contains any of keywords in [" + keyword + "] in title;\n";
        }
        if (!earliestTime.isEmpty()) {
            successMsg += "Has starting time[event] or deadline[deadline] after "
                           + earliestTime
                           + ";\n";
        }
        if (!latestTime.isEmpty()) {
            successMsg += "Has starting time[event] or deadline[deadline] before "
                    + latestTime
                    + ";\n";
        }
        if (!tag.isEmpty()) {
            successMsg += "Has any of tags in [" + tag + "];\n";
        }
        return successMsg;
    }
}
