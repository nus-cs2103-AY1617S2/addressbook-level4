//@@author A0131125Y
package guitests;

import org.junit.Test;

/**
 * Gui tests for exit command
 */
public class ExitCommandTest extends ToLuistGuiTest {
    @Test
    public void exit() {
        String command = "exit";
        commandBox.runCommand(command);
        // Just making sure that this passed and no exceptions happened
    }
}
