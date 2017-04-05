package seedu.task.testutil;

import java.util.ArrayList;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.RecurringTaskOccurrence;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, ida, hoon;
    public TestTask recMonth;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withDescription("Say hello to Alice Pauline")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2100")
                    .withPriority("3")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Say goodbye to Benson Meier")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/12/2110")
                    .withPriority("2")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Say hi to Carl Kurz")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/12/2110")
                    .withPriority("2").build();
            daniel = new TaskBuilder().withDescription("Say farewell to Daniel Meier")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/1017")
                    .withEndTiming("02/03/2100")
                    .withPriority("3").build();
            elle = new TaskBuilder().withDescription("Say howdy to Elle Meyer")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2110")
                    .withPriority("1").build();
            fiona = new TaskBuilder().withDescription("Say so long to Fiona Kunz")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2100")
                    .withPriority("1")
                    .withTags("owesMoney", "friends").build();
            george = new TaskBuilder().withDescription("Say greetings to George Best")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2100")
                    .withPriority("3").build();
            ida = new TaskBuilder().withDescription("eat pizza")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2020")
                    .withEndTiming("02/10/2100")
                    .withPriority("2").build();
            hoon = new TaskBuilder().withDescription("go gym")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency(null)
                    .withStartTiming("01/02/2020")
                    .withEndTiming("12/10/2100")
                    .withPriority("3").build();
            recMonth = new TaskBuilder().withDescription("recMonth")
                    .withOccurrences(new ArrayList<RecurringTaskOccurrence>())
                    .withFrequency("2m")
                    .withStartTiming("01/01/2017")
                    .withEndTiming("05/01/2017")
                    .withPriority("1").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }
    public TestTask[] getEmptyTasks() {
        return new TestTask[]{};
    }
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{fiona};
    }

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
