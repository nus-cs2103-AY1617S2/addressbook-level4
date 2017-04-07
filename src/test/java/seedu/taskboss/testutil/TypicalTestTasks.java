package seedu.taskboss.testutil;

import seedu.taskboss.commons.exceptions.IllegalValueException;
import seedu.taskboss.logic.commands.AddCommand;
import seedu.taskboss.model.TaskBoss;
import seedu.taskboss.model.task.Recurrence.Frequency;
import seedu.taskboss.model.task.Task;
import seedu.taskboss.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI, taskJ, taskK, taskL;

    public TypicalTestTasks() {
        try {
            taskA = new TaskBuilder().withName("Attend wedding")
                    .withInformation("123, Jurong West Ave 6, #08-111")
                    .withPriorityLevel("Yes")
                    .withStartDateTime("Feb 18, 2017 5pm")
                    .withEndDateTime("Mar 28, 2017 5pm")
                    .withRecurrence(Frequency.NONE)
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS, "Friends").build();
            taskB = new TaskBuilder().withName("Birthday party")
                    .withInformation("311, Clementi Ave 2, #02-25")
                    .withPriorityLevel("Yes")
                    .withRecurrence(Frequency.NONE)
                    .withStartDateTime("Feb 23, 2017 10pm")
                    .withEndDateTime("Jun 28, 2017 5pm")
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS, "Friends", "Owesmoney").build();
            taskC = new TaskBuilder().withName("Clean house").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2017 11pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withRecurrence(Frequency.NONE)
                    .withInformation("wall street").build();
            taskD = new TaskBuilder().withName("Debug code").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 20, 2017 11.30pm")
                    .withEndDateTime("Apr 28, 2017 3pm")
                    .withRecurrence(Frequency.NONE)
                    .withInformation("10th street").build();
            taskE = new TaskBuilder().withName("Ensure code quality").withPriorityLevel("No")
                    .withStartDateTime("Feb 22, 2017 5pm")
                    .withEndDateTime("Feb 28, 2017 5pm")
                    .withRecurrence(Frequency.MONTHLY)
                    .withInformation("michegan ave").build();
            taskF = new TaskBuilder().withName("Fix errors in report").withPriorityLevel("No")
                    .withStartDateTime("Feb 21, 2017 1pm")
                    .withEndDateTime("Dec 10, 2017 5pm")
                    .withRecurrence(Frequency.WEEKLY)
                    .withInformation("little tokyo")
                    .withCategories("School").build();
            taskG = new TaskBuilder().withName("Game project player testing").withPriorityLevel("Yes")
                    .withStartDateTime("Jan 1, 2017 5pm")
                    .withEndDateTime("Nov 28, 2017 5pm")
                    .withRecurrence(Frequency.DAILY)
                    .withInformation("4th street").build();

            // Manually added
            taskH = new TaskBuilder().withName("Having dinner with Hoon Meier").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2018 5pm")
                    .withEndDateTime("Feb 28, 2018 5pm")
                    .withInformation("little india")
                    .withRecurrence(Frequency.NONE)
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
            taskI = new TaskBuilder().withName("Invite friends home").withPriorityLevel("Yes")
                    .withStartDateTime("Feb 19, 2019 5pm")
                    .withEndDateTime("Feb 28, 2019 5pm")
                    .withInformation("chicago ave")
                    .withRecurrence(Frequency.YEARLY)
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
            taskJ = new TaskBuilder().withName("Join Leader Group").withPriorityLevel("Yes")
                    .withStartDateTime("next sat 5pm")
                    .withEndDateTime("tomorrow")
                    .withInformation("Silicon Valley")
                    .withRecurrence(Frequency.MONTHLY)
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
            taskK = new TaskBuilder().withName("Kelvin Koo party").withPriorityLevel("Yes")
                    .withStartDateTime("Dec 20 2019")
                    .withEndDateTime("Dec 21 2019")
                    .withInformation("clementi ave 2")
                    .withRecurrence(Frequency.NONE)
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
            taskL = new TaskBuilder().withName("Lower costs of product").withPriorityLevel("Yes")
                    .withStartDateTime("Dec 30 2019")
                    .withEndDateTime("Jan 2 2020")
                    .withInformation("update John on new price")
                    .withRecurrence(Frequency.NONE)
                    .withCategories(AddCommand.BUILT_IN_ALL_TASKS).build();
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
        return new TestTask[]{taskC, taskE, taskA, taskD, taskB, taskG, taskF};
    }

    public TaskBoss getTypicalTaskBoss() {
        TaskBoss ab = new TaskBoss();
        loadTaskBossWithSampleData(ab);
        return ab;
    }
}
