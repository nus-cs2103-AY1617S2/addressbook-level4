package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withDescription("Alice Pauline")
                    .withTags("friends").build();
            benson = new TaskBuilder().withDescription("Benson Meier")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withDescription("Carl Kurz").build();
            daniel = new TaskBuilder().withDescription("Daniel Meier").build();
            elle = new TaskBuilder().withDescription("Elle Meyer").build();
            fiona = new TaskBuilder().withDescription("Fiona Kunz").build();
            george = new TaskBuilder().withDescription("George Best").build();

            // Manually added
            hoon = new TaskBuilder().withDescription("Hoon Meier")
                    .withDueDate("2018/02/09 1230")
                    .build();
            ida = new TaskBuilder().withDescription("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {
        for (TestTask person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskList getTypicalAddressBook() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
