package seedu.bulletjournal.testutil;

import seedu.bulletjournal.commons.exceptions.IllegalValueException;
import seedu.bulletjournal.model.TodoList;
import seedu.bulletjournal.model.task.Task;
import seedu.bulletjournal.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask assignment, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            assignment = new TaskBuilder().withTaskName("Assignment for CS2103")
                    .withDetail("123, Jurong West Ave 6, #08-111").withStatus("alice@gmail.com")
                    .withDeadline("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTaskName("Benson Meier").withDetail("311, Clementi Ave 2, #02-25")
                    .withStatus("johnd@gmail.com").withDeadline("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTaskName("Carl Kurz").withDeadline("95352563")
                    .withStatus("heinz@yahoo.com").withDetail("wall street").build();
            daniel = new TaskBuilder().withTaskName("Daniel Meier").withDeadline("87652533")
                    .withStatus("cornelia@google.com").withDetail("10th street").build();
            elle = new TaskBuilder().withTaskName("Elle Meyer").withDeadline("9482224")
                    .withStatus("werner@gmail.com").withDetail("michegan ave").build();
            fiona = new TaskBuilder().withTaskName("Fiona Kunz").withDeadline("9482427")
                    .withStatus("lydia@gmail.com").withDetail("little tokyo").build();
            george = new TaskBuilder().withTaskName("George Best").withDeadline("9482442")
                    .withStatus("anna@google.com").withDetail("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTaskName("Hoon Meier").withDeadline("8482424")
                    .withStatus("stefan@mail.com").withDetail("little india").build();
            ida = new TaskBuilder().withTaskName("Ida Mueller").withDeadline("8482131")
                    .withStatus("hans@google.com").withDetail("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TodoList ab) {
        for (TestTask person : new TypicalTestTasks().getTypicalPersons()) {
            try {
                ab.addPerson(new Task(person));
            } catch (UniqueTaskList.DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalPersons() {
        return new TestTask[]{assignment, benson, carl, daniel, elle, fiona, george};
    }

    public TodoList getTypicalAddressBook() {
        TodoList ab = new TodoList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
