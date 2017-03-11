package guitests;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.controller.ViewAliasController;
import seedu.toluist.model.CommandAliasConfig;

/**
 * Gui tests for viewalias command
 */
public class ViewAliasCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        CommandAliasConfig.getInstance().clearAliases();
    }

    @Test
    public void viewAlias_noAliases() {
        checkResultOfViewAlias(ViewAliasController.NO_ALIAS_MESSAGE);
    }

    @Test
    public void viewAlias_OneAlias() {
        String aliasCommand = "alias d add";
        commandBox.runCommand(aliasCommand);

        String expected = "d:add";
        checkResultOfViewAlias(expected);
    }

    @Test
    public void viewAlias_MultipleAliases() {
        String aliasCommandForAdd = "alias d add";
        commandBox.runCommand(aliasCommandForAdd);

        String aliasCommandForDelete = "alias del delete";
        commandBox.runCommand(aliasCommandForDelete);

        String aliasCommandForGibberish = "alias g gibberish";
        commandBox.runCommand(aliasCommandForGibberish);


        String expected = "d:add\ndel:delete\ng:gibberish";
        checkResultOfViewAlias(expected);
    }

    /**
     * Check output of viewalias command
     * @param expected expected result message
     */
    private void checkResultOfViewAlias(String expected) {
        String command = "viewalias";
        commandBox.runCommand(command);
        assertResultMessage(expected);
    }
}
