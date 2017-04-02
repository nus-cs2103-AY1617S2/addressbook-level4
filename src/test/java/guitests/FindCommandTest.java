//@@author A0131125Y
package guitests;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.model.Task;
import seedu.toluist.testutil.TypicalTestTodoLists;

/**
 * Gui tests for find command
 */
public class FindCommandTest extends ToLuistGuiTest {

    @Test
    public void findByName() {
        Task[] sampleTasks = (new TypicalTestTodoLists()).getTypicalTasks();

        // Find without argument
        String commandNoArguments = "find";
        testFindCommand(commandNoArguments, sampleTasks, new Task[0]);

        // Find with only spaces
        String commandOnlySpaces = "find  ";
        testFindCommand(commandOnlySpaces, sampleTasks, new Task[0]);

        // Find by complete word
        String commandCompleteWord = "find clean  ";
        testFindCommand(commandCompleteWord, new Task[] { sampleTasks[0] }, new Task[] { sampleTasks[1] });

        // Find by partial word
        String commandPartialWord = "find  clea";
        testFindCommand(commandPartialWord, new Task[] { sampleTasks[0] }, new Task[] { sampleTasks[1] });

        // Find with explicit parameter
        String commandExplicitByName = "find  clean /name";
        testFindCommand(commandExplicitByName, new Task[] { sampleTasks[0] }, new Task[] { sampleTasks[1] });

        // Check that find is case-insensitive
        String commandCaseInsensitive = "find CLEAn";
        testFindCommand(commandCaseInsensitive, new Task[] { sampleTasks[0] }, new Task[] { sampleTasks[1] });

        // Find with multiple words
        String commandMultipleWords = "find clean assign";
        testFindCommand(commandMultipleWords, sampleTasks, new Task[0]);
    }

    @Test
    public void findByTag() {
        Task[] sampleTasks = (new TypicalTestTodoLists()).getTypicalTasks();

        // Find with only spaces
        String commandOnlySpaces = "find  /tag";
        testFindCommand(commandOnlySpaces, sampleTasks, new Task[0]);

        // Find by complete word
        String commandCompleteWord = "find lewis  /tag";
        testFindCommand(commandCompleteWord, new Task[] { sampleTasks[0] }, new Task[] { sampleTasks[1] });

        // Find by partial word
        String commandPartialWord = "find  lew /tag";
        testFindCommand(commandPartialWord, new Task[] { sampleTasks[0] }, new Task[] { sampleTasks[1] });


        // Check that find is case-insensitive
        String commandCaseInsensitive = "find WORK /tag";
        testFindCommand(commandCaseInsensitive, sampleTasks, new Task[0]);

        // Find with multiple words
        String commandMultipleWords = "find lewis  louis /tag";
        testFindCommand(commandMultipleWords, sampleTasks, new Task[0]);
    }

    /**
     * Helper method to run find command and check the result
     * @param command find command
     * @param tasksToBeShown all these tasks must be shown
     * @param tasksNotToBeShown all these tasks must be hidden
     */
    private void testFindCommand(String command, Task[] tasksToBeShown, Task[] tasksNotToBeShown) {
        commandBox.runCommand(command);

        assertTrue(areTasksShown(tasksToBeShown));

        for (Task task : tasksNotToBeShown) {
            assertFalse(isTaskShown(task));
        }
    }
}
