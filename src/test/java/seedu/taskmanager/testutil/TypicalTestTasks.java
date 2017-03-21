package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask eatbreakfast, eatlunch, eatdinner, doCS, giveupCS, tryagainCS, regret, sampleEvent, sampleFloatingTask, sampleDeadline;

    public TypicalTestTasks() {
        try {
            eatbreakfast = new TaskBuilder().withTaskName("Eat breakfast with mom")
                    .withDate("03/03/17 1000").withEndTime("1100")
                    /*.withCategories("friends")*/.build(); // event
            eatlunch = new TaskBuilder().withTaskName("Eat lunch @ techno").withDate("04/03/17")
                    .withEndTime("1400").build(); // deadline
            eatdinner = new TaskBuilder().withTaskName("Eat dinner with my only 2 friends").withDate("09/03/17 1800")
                    .withEndTime("2000").build(); // event
            doCS = new TaskBuilder().withTaskName("Start on the CS2103 project")
                    .withStartTime("03/03/17").withEndTime("03/04/17").build(); // event
            giveupCS = new TaskBuilder().withTaskName("Give up on CS2103 project").withDate("04/04/17 1400").build(); // event
            tryagainCS = new TaskBuilder().withTaskName("Try again for CS2103")
                    .withStartTime("05/04/17").withEndTime("05/05/17").build(); // event
            regret = new TaskBuilder().withTaskName("Endless cycles of regret").build(); // floating task

            // Manually added
            sampleEvent = new TaskBuilder().withTaskName("Time to relax a little").withDate("06/05/17 1300").build();
            sampleFloatingTask = new TaskBuilder().withTaskName("Chiong all day everyday").build();
            sampleDeadline = new TaskBuilder().withTaskName("Get it done").withDate("06/05/17").withEndTime("1700").build();
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
        return new TestTask[]{eatbreakfast, eatlunch, eatdinner, doCS, giveupCS, tryagainCS, regret};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
