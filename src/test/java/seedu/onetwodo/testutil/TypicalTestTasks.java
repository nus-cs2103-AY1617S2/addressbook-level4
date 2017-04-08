package seedu.onetwodo.testutil;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.UniqueTaskList;

//@@author A0139343E
/**
 *  Tasks to be used for testing. Includes default tasks and tasks to be added.
 */
public class TypicalTestTasks {

    public TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI, taskJ,
            task1, task2, task3;

    public TypicalTestTasks() {
        try {
            // Event with all info
            taskA = new TaskBuilder().withName("guard duty")
                    .withStartDate("15/12/2018 7am").withEndDate("16/12/2018 11pm")
                    .withRecurring("monthly")
                    .withDescription("bring weapon")
                    .withTags("army", "work")
                    .withPriority('l')
                    .build();
            // Event with some missing info
            taskB = new TaskBuilder().withName("study at home")
                    .withStartDate("10 Mar 2018").withEndDate("13 mar 2018")
                    .withRecurring("weekly")
                    .withDescription("")
                    .withTags("work", "school")
                    .withPriority('l')
                    .build();
            // Event with many missing info
            taskC = new TaskBuilder().withName("meet boss")
                    .withStartDate("10 Mar 2018 08:00").withEndDate("10 mar 2018 12:00")
                    .withRecurring("yearly")
                    .withDescription("")
                    .withPriority('h')
                    .build();
            // Deadline with all date info
            taskD = new TaskBuilder().withName("submit cs2101 reflection")
                    .withStartDate("").withEndDate("13-05-2018 23:30")
                    .withRecurring("")
                    .withDescription("use the 7 C")
                    .withTags("school")
                    .withPriority('l')
                    .build();
            // Deadline with no time
            taskE = new TaskBuilder().withName("complete 2103 tutorial")
                    .withStartDate("").withEndDate("tomorrow")
                    .withRecurring("weekly")
                    .withDescription("bring laptop")
                    .withTags("school", "favourite")
                    .withPriority('l')
                    .build();
            // Deadline with many missing info
            taskF = new TaskBuilder().withName("finish assignments")
                    .withStartDate("").withEndDate("11pm")
                    .withRecurring("monthly")
                    .withDescription("")
                    .withPriority('l')
                    .build();
            // Deadline with daily recurring
            taskJ = new TaskBuilder().withName("Do pushup everyday")
                    .withStartDate("").withEndDate("today 1159")
                    .withRecurring("daily")
                    .withDescription("Do 20")
                    .withTags("favourite")
                    .withPriority('m')
                    .build();
            // To-do with all info
            taskG = new TaskBuilder().withName("buy new bag")
                    .withStartDate("").withEndDate("")
                    .withRecurring("")
                    .withDescription("find cheap ones")
                    .withTags("shopping", "favourite", "hobby")
                    .withPriority('l')
                    .build();
            // To-do with some missing info
            taskH = new TaskBuilder().withName("change shirt")
                    .withStartDate("").withEndDate("")
                    .withRecurring("")
                    .withDescription("")
                    .withTags("habit", "favourite", "hobby")
                    .withPriority('l')
                    .build();
            // To-do with many missing info
            taskI = new TaskBuilder().withName("change pants")
                    .withStartDate("").withEndDate("")
                    .withRecurring("")
                    .withDescription("")
                    .withPriority('l')
                    .build();


            // Manually added
            task1 = new TaskBuilder().withName("stay over boss house")
                    .withStartDate("tomorrow 11am").withEndDate("tomorrow 2359")
                    .withRecurring("weekly")
                    .withDescription("prepare to get scolded")
                    .withTags("work")
                    .withPriority('m')
                    .build();
            task2 = new TaskBuilder().withName("do boss evaluation")
                    .withStartDate("").withEndDate("16 july 2018 10:00")
                    .withRecurring("")
                    .withDescription("grade him 10/10")
                    .withTags("work", "school")
                    .withPriority('h')
                    .build();
            task3 = new TaskBuilder().withName("reply boss email")
                    .withStartDate("").withEndDate("")
                    .withRecurring("")
                    .withDescription("")
                    .withPriority('l')
                    .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadToDoListWithSampleData(ToDoList ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    /**
     * @return TestTask[] containing default tasks A to I
     */
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI, taskJ};
    }

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
