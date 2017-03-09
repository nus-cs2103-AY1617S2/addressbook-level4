package seedu.task.testutil;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask apply, buy, calculate, decide, eat, find, give, handle, identify;

    public TypicalTestTasks() {
        try {
            apply = new TaskBuilder().withName("Apply for internship")
                    .withLocation("123, Jurong West Ave 6").withRemark("checkout career fair")
                    .withDate("08-03-1989")
                    .withTags("personal").build();
            buy = new TaskBuilder().withName("Buy pencil").withLocation("311, Clementi Ave 2, #02-25")
                    .withRemark("Get the cheapest one").withDate("09-08-2032")
                    .withTags("shopping", "school").build();
            calculate = new TaskBuilder().withName("Calculate expenses").withDate("05-05-2003")
                    .withRemark("Calculate for march").withLocation("wall street").build();
            decide = new TaskBuilder().withName("Decide on birthday dinner").withDate("07-05-2003")
                    .withRemark("Japanese or western?").withLocation("10th street").build();
            eat = new TaskBuilder().withName("Eat dinner with Bob").withDate("09-02-1992")
                    .withRemark("Somewhere cheap").withLocation("michegan ave").build();
            find = new TaskBuilder().withName("Find old documents").withDate("04-02-2027")
                    .withRemark("Should be somewhere at home").withLocation("home").build();
            give = new TaskBuilder().withName("Give dad present").withDate("13-05-2011")
                    .withRemark("Maybe a tie").withLocation("4th street").build();

            // Manually added
            handle = new TaskBuilder().withName("Handle 2103 project").withDate("23-12-1999")
                    .withRemark("In charge of testing").withLocation("little india").build();
            identify = new TaskBuilder().withName("Identify problems in report").withDate("24-02-2131")
                    .withRemark("Might need help from teammates").withLocation("chicago ave").build();
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
        return new TestTask[]{apply, buy, calculate, decide, eat, find, give};
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }
}
