package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Tag;
import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui Tests for untag command
 */
public class UntagCommandTest extends ToLuistGuiTest {

    @Test
    public void removeTag_SingleTag() {
        // add one task
        Tag lewisTag = new Tag("lewis");
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        task.removeTag(lewisTag);
        String command = "untag 1 lewis";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }
}
