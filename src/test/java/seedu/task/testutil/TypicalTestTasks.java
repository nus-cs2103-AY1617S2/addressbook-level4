package seedu.task.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.model.TaskManager;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask apply, buy, calculate, decide, eat, find, give, handle, identify, jump, kick, look, mark, neglect;

    public TypicalTestTasks() {
        try {
            apply = new TaskBuilder().withName("Apply for internship").withStartDate("04-03-1989 23:59")
                    .withEndDate("08-03-1989 23:59").withRemark("checkout career fair")
                    .withLocation("123, Jurong West Ave 6").withTags("personal").build();
            buy = new TaskBuilder().withName("Buy pencil").withStartDate("4-08-2030 23:59")
                    .withEndDate("09-08-2032 23:59").withRemark("Get the cheapest one")
                    .withLocation("311, Clementi Ave 2, #02-25").withTags("school", "shopping").build();
            calculate = new TaskBuilder().withName("Calculate expenses").withStartDate("5-04-03 23:59")
                    .withEndDate("05-05-2003 23:59").withRemark("Calculate for march").withLocation("wall street")
                    .build();
            decide = new TaskBuilder().withName("Decide on birthday dinner").withStartDate("11-11-02 23:59")
                    .withEndDate("07-05-2003 23:59").withRemark("Japanese or western?").withLocation("10th street")
                    .build();
            eat = new TaskBuilder().withName("Eat dinner with Bob").withStartDate("9-1-1992 23:59")
                    .withEndDate("09-02-1992 23:59").withRemark("Somewhere cheap").withLocation("michegan ave").build();
            find = new TaskBuilder().withName("Find old documents").withStartDate("4-1-2023 23:59")
                    .withEndDate("04-02-2027 23:59").withRemark("Should be somewhere at home").withLocation("home")
                    .build();
            give = new TaskBuilder().withName("Give dad present").withStartDate("3-8-10 23:59")
                    .withEndDate("3-10-2011 23:59").withRemark("Maybe a tie").withLocation("4th street").build();

            // Manually added
            handle = new TaskBuilder().withName("Handle 2103 project").withStartDate("9-3-1993 23:59")
                    .withEndDate("9-12-1999 23:59").withRemark("In charge of testing").withLocation("little india")
                    .build();
            identify = new TaskBuilder().withName("Identify problems in report").withStartDate("3-12-99 23:59")
                    .withEndDate("3-22-2131 23:59").withRemark("Might need help from teammates")
                    .withLocation("chicago ave").build();
            jump = new TaskBuilder().withName("Jump ropes").withStartDate("01-1-99 23:59")
                    .withEndDate("1-02-2131 23:59").withRemark("please exercise").withLocation("ERC field").build();
            kick = new TaskBuilder().withName("Kick over the cat").withStartDate("11-01-99 23:59")
                    .withEndDate("11-24-2031 23:59").withRemark("").withLocation("Physics Lab").build();
            look = new TaskBuilder().withName("Look for the exam location").withStartDate("2-2-99 23:59")
                    .withEndDate("2-27-2112 23:59").withRemark("Please help me find location").withLocation("").build();
            mark = new TaskBuilder().withName("Mark the tasks completed").withStartDate("1-23-99 23:59").withEndDate("")
                    .withRemark("Tick the completed tasks").withLocation("").build();
            neglect = new TaskBuilder().withName("Neglect all warnings").withStartDate("").withEndDate("8-9-2017 23:59")
                    .withRemark("Neglect this remark please").withLocation("").build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager tm) {
        for (TestTask task : asList(new TypicalTestTasks().getTypicalTasks())) {
            try {
                tm.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                    assert false : "not possible";
            } catch (IllegalValueException ive) {
                    assert false : "not possible";
            }
        }
    }

    /**
     *
     * @return the typical tasks in sorted order
     */
    public TestTask[] getTypicalTasks() {
        TestTask[] typicalTasks = new TestTask[] { apply, buy, calculate, decide, eat, find, give};
        //, handle, identify, jump , kick, look, mark, neglect   manually added tasks,
        //that cause errors because of empty fields

        Arrays.sort(typicalTasks);
        return typicalTasks;
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager tm = new TaskManager();
        loadTaskManagerWithSampleData(tm);
        return tm;
    }

    public static ArrayList<TestTask> asList(TestTask[] testTasks) {
        ArrayList<TestTask> testTaskList = new ArrayList<>();
        for (TestTask tt : testTasks) {
            testTaskList.add(tt);
        }
        return testTaskList;
    }
}
