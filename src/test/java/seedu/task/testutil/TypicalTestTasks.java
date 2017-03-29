package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, ida, hoon;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withDescription("Say hello to Alice Pauline")
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2100")
                    .withPriority("3")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Say goodbye to Benson Meier")
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/12/2110")
                    .withPriority("2")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Say hi to Carl Kurz")
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/12/2110")
                    .withPriority("2").build();
            daniel = new TaskBuilder().withDescription("Say farewell to Daniel Meier")
                    .withStartTiming("01/02/1017")
                    .withEndTiming("02/03/2100")
                    .withPriority("3").build();
            elle = new TaskBuilder().withDescription("Say howdy to Elle Meyer")
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2110")
                    .withPriority("1").build();
            fiona = new TaskBuilder().withDescription("Say so long to Fiona Kunz")
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2100")
                    .withPriority("1")
                    .withTags("owesMoney", "friends").build();
            george = new TaskBuilder().withDescription("Say greetings to George Best")
                    .withStartTiming("01/02/2017")
                    .withEndTiming("02/03/2100")
                    .withPriority("3").build();
            ida = new TaskBuilder().withDescription("eat pizza")
                    .withStartTiming("01/02/2020")
                    .withEndTiming("02/10/2100")
                    .withPriority("2").build();
            hoon = new TaskBuilder().withDescription("go gym")
                    .withStartTiming("01/02/2020")
                    .withEndTiming("12/10/2100")
                    .withPriority("3").build();
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
