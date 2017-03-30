package guitests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.logic.commands.ExitCommand;

public class ExitCommandTest extends TaskManagerGuiTest {

    @Test
    public void exit_ValidCommand() {
        ExitCommand ec = new ExitCommand();
        assertFalse(ec.isMutating());
    }
}
