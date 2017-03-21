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
                    .withDate("123, Jurong West Ave 6, #08-111").withStartTime("alice@gmail.com")
                    .withEndTime("85355255")
                    /*.withCategories("friends")*/.build();
            benson = new TaskBuilder().withTaskName("Benson Meier").withDate("311, Clementi Ave 2, #02-25")
                    .withStartTime("johnd@gmail.com").withEndTime("98765432")
                    /*.withCategories("owesMoney", "friends")*/.build();
            carl = new TaskBuilder().withTaskName("Carl Kurz").withDate("95352563")
                    .withStartTime("heinz@yahoo.com").withEndTime("wall street").build();
            daniel = new TaskBuilder().withTaskName("Daniel Meier").withDate("87652533")
                    .withStartTime("cornelia@google.com").withEndTime("10th street").build();
            elle = new TaskBuilder().withTaskName("Elle Meyer").withDate("9482224")
                    .withStartTime("werner@gmail.com").withEndTime("michegan ave").build();
            fiona = new TaskBuilder().withTaskName("Fiona Kunz").withDate("9482427")
                    .withStartTime("lydia@gmail.com").withEndTime("little tokyo").build();
            george = new TaskBuilder().withTaskName("George Best").withDate("9482442")
                    .withStartTime("anna@google.com").withEndTime("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withTaskName("Hoon Meier").withDate("8482424")
                    .withStartTime("stefan@mail.com").withEndTime("little india").build();
            ida = new TaskBuilder().withTaskName("Ida Mueller").withDate("8482131")
                    .withStartTime("hans@google.com").withEndTime("chicago ave").build();
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
