package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withTaskName("Alice Pauline")
                    .withDate("123, Jurong West Ave 6, #08-111").withDeadline("alice@gmail.com")
                    .withStartTime("85355255")
                    /*.withCategories("friends")*/.build();
            benson = new TaskBuilder().withTaskName("Benson Meier").withDate("311, Clementi Ave 2, #02-25")
                    .withDeadline("johnd@gmail.com").withStartTime("98765432")
                    /*.withCategories("owesMoney", "friends")*/.build();
            carl = new TaskBuilder().withTaskName("Carl Kurz").withStartTime("95352563")
                    .withDeadline("heinz@yahoo.com").withDate("wall street").build();
            daniel = new TaskBuilder().withTaskName("Daniel Meier").withStartTime("87652533")
                    .withDeadline("cornelia@google.com").withDate("10th street").build();
            elle = new TaskBuilder().withTaskName("Elle Meyer").withStartTime("9482224")
                    .withDeadline("werner@gmail.com").withDate("michegan ave").build();
            fiona = new TaskBuilder().withTaskName("Fiona Kunz").withStartTime("9482427")
                    .withDeadline("lydia@gmail.com").withDate("little tokyo").build();
            george = new TaskBuilder().withTaskName("George Best").withStartTime("9482442")
                    .withDeadline("anna@google.com").withDate("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTaskName("Hoon Meier").withStartTime("8482424")
                    .withDeadline("stefan@mail.com").withDate("little india").build();
            ida = new TaskBuilder().withTaskName("Ida Mueller").withStartTime("8482131")
                    .withDeadline("hans@google.com").withDate("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
