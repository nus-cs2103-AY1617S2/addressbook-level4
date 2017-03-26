package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

//@@author A0146789H
/**
 * Creates sample test people.
 */
public class TypicalTestPersons {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TestTaskBuilder().withName("Alice Pauline")
                    .withCompletion(true).withEndDate("03/06/17 00:00")
                    .withStartDate("03/06/17 00:00")
                    .withTags("friends").build();
            benson = new TestTaskBuilder().withName("Benson Meier").withCompletion(false)
                    .withEndDate("03/06/17 00:00").withStartDate("03/06/17 00:00")
                    .withTags("owesMoney", "friends").build();
            carl = new TestTaskBuilder().withName("Carl Kurz").withStartDate("03/06/17 00:00")
                    .withEndDate("03/06/17 00:00").withCompletion(false).withTags("test").build();
            daniel = new TestTaskBuilder().withName("Daniel Meier").withStartDate("03/06/17 00:000")
                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
            elle = new TestTaskBuilder().withName("Elle Meyer").withStartDate("03/06/17 00:000")
                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
            fiona = new TestTaskBuilder().withName("Fiona Kunz").withStartDate("03/06/17 00:000")
                    .withEndDate("03/06/17 00:000").withCompletion(true).withTags("test").build();
            george = new TestTaskBuilder().withName("George Best").withStartDate("03/06/17 00:000")
                    .withEndDate("03/06/17 00:000").withCompletion(true).withTags("test").build();

            // Manually added
            hoon = new TestTaskBuilder().withName("Hoon Meier").withStartDate("03/06/17 00:000")
                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
            ida = new TestTaskBuilder().withName("Ida Mueller").withStartDate("03/06/17 00:000")
                    .withEndDate("03/06/17 00:000").withCompletion(false).withTags("test").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
