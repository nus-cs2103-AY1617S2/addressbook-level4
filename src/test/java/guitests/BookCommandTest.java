package guitests;

import org.junit.Test;

import guitests.guihandles.TaskCardHandle;
import seedu.address.commons.exceptions.IllegalDateTimeValueException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.testutil.TaskBuilder;
import seedu.address.testutil.TestTask;

public class BookCommandTest extends TaskManagerGuiTest {

    @Test
    public void find_IsMutating() throws IllegalDateTimeValueException, IllegalValueException, CommandException {
        TestTask booking = new TaskBuilder().withTitle("Complete booking")
                .withLabels("friends").withBookings("10-10-2017 2pm to 5pm,"
                        + " 11-10-2017 2pm to 5pm, 12-10-2017 2pm to 5pm")
                .withStatus(false).build();
        commandBox.runCommand(booking.getAddCommand());
        TaskCardHandle addedCard = taskListPanel.navigateToTask(booking.getTitle().title);
        assertMatching(booking, addedCard);
    }

}
