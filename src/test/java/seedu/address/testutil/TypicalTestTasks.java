package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.task.Task;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withDeadline("12/06/8111")
                    .withDescription("alice@gmail.com")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withDeadline("27/02/2025")
                    .withDescription("johnd@gmail.com")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz")
                    .withDescription("heinz@yahoo.com")
                    .withDeadline("11/11/2011").build();
            daniel = new TaskBuilder().withName("Daniel Meier")
                    .withDescription("cornelia@google.com")
                    .withDeadline("10/10/2010").build();
            elle = new TaskBuilder().withName("Elle Meyer")
                    .withDescription("werner@gmail.com")
                    .withDeadline("12/12/2012").build();
            fiona = new TaskBuilder().withName("Fiona Kunz")
                    .withDescription("lydia@gmail.com")
                    .withDeadline("22/02/2222").build();
            george = new TaskBuilder().withName("George Best")
                    .withDescription("anna@google.com")
                    .withDeadline("04/04/2004").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier")
                    .withDescription("stefan@mail.com")
                    .withDeadline("01/01/2001").build();
            ida = new TaskBuilder().withName("Ida Mueller")
                    .withDescription("hans@google.com")
                    .withDeadline("02/02/2002").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            ab.addTask(new Task(task));
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
