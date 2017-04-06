package seedu.jobs.testutil;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.model.TaskBook;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.UniqueTaskList;
import seedu.jobs.model.task.UniqueTaskList.IllegalTimeException;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask CS3101, CS3102, CS3103, CS3104, CS3105, CS3106, CS3107, CS4101, CS4102;

    public TypicalTestTasks() {
        try {
            CS3101 = new TaskBuilder().withName("CS3101")
                    .withDescription("chapter 1 to chapter 6").withEndTime("10/03/2017 17:00")
                    .withStartTime("10/03/2017 15:00")
                    .withTags("test").withPeriod("0").build();
            CS3102 = new TaskBuilder().withName("CS3102").withDescription("Data Structure")
                    .withEndTime("17/03/2017 10:00").withStartTime("17/03/2017 08:00")
                    .withTags("lectures").withPeriod("0").build();
            CS3103 = new TaskBuilder().withName("CS3103").withStartTime("17/03/2017 14:00")
                    .withEndTime("17/03/2017 16:00").withDescription("group project meeting").withPeriod("0").build();

            CS3104 = new TaskBuilder().withName("CS3104").withStartTime("17/03/2017 09:00")
                    .withEndTime("19/03/2017 23:59").withDescription("assignment 3 deadline").withPeriod("0").build();
            CS3105 = new TaskBuilder().withName("CS3105").withStartTime("14/03/2017 09:00")
                    .withEndTime("14/03/2017 23:59").withDescription("feedback deadline").withPeriod("0").build();
            CS3106 = new TaskBuilder().withName("CS3106").withStartTime("14/03/2017 12:00")
                    .withEndTime("14/03/2017 16:00").withDescription("group poeject meeting").withPeriod("0").build();
            CS3107 = new TaskBuilder().withName("CS3107").withStartTime("01/04/2017 08:00")
                    .withEndTime("01/04/2017 10:00").withDescription("consultation with tutor").withPeriod("0").build();


            // Manually added
            CS4101 = new TaskBuilder().withName("CS4101").withStartTime("20/03/2017 08:00")
                    .withEndTime("20/03/2017 10:00").withDescription("in-class test").withPeriod("0").build();
            CS4102 = new TaskBuilder().withName("CS4102").withStartTime("20/03/2017 12:00")
                    .withEndTime("20/03/2017 14:00").withDescription("in-class test").withPeriod("0").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskBookWithSampleData(TaskBook ab) throws IllegalTimeException {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }

        }
    }

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{CS3101, CS3102, CS3103, CS3104, CS3105, CS3106, CS3107};
    }

    public TaskBook getTypicalTaskBook() throws IllegalTimeException {
        TaskBook ab = new TaskBook();
        loadTaskBookWithSampleData(ab);
        return ab;
    }
}
