//@@author A0131125Y
package guitests;

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
        runCommandThenCheckForResultMessage(unaliasCommand,
                String.format(UnaliasController.RESULT_MESSAGE_NOT_ALIAS, "d"));
    }

    @Test
    public void unalias_existingAlias() {
        Config.getInstance().getAliasTable().setAlias("d", "add");

        String unaliasCommand = "unalias d";
        commandBox.runCommand(unaliasCommand);

        String taskDescription = "read lecture slides from Prof Henry";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        runCommandThenCheckForTasks(addCommand, new Task[0], new Task[] { task });
    }
}
