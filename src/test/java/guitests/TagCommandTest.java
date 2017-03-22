//@@author A0131125Y
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui Tests for tag command
 */
public class TagCommandTest extends ToLuistGuiTest {

    @Test
    public void addTag_singleTag() {
        String tagName = "aTag";
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        task.addTag(new Tag(tagName));
        String command = "tag 1 " + tagName;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }
}
