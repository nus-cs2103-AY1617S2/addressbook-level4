package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, submission, hoon, ida;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("3")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withCategories("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withInformation("311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("3")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withCategories("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriorityLevel("3")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriorityLevel("2")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriorityLevel("2")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriorityLevel("2")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("little tokyo").build();
            submission = new TaskBuilder().withName("Submit progress report").withPriorityLevel("3")
                    .withStartDateTime("Feb 9, 2017 5pm")
                    .withEndDateTime("10am Feb 24, 2017 5pm")
                    .withInformation("notify department head")
                    .withCategories("Work", "Project").build();
            george = new TaskBuilder().withName("George Best").withPriorityLevel("2")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriorityLevel("2")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriorityLevel("2")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("chicago ave").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBossWithSampleData(TaskBoss ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, submission, george};
    }

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
