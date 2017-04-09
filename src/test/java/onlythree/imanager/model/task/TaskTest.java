package onlythree.imanager.model.task;

import static org.junit.Assert.assertNotNull;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import onlythree.imanager.commons.exceptions.IllegalValueException;
import onlythree.imanager.model.tag.UniqueTagList;
import onlythree.imanager.model.task.exceptions.InvalidDurationException;
import onlythree.imanager.model.task.exceptions.PastDateTimeException;

//@@author A0140023E
public class TaskTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void constructor_withRequiredFields_success() throws IllegalValueException {
        assertNotNull(new Task(new Name("Random"), Optional.empty(), Optional.empty(), new UniqueTagList()));

        assertNotNull(
                new Task(new Name("&#421"), Optional.empty(), Optional.empty(), new UniqueTagList("tag1")));

        assertNotNull(new Task(new Name("1234"), Optional.empty(), Optional.empty(),
                new UniqueTagList("tag1", "tag2")));
    }

    @Test
    public void constructor_withDeadline_success() throws PastDateTimeException, IllegalValueException {
        assertNotNull(new Task(new Name("Test"), Optional.of(new Deadline(ZonedDateTime.now().plusDays(6))),
                        Optional.empty(), new UniqueTagList()));

        assertNotNull(new Task(new Name("Test"), Optional.of(new Deadline(ZonedDateTime.now().plusSeconds(25))),
                        Optional.empty(), new UniqueTagList()));
    }

    @Test
    public void constructor_withStartEndDateTime_success()
            throws PastDateTimeException, InvalidDurationException, IllegalValueException {

        assertNotNull(new Task(new Name("why what lol"), Optional.empty(), Optional.of(
                new StartEndDateTime(ZonedDateTime.now().plusMinutes(5), ZonedDateTime.now().plusHours(2))),
                new UniqueTagList()));

        assertNotNull(new Task(new Name("idkkk"), Optional.empty(), Optional.of(
                new StartEndDateTime(ZonedDateTime.now().plusDays(7), ZonedDateTime.now().plusYears(2))),
                new UniqueTagList()));
    }

    @Test
    public void constructor_withDeadlineAndStartEndDateTime_expectsException()
            throws PastDateTimeException, InvalidDurationException, IllegalValueException {

        exception.expect(IllegalValueException.class);
        exception.expectMessage(Task.MESSAGE_TASK_CONSTRAINTS);

        final Deadline deadline = new Deadline(ZonedDateTime.now().plusMinutes(7));
        final StartEndDateTime startEndDateTime = new StartEndDateTime(ZonedDateTime.now().plusDays(7),
                ZonedDateTime.now().plusYears(2));
        assertNotNull(new Task(new Name("Rumble roll!"), Optional.of(deadline), Optional.of(startEndDateTime),
                new UniqueTagList()));
    }
}
