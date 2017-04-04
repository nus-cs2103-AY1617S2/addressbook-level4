//@@author A0131125Y
package guitests;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.commons.core.Config;
import seedu.toluist.controller.AliasController;
import seedu.toluist.controller.ViewAliasController;
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
    public void alias_singleWord() {
        String aliasCommand = "alias d add";
        commandBox.runCommand(aliasCommand);

        String taskDescription = "read lecture slides from Uncle Soo";
        String addCommand = "d " + taskDescription;
        Task task = new Task(taskDescription);
        runCommandThenCheckForTasks(addCommand, new Task[] { task }, new Task[0]);
    }

    @Test
    public void alias_phraseWithMultipleWords() {
        String aliasCommand = "alias d add a task";
        commandBox.runCommand(aliasCommand);

        String addCommand = "d ";
        Task task = new Task("a task");
        runCommandThenCheckForTasks(addCommand, new Task[] { task }, new Task[0]);
    }

    @Test
    public void alias_ReservedWord() {
        String aliasCommand = "alias alias alias";
        runCommandThenCheckForResultMessage(aliasCommand,
                String.format(AliasController.RESULT_MESSAGE_RESERVED_WORD, "alias"));

        String viewAliasCommand = "viewalias";
        runCommandThenCheckForResultMessage(viewAliasCommand, ViewAliasController.RESULT_MESSAGE_NO_ALIAS);
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
        runCommandThenCheckForTasks(addCommand, new Task[] { task }, new Task[0]);
    }
}
