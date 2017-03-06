package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withTitle("Alice Pauline")
                    .withEndTime("123, Jurong West Ave 6, #08-111").withStartTime("alice@gmail.com")
                    .withVenue("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withTitle("Benson Meier").withEndTime("311, Clementi Ave 2, #02-25")
                    .withStartTime("johnd@gmail.com").withVenue("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withTitle("Carl Kurz").withVenue("95352563")
                    .withStartTime("heinz@yahoo.com").withEndTime("wall street").build();
            daniel = new TaskBuilder().withTitle("Daniel Meier").withVenue("87652533")
                    .withStartTime("cornelia@google.com").withEndTime("10th street").build();
            elle = new TaskBuilder().withTitle("Elle Meyer").withVenue("9482224")
                    .withStartTime("werner@gmail.com").withEndTime("michegan ave").build();
            fiona = new TaskBuilder().withTitle("Fiona Kunz").withVenue("9482427")
                    .withStartTime("lydia@gmail.com").withEndTime("little tokyo").build();
            george = new TaskBuilder().withTitle("George Best").withVenue("9482442")
                    .withStartTime("anna@google.com").withEndTime("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTitle("Hoon Meier").withVenue("8482424")
                    .withStartTime("stefan@mail.com").withEndTime("little india").build();
            ida = new TaskBuilder().withTitle("Ida Mueller").withVenue("8482131")
                    .withStartTime("hans@google.com").withEndTime("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {
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

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
