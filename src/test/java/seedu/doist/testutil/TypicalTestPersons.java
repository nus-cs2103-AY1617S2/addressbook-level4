package seedu.doist.testutil;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.model.TodoList;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline").build();  //.withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("VERYIMPORTANT").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("IMPORTANT").build();
            elle = new TaskBuilder().withName("Elle Meyer").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").build();
            george = new TaskBuilder().withName("George Best").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").build();
            ida = new TaskBuilder().withName("Ida Mueller").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TodoList ab) {
        for (TestPerson person : new TypicalTestPersons().getTypicalTasks()) {
            try {
                ab.addTask(new Task(person));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalTasks() {
        return new TestPerson[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TodoList getTypicalAddressBook() {
        TodoList ab = new TodoList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
