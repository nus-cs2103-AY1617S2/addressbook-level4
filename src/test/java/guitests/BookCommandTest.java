package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BookCommand;

public class BookCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_IsMutating() throws IllegalDateTimeValueException, IllegalValueException {
        BookCommand bc = new BookCommand("book command","nothing");
        assertTrue(bc.isMutating());
    }

}
