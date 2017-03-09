package seedu.onetwodo.testutil;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withDescription("123, Jurong West Ave 6, #08-111").withEndDate("alice@gmail.com")
                    .withStartDate("85355255")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withDescription("311, Clementi Ave 2, #02-25")
                    .withEndDate("johnd@gmail.com").withStartDate("98765432")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withStartDate("95352563")
                    .withEndDate("heinz@yahoo.com").withDescription("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withStartDate("87652533")
                    .withEndDate("cornelia@google.com").withDescription("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withStartDate("9482224")
                    .withEndDate("werner@gmail.com").withDescription("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withStartDate("9482427")
                    .withEndDate("lydia@gmail.com").withDescription("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withStartDate("9482442")
                    .withEndDate("anna@google.com").withDescription("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withStartDate("8482424")
                    .withEndDate("stefan@mail.com").withDescription("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withStartDate("8482131")
                    .withEndDate("hans@google.com").withDescription("chicago ave").build();
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
