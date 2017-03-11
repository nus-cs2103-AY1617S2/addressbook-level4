package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskList;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList.DuplicateTaskException;

/**
 *
 */
public class TypicalTestPersons {

    public TestPerson alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder().withName("Walk the dog")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Important").build();
            benson = new PersonBuilder().withName("Walk the cat")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Important").build();
            carl = new PersonBuilder().withName("Walk the cow")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Important").build();
            daniel = new PersonBuilder().withName("Walk the nyan cat")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Important").build();
            elle = new PersonBuilder().withName("Walk the fish")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Important").build();
            fiona = new PersonBuilder().withName("Walk the lion")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Unimportant").build();
            george = new PersonBuilder().withName("Walk the elephant")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").withTags("Unimportant").build();

            // Manually added
            hoon = new PersonBuilder().withName("Walk the tiger")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").build();
            ida = new PersonBuilder().withName("Walk the zebra")
                    .withDescription("Take Zelda on a walk at the park").withStartDateTime("01/01/2016 0900")
                    .withEndDateTime("01/01/2016 1000").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskList ab) {
        for (TestPerson person : new TypicalTestPersons().getTypicalPersons()) {
            try {
                ab.addTask(new Task(person));
            } catch (DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestPerson[] getTypicalPersons() {
        return new TestPerson[] { alice, benson, carl, daniel, elle, fiona, george };
    }

    public TaskList getTypicalAddressBook() {
        TaskList ab = new TaskList();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
