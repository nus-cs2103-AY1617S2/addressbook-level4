package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, ida, hoon;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withDescription("Say hello to Alice Pauline")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2100")
                    .withPriority("3")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Say goodbye to Benson Meier")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/12/2110")
                    .withPriority("2")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Say hi to Carl Kurz")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/12/2110")
                    .withPriority("2").build();
            daniel = new TaskBuilder().withDescription("Say farewell to Daniel Meier")
                    .withStartDate("01/02/1017")
                    .withEndDate("02/03/2100")
                    .withPriority("3").build();
            elle = new TaskBuilder().withDescription("Say howdy to Elle Meyer")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2110")
                    .withPriority("1").build();
            fiona = new TaskBuilder().withDescription("Say so long to Fiona Kunz")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2100")
                    .withPriority("1").build();
            george = new TaskBuilder().withDescription("Say greetings to George Best")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2100")
                    .withPriority("3").build();
            ida = new TaskBuilder().withDescription("Say greetings to Ida Noasdergen")
                    .withStartDate("01/02/2020")
                    .withEndDate("02/10/2100")
                    .withPriority("1").build();
            hoon = new TaskBuilder().withDescription("Say greetings to Hoon Loffschringer")
                    .withStartDate("01/02/2020")
                    .withEndDate("12/10/2100")
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

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskList getTypicalTaskList() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
