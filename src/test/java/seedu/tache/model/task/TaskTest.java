package seedu.tache.model.task;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.tag.UniqueTagList;

public class TaskTest {

    //@@author A0139961U
    @Test
    public void isWithinDate_validWithinDate_success() throws IllegalValueException {
        Date today = new Date();
        Task task1 = new Task(new Name("TestTask1"), Optional.of(new DateTime("yesterday")),
                Optional.of(new DateTime("tomorrow")), new UniqueTagList("TestTag"), true,
                RecurInterval.NONE, new ArrayList<Date>());
        assertTrue(task1.isWithinDate(today));
        Task task2 = new Task(new Name("TestTask2"), Optional.of(new DateTime("today 000000")),
                Optional.of(new DateTime("today 235959")), new UniqueTagList("TestTag"), true,
                RecurInterval.NONE, new ArrayList<Date>());
        assertTrue(task2.isWithinDate(today));
    }

    @Test
    public void isWithinDate_validWithinDate_failure() throws IllegalValueException {
        Date today = new Date();
        Task task1 = new Task(new Name("TestTask1"), Optional.of(new DateTime("yesterday 0000")),
                Optional.of(new DateTime("yesterday 2359")), new UniqueTagList("TestTag"), true,
                RecurInterval.NONE, new ArrayList<Date>());
        assertFalse(task1.isWithinDate(today));
        Task task2 = new Task(new Name("TestTask2"), Optional.of(new DateTime("tomorrow 0000")),
                Optional.of(new DateTime("tomorrow 2359")), new UniqueTagList("TestTag"), true,
                RecurInterval.NONE, new ArrayList<Date>());
        assertFalse(task2.isWithinDate(today));
    }
    //@@author

}
