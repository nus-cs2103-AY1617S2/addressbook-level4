//@@author A0131125Y
package guitests;

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
        runCommandThenCheckForTasks(commandNoArguments, sampleTasks, new Task[0]);

        // Find with only spaces
        String commandOnlySpaces = "find  ";
        runCommandThenCheckForTasks(commandOnlySpaces, sampleTasks, new Task[0]);

        // Find by complete word
        String commandCompleteWord = "find clean  ";
        runCommandThenCheckForTasks(commandCompleteWord, new Task[] { sampleTasks[0] },
                new Task[] { sampleTasks[1] });

        // Find by partial word
        String commandPartialWord = "find  clea";
        runCommandThenCheckForTasks(commandPartialWord, new Task[] { sampleTasks[0] },
                new Task[] { sampleTasks[1] });

        // Find with explicit parameter
        String commandExplicitByName = "find  clean /name";
        runCommandThenCheckForTasks(commandExplicitByName, new Task[] { sampleTasks[0] },
                new Task[] { sampleTasks[1] });

        // Check that find is case-insensitive
        String commandCaseInsensitive = "find CLEAn";
        runCommandThenCheckForTasks(commandCaseInsensitive, new Task[] { sampleTasks[0] },
                new Task[] { sampleTasks[1] });

        // Find with multiple words
        String commandMultipleWords = "find clean assign";
        runCommandThenCheckForTasks(commandMultipleWords, sampleTasks, new Task[0]);
    }

    @Test
    public void findByTag() {
        Task[] sampleTasks = (new TypicalTestTodoLists()).getTypicalTasks();

        // Find with only spaces
        String commandOnlySpaces = "find  /tag";
        runCommandThenCheckForTasks(commandOnlySpaces, sampleTasks, new Task[0]);

        // Find by complete word
        String commandCompleteWord = "find lewis  /tag";
        runCommandThenCheckForTasks(commandCompleteWord, new Task[] { sampleTasks[0] },
                new Task[] { sampleTasks[1] });

        // Find by partial word
        String commandPartialWord = "find  lew /tag";
        runCommandThenCheckForTasks(commandPartialWord, new Task[] { sampleTasks[0] },
                new Task[] { sampleTasks[1] });


        // Check that find is case-insensitive
        String commandCaseInsensitive = "find WORK /tag";
        runCommandThenCheckForTasks(commandCaseInsensitive, sampleTasks, new Task[0]);

        // Find with multiple words
        String commandMultipleWords = "find lewis  louis /tag";
        runCommandThenCheckForTasks(commandMultipleWords, sampleTasks, new Task[0]);
    }
}
