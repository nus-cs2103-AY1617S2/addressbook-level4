package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.commons.core.Config;
import seedu.toluist.model.AliasTable;
import seedu.toluist.model.Task;

/**
 * Gui tests for alias command
 */
public class AliasCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        Config.getInstance().getAliasTable().clearAliases();
    }

    @Test
    public void aliasSingleWord() {
        String aliasCommand = "alias d add";
        commandBox.runCommand(aliasCommand);

        String taskDescription = "read lecture slides from Uncle Soo";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void aliasPhraseWithMultipleWords() {
        String aliasCommand = "alias d add a task";
        commandBox.runCommand(aliasCommand);

        String addCommand = "d ";
        Task task = new Task("a task");
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));
    }

    @Test
    public void updateAlias() {
        String aliasCommandForDelete = "alias d delete";
        commandBox.runCommand(aliasCommandForDelete);

        String aliasCommandForAdd = "alias d add";
        commandBox.runCommand(aliasCommandForAdd);

        String taskDescription = "watch webcast at home";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertTrue(isTaskShown(task));
    }
}
