package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoApp;
import seedu.address.model.person.Task;
import seedu.address.model.person.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withTags("friends").withNotes("")
                    .withCompletion("false").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withStart("today").withDeadline("tomorrow")
                    .withTags("owesMoney", "friends")
                    .withPriority(1).withNotes("")
                    .withCompletion("false").build();
            carl = new TaskBuilder().withName("Carl Kurz")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();
            daniel = new TaskBuilder().withName("Daniel Meier")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();
            elle = new TaskBuilder().withName("Elle Meyer")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();
            fiona = new TaskBuilder().withName("Fiona Kunz")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();
            george = new TaskBuilder().withName("George Best")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();
            ida = new TaskBuilder().withName("Ida Mueller")
                    .withStart("today").withDeadline("tomorrow")
                    .withPriority(1).withNotes("").withCompletion("false").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoAppWithSampleData(ToDoApp ab) {
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

    public ToDoApp getTypicalToDoApp() {
        ToDoApp ab = new ToDoApp();
        loadToDoAppWithSampleData(ab);
        return ab;
    }
}
