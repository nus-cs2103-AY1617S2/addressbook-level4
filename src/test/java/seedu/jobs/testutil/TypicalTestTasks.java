package seedu.jobs.testutil;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.task.Person;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withDescription("123, Jurong West Ave 6, #08-111").withEndTime("alice@gmail.com")
                    .withStartTime("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                    .withEndTime("johnd@gmail.com").withStartTime("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withStartTime("95352563")
                    .withEndTime("heinz@yahoo.com").withDescription("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withStartTime("87652533")
                    .withEndTime("cornelia@google.com").withDescription("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withStartTime("9482224")
                    .withEndTime("werner@gmail.com").withDescription("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withStartTime("9482427")
                    .withEndTime("lydia@gmail.com").withDescription("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withStartTime("9482442")
                    .withEndTime("anna@google.com").withDescription("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withStartTime("8482424")
                    .withEndTime("stefan@mail.com").withDescription("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withStartTime("8482131")
                    .withEndTime("hans@google.com").withDescription("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskBook ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalPersons()) {
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

    public TaskBook getTypicalAddressBook() {
        TaskBook ab = new TaskBook();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
