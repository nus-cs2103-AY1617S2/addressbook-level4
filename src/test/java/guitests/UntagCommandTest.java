//@@author A0131125Y
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
    public void removeTag_singleTag() {
        Tag lewisTag = new Tag("lewis");
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        task.removeTag(lewisTag);
        String command = "untag 1 lewis";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void addTag_MultipleTags() {
        Tag lewisTag = new Tag("lewis");
        Tag workTag = new Tag("work");
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        task.removeTag(lewisTag);
        task.removeTag(workTag);
        String command = "untag 1 lewis work";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void revoveTag_nonExistingTags() {
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        String command = "untag 1 aTag";
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }
}
