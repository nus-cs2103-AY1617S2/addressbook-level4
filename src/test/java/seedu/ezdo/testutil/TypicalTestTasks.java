package seedu.ezdo.testutil;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.model.EzDo;
import seedu.ezdo.model.todo.Task;
import seedu.ezdo.model.todo.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withStartDate("123, Jurong West Ave 6, #08-113").withEmail("alice@gmail.com")
                    .withPriority("1").withDueDate("14/03/2015")
                    .withTags("friends").build();
            benson = new TaskBuilder().withName("Benson Meier").withStartDate("311, Clementi Ave 2, #02-25")
                    .withEmail("johnd@gmail.com").withDueDate("12/02/2015").withPriority("1")
                    .withTags("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriority("2").withDueDate("13/03/2015")
                    .withEmail("heinz@yahoo.com").withStartDate("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriority("2").withDueDate("14/04/2015")
                    .withEmail("cornelia@google.com").withStartDate("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriority("3").withDueDate("15/05/2015")
                    .withEmail("werner@gmail.com").withStartDate("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriority("2").withDueDate("16/06/2015")
                    .withEmail("lydia@gmail.com").withStartDate("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriority("3").withDueDate("17/07/2015")
                    .withEmail("anna@google.com").withStartDate("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriority("1").withDueDate("18/08/2015")
                    .withEmail("stefan@mail.com").withStartDate("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriority("2").withDueDate("19/09/2015")
                    .withEmail("hans@google.com").withStartDate("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadEzDoWithSampleData(EzDo ez) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ez.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public EzDo getTypicalEzDo() {
        EzDo ez = new EzDo();
        loadEzDoWithSampleData(ez);
        return ez;
    }
}
