//@@author A0162011A
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.commons.core.Messages;
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

    @Test
    public void removeTag_InvalidIndex() {
        String[] validCommandWithInvalidIndex = { "untag 0 aTag", "untag 1000 aTag"};
        for (String command : validCommandWithInvalidIndex) {
            commandBox.runCommand(command);
            assertResultMessage(Messages.MESSAGE_INVALID_TASK_INDEX);
        }
    }

    @Test
    public void removeTag_InvalidFormat() {
        String[] invalidCommands = { "untag", "untag aTag", "untag aTag bTag", "untag aTag 1",  "untag 1"};
        for (String command : invalidCommands) {
            commandBox.runCommand(command);
            assertResultMessage(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, "untag"));
        }
    }
}
