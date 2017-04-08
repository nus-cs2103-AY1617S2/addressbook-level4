package onlythree.imanager.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.ZonedDateTime;
import java.util.Optional;

import org.junit.Test;

import onlythree.imanager.model.tag.UniqueTagList;

public class TaskTest {

    @Test
    // TODO in progress
    public void anyhowTest() throws Exception {
        //new TestTask();
        Task taskNormalOne = new Task(new Name("Test"), Optional.empty(), Optional.empty(), new UniqueTagList());
        Task taskNormalTwo = new Task(new Name("Test"), Optional.empty(), Optional.empty(), new UniqueTagList());
        //assertFalse(taskOne.equals(taskTwo));

        ZonedDateTime dateTime = ZonedDateTime.now().plusDays(1);
        ZonedDateTime dateTimeTwo = dateTime.plusDays(1);

        Optional<Deadline> deadlineOne = Optional.of(new Deadline(dateTime));
        Optional<Deadline> deadlineTwo = Optional.of(new Deadline(dateTimeTwo));

        //Optional<Deadline> deadlineSame = Optional.of(new Deadline(dateTime));
        Task taskDeadlineOne = new Task(new Name("Test"), deadlineOne, Optional.empty(), new UniqueTagList());
        Task taskDeadlineTwo = new Task(new Name("Test"), deadlineTwo, Optional.empty(), new UniqueTagList());

        Task taskSpecialA = new Task(new Name("lol"), deadlineOne, Optional.empty(), new UniqueTagList());
        Task taskSpecialB = new Task(new Name("lol"), Optional.empty(), Optional.empty(), new UniqueTagList());

        assertFalse(taskDeadlineOne.equals(taskDeadlineTwo)); // currently problematic because too restrictive

        assertTrue(taskNormalOne.equals(taskNormalTwo));

        assertFalse(taskSpecialA.equals(taskSpecialB)); // currently expected behaviour
    }
}
