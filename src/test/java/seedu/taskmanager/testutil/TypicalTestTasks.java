package seedu.taskmanager.testutil;

import seedu.taskmanager.commons.exceptions.IllegalValueException;
import seedu.taskmanager.model.TaskManager;
import seedu.taskmanager.model.task.Task;
import seedu.taskmanager.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask buy1, buy2, meeting1, meeting2, submit1, submit2, send1, send2, call1;

    public TypicalTestTasks() {
        try {
            buy1 = new TaskBuilder().withTitle("Buy Banana")
                    .withDescription("Sunpride brand").withEndDate("12/03/2017")
                    .withStartDate("12/03/2017")
                    .withRepeat("DAY")
                    .withTags("friends").build();
            buy2 = new TaskBuilder().withTitle("Buy new house").withDescription("at 311, Clementi Ave 2, #02-25")
                    .withEndDate("15/03/2017").withStartDate("12/03/2017")
                    .withRepeat("DAY")
                    .withTags("owesMoney", "friends").build();
            meeting1 = new TaskBuilder().withTitle("Meeting boss").withStartDate("17/03/2017")
                    .withEndDate("17/03/2017").withDescription("at the office")
                    .withRepeat("DAY").build();
            meeting2 = new TaskBuilder().withTitle("Meeting Daniel Meier").withStartDate("18/03/2017")
                    .withEndDate("12/03/2017").withDescription("at 10th street")
                    .withRepeat("DAY").build();
            submit1 = new TaskBuilder().withTitle("Submit CS2013").withStartDate("03/04/2017")
                    .withEndDate("05/04/2017").withDescription("TaskManager Version 1.0.2")
                    .withRepeat("DAY").build();
            submit2 = new TaskBuilder().withTitle("Submit FYP Report").withStartDate("31/03/2017")
                    .withEndDate("31/03/2017").withDescription("a big burden")
                    .withRepeat("DAY").build();
            send1 = new TaskBuilder().withTitle("Send encyrpted package").withStartDate("14/03/2017")
                    .withEndDate("20/03/2017").withDescription("CIA Secret Files")
                    .withRepeat("DAY").build();

            // Manually added
            send2 = new TaskBuilder().withTitle("Send package to Hoon Meier").withStartDate("20/03/2017")
                    .withEndDate("25/03/2017").withDescription("at 345 Little India")
                    .withRepeat("DAY").build();
            call1 = new TaskBuilder().withTitle("Call Ida Mueller").withStartDate("12/03/2017")
                    .withEndDate("30/03/2017").withDescription("Discuss about Chicago Ave")
                    .withRepeat("DAY").build();
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
        return new TestTask[]{buy1, buy2, meeting1, meeting2, submit1, submit2, send1};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
