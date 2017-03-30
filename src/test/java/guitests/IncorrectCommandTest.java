package guitests;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.logic.commands.IncorrectCommand;

public class IncorrectCommandTest {

    @Test
    public void list_IsMutating() throws IllegalDateTimeValueException {
        IncorrectCommand ic = new IncorrectCommand("incorrectCommand");
        assertFalse(ic.isMutating());
    }
}
