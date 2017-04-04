//@@author A0131125Y
package guitests;

import org.junit.Before;
import org.junit.Test;

import seedu.toluist.commons.core.Config;
import seedu.toluist.controller.ViewAliasController;

/**
 * Gui tests for viewalias command
 */
public class ViewAliasCommandTest extends ToLuistGuiTest {
    @Before
    public void setUp() {
        Config.getInstance().getAliasTable().clearAliases();
    }

    @Test
    public void viewAlias_noAliases() {
        checkResultOfViewAlias(ViewAliasController.RESULT_MESSAGE_NO_ALIAS);
    }

    @Test
    public void viewAlias_oneAlias() {
        String aliasCommand = "alias d add";
        commandBox.runCommand(aliasCommand);

        String expected = "d:add";
        checkResultOfViewAlias(expected);
    }

    @Test
    public void viewAlias_multipleAliases() {
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
        runCommandThenCheckForResultMessage("viewalias", expected);
    }
}
