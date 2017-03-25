//@@author A0162011A
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

    @Test
    public void addTag_MultipleTags() {
        String tagName = "aTag";
        String tagName2 = "bTag";
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        task.addTag(new Tag(tagName));
        task.addTag(new Tag(tagName2));
        String command = " taG 1 " + tagName + " " + tagName2;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void addTag_MultipleTagsWithDuplicate() {
        String tagName = "aTag";
        Task task = new TypicalTestTodoLists().getTypicalTasks()[0];
        task.addTag(new Tag(tagName));
        String command = " taG 1 " + tagName + " " + tagName;
        commandBox.runCommand(command);
        assertTrue(isTaskShown(task));
    }
}
