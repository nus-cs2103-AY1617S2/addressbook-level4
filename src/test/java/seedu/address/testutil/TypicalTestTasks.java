package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withDescription("Alice Pauline's big task")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2100")
                    .withPriority("3")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Benson Meier's big task")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/12/2110")
                    .withPriority("2")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Carl Kurz's big task")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/12/2110")
                    .withPriority("2").build();
            daniel = new TaskBuilder().withDescription("Daniel Meier's big task")
                    .withStartDate("01/02/1017")
                    .withEndDate("02/03/2100")
                    .withPriority("3").build();
            elle = new TaskBuilder().withDescription("Elle Meyer's big task")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2110")
                    .withPriority("1").build();
            fiona = new TaskBuilder().withDescription("Fiona Kunz's big task")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2100")
                    .withPriority("1").build();
            george = new TaskBuilder().withDescription("George Best's big task")
                    .withStartDate("01/02/2017")
                    .withEndDate("02/03/2100")
                    .withPriority("3").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {
        for (TestTask Task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(Task));
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
