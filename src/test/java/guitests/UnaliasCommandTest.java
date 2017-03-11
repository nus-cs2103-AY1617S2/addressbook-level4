package guitests;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.model.CommandAliasConfig;
import seedu.toluist.model.Task;

/**
 * Gui tests for unalias command
 */
public class UnaliasCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        CommandAliasConfig.getInstance().clearAliases();
    }

    @Test
    public void aliasAddCommandThenUnaliasIt() {
        String aliasCommand = "alias d add";
        commandBox.runCommand(aliasCommand);

        String unaliasCommand = "unalias d";
        commandBox.runCommand(unaliasCommand);

        String taskDescription = "read lecture slides from Prof Henry";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertFalse(isTaskShown(task));
    }
}
