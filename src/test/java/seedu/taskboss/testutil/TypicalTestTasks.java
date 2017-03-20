package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask alice, benson, carl, daniel, elle, fiona, george, hoon, ida, johnny, kelvin;

    public TypicalTestTasks() {
        try {
            alice = new TaskBuilder().withName("Alice Pauline")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("High priority")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withCategories("friends").build();
            benson = new TaskBuilder().withName("Benson Meier")
                    .withInformation("311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("No priority")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withCategories("owesMoney", "friends").build();
            carl = new TaskBuilder().withName("Carl Kurz").withPriorityLevel("High priority")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("wall street").build();
            daniel = new TaskBuilder().withName("Daniel Meier").withPriorityLevel("No priority")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("10th street").build();
            elle = new TaskBuilder().withName("Elle Meyer").withPriorityLevel("High priority")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("michegan ave").build();
            fiona = new TaskBuilder().withName("Fiona Kunz").withPriorityLevel("No priority")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("little tokyo").build();
            george = new TaskBuilder().withName("George Best").withPriorityLevel("High priority")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("4th street").build();

            // Manually added
            hoon = new TaskBuilder().withName("Hoon Meier").withPriorityLevel("No priority")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("little india").build();
            ida = new TaskBuilder().withName("Ida Mueller").withPriorityLevel("High priority")
                    .withStartDateTime("Feb 19, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withInformation("chicago ave").build();
            johnny = new TaskBuilder().withName("Johnny Depp").withPriorityLevel("No priority")
                    .withStartDateTime("next sat 5pm")
                    .withEndDateTime("tomorrow")
                    .withInformation("Silicon Valley").build();
            kelvin = new TaskBuilder().withName("Kelvin Koo").withPriorityLevel("High priority")
                    .withStartDateTime("today 10am")
                    .withEndDateTime("next wed")
                    .withInformation("clementi ave 2").build();
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
        return new TestTask[]{alice, benson, carl, daniel, elle, fiona, george};
    }

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
