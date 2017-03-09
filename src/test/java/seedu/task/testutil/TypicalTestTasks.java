package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withLocation("123, Jurong West Ave 6, #08-111").withRemark("alice@gmail.com")
                    .withDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withLocation("311, Clementi Ave 2, #02-25")
                    .withRemark("johnd@gmail.com").withDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withDate("95352563")
                    .withRemark("heinz@yahoo.com").withLocation("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withDate("87652533")
                    .withRemark("cornelia@google.com").withLocation("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withDate("9482224")
                    .withRemark("werner@gmail.com").withLocation("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withDate("9482427")
                    .withRemark("lydia@gmail.com").withLocation("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withDate("9482442")
                    .withRemark("anna@google.com").withLocation("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withDate("8482424")
                    .withRemark("stefan@mail.com").withLocation("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withDate("8482131")
                    .withRemark("hans@google.com").withLocation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalAddressBook() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
