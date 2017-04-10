package seedu.task.model.task;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.tag.UniqueTagList;

public class TaskTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void invalidDatesConstructor_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        Task task = new Task(new Name("test"), new Date("1/1/2018"), new Date("1/1/2017"), new Remark(), new Location(),
                new UniqueTagList(), false, new String());
    }

    @Test
    public void checkEventId_fail() throws IllegalValueException {
        thrown.expect(IllegalValueException.class);
        Task task = new Task(new Name("test"), new Date(), new Date(), new Remark(), new Location(),
                new UniqueTagList(), false, new String("1"));
        task.setEventId("1");
    }

    @Test
    public void equal_symmetric() throws IllegalValueException {
        Task task = new Task(new Name("Apply for internship"), new Date("04-03-1989 23:59"),
                new Date("08-03-1989 23:59"), new Remark("checkout career fair"),
                new Location("123, Jurong West Ave 6"), new UniqueTagList("personal"), false, new String("eventId"));
        Task task2 = new Task(new Name("Apply for internship"), new Date("04-03-1989 23:59"),
                new Date("08-03-1989 23:59"), new Remark("checkout career fair"),
                new Location("123, Jurong West Ave 6"), new UniqueTagList("personal"), false, new String("eventId"));
        assertTrue(task.equals(task2));
        assertTrue(task.hashCode() == task2.hashCode());

    }
}
