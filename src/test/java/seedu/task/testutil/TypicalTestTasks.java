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
                    .withStartDate("04-03-1989").withEndDate("08-03-1989")
                    .withRemark("checkout career fair").withLocation("123, Jurong West Ave 6")
                    .withTags("personal").build();
            buy = new TaskBuilder().withName("Buy pencil")
                    .withStartDate("4-08-2030").withEndDate("09-08-2032")
                    .withRemark("Get the cheapest one").withLocation("311, Clementi Ave 2, #02-25")
                    .withTags("shopping", "school").build();
            calculate = new TaskBuilder().withName("Calculate expenses")
                    .withStartDate("30-04-03").withEndDate("05-05-2003")
                    .withRemark("Calculate for march").withLocation("wall street").build();
            decide = new TaskBuilder().withName("Decide on birthday dinner")
                    .withStartDate("11-11-02").withEndDate("07-05-2003")
                    .withRemark("Japanese or western?").withLocation("10th street").build();
            eat = new TaskBuilder().withName("Eat dinner with Bob")
                    .withStartDate("13-7-1992").withEndDate("09-02-1992")
                    .withRemark("Somewhere cheap").withLocation("michegan ave").build();
            find = new TaskBuilder().withName("Find old documents")
                    .withStartDate("17-12-2023").withEndDate("04-02-2027")
                    .withRemark("Should be somewhere at home").withLocation("home").build();
            give = new TaskBuilder().withName("Give dad present")
                    .withStartDate("3-8-10").withEndDate("13-05-2011")
                    .withRemark("Maybe a tie").withLocation("4th street").build();

            // Manually added
            handle = new TaskBuilder().withName("Handle 2103 project")
                    .withStartDate("9-3-1993").withEndDate("23-12-1999")
                    .withRemark("In charge of testing").withLocation("little india").build();
            identify = new TaskBuilder().withName("Identify problems in report")
                    .withStartDate("31-12-99").withEndDate("24-02-2131")
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
