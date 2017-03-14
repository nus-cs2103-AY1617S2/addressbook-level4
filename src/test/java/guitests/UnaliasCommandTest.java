package guitests;

import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.commons.core.Config;
import seedu.toluist.controller.UnaliasController;
import seedu.toluist.model.Task;

/**
 * Gui tests for unalias command
 */
public class UnaliasCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        Config.getInstance().getAliasTable().clearAliases();
    }

    @Test
    public void unalias_nonExistingAlias() {
        String unaliasCommand = "unalias d";
        commandBox.runCommand(unaliasCommand);
        assertResultMessage(String.format(UnaliasController.RESULT_MESSAGE_NOT_ALIAS, "d"));
    }

    @Test
    public void unalias_existingAlias() {
        Config.getInstance().getAliasTable().setAlias("d", "add");

        String unaliasCommand = "unalias d";
        commandBox.runCommand(unaliasCommand);

        String taskDescription = "read lecture slides from Prof Henry";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        commandBox.runCommand(addCommand);
        assertFalse(isTaskShown(task));
    }
}
