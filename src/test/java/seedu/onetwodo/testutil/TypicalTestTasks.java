package seedu.onetwodo.testutil;

import seedu.onetwodo.commons.exceptions.IllegalValueException;
import seedu.onetwodo.model.ToDoList;
import seedu.onetwodo.model.task.Task;
import seedu.onetwodo.model.task.UniqueTaskList;

/**
 *  Tasks to be used for testing. Includes default tasks and tasks to be added.
 */
public class TypicalTestTasks {

    public TestTask taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI,
            task1, task2, task3;

    public TypicalTestTasks() {
        try {
            // Event with all info
            taskA = new TaskBuilder().withName("guard duty")
                    .withStartDate("15/12/2018 7am").withEndDate("16/12/2018 11pm")
                    .withDescription("bring weapon")
                    .withTags("army", "work")
                    .build();
            // Event with some missing info
            taskB = new TaskBuilder().withName("study at home")
                    .withStartDate("10 Mar 2018").withEndDate("13 mar 2018")
                    .withDescription("")
                    .withTags("work", "school")
                    .build();
            // Event with many missing info
            taskC = new TaskBuilder().withName("meet boss")
                    .withStartDate("10 Mar 2018 08:00").withEndDate("10 mar 2018 12:00")
                    .withDescription("")
                    .build();
            // Deadline with all date info
            taskD = new TaskBuilder().withName("submit cs2101 reflection")
                    .withStartDate("").withEndDate("13-05-2018 23:30")
                    .withDescription("use the 7 C")
                    .withTags("school")
                    .build();
            // Deadline with no time
            taskE = new TaskBuilder().withName("complete 2103 tutorial")
                    .withStartDate("").withEndDate("tomorrow")
                    .withDescription("bring laptop")
                    .withTags("school", "favourite")
                    .build();
            // Deadline with many missing info
            taskF = new TaskBuilder().withName("finish assignments")
                    .withStartDate("").withEndDate("11pm")
                    .withDescription("")
                    .build();
            // To-do with all info
            taskG = new TaskBuilder().withName("buy new bag")
                    .withStartDate("").withEndDate("")
                    .withDescription("find cheap ones")
                    .withTags("shopping", "favourite", "hobby")
                    .build();
            // To-do with some missing info
            taskH = new TaskBuilder().withName("change shirt")
                    .withStartDate("").withEndDate("")
                    .withDescription("")
                    .withTags("habit", "favourite", "hobby")
                    .build();
            // To-do with many missing info
            taskI = new TaskBuilder().withName("change pants")
                    .withStartDate("").withEndDate("")
                    .withDescription("")
                    .build();

            
            // Manually added
            task1 = new TaskBuilder().withName("stay over boss house")
                    .withStartDate("tomorrow 11am").withEndDate("tomorrow 2359")
                    .withDescription("prepare to get scolded")
                    .withTags("work")
                    .build();
            task2 = new TaskBuilder().withName("do peer evaluation")
                    .withStartDate("").withEndDate("16 july 2018 10:00")
                    .withDescription("grade everyone 10/10")
                    .withTags("school")
                    .build();
            task3 = new TaskBuilder().withName("reply boss email")
                    .withStartDate("").withEndDate("")
                    .withDescription("")
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

    public TestTask[] getTypicalTasks() {
        return new TestTask[]{taskA, taskB, taskC, taskD, taskE, taskF, taskG, taskH, taskI};
    }

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
