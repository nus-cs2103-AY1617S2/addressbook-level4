package todolist.testutil;

import todolist.commons.exceptions.IllegalValueException;
import todolist.model.ToDoList;
import todolist.model.task.Task;
import todolist.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc, tuitionPartTime,
        stringsRehearsal, dinnerAuntie, ma3269Quiz, laundry;

    public TypicalTestTasks() {
        try {
            cs2103Tutorial = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withStartTime("Tuesday 10:00").withEndTime("Tuesday 11:00")
                    .withTags("lesson").withUrgencyLevel("5").withDescription("I love you").build();
            dbsInterview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                    .withStartTime("March 31, 9:30").withEndTime("March 31, 11:30")
                    .withTags("interview", "internship", "important").withUrgencyLevel("5")
                    .withDescription("I love you").build();
            hangOutJoe = new TaskBuilder().withTitle("Hang out with Joe").withVenue("313 Somerset")
                    .withStartTime("Saturday 17:00").withEndTime("Saturday 21:00")
                    .withUrgencyLevel("4").withDescription("I love you").build();
            statsSoc = new TaskBuilder().withTitle("Statistics society meeting").withVenue("S16 04-30")
                    .withStartTime("Wednesday 19:00").withEndTime("Wednesday 21:00")
                    .withUrgencyLevel("4").withDescription("I love you").build();
            tuitionPartTime = new TaskBuilder().withTitle("Tuition part-time job")
                    .withVenue("Jun Wei's house at Jurong Ease Avenue 1")
                    .withStartTime("Next Thursday 19:00").withEndTime("Next Thursday 21:00")
                    .withUrgencyLevel("4").withDescription("I love you").build();
            stringsRehearsal = new TaskBuilder().withTitle("Strings ensemble rehearsal").withVenue("UCC hall")
                    .withStartTime("Friday 9:00").withEndTime("Friday 18:00")
                    .withUrgencyLevel("3").withDescription("I love you").build();
            dinnerAuntie = new TaskBuilder().withTitle("Dinner with auntie").withVenue("Home")
                    .withStartTime("Friday 19:00").withEndTime("Friday 20:00")
                    .withUrgencyLevel("2").withDescription("I love you").build();

            // Manually added
            ma3269Quiz = new TaskBuilder().withTitle("MA3269 Quiz").withVenue("LT26")
                    .withStartTime("Thursday 12:00").withEndTime("Thursday 14:00")
                    .withUrgencyLevel("1").withDescription("I love you").build();
            laundry = new TaskBuilder().withTitle("Do laundry").withVenue("Hostel")
                    .withStartTime("now").withEndTime("1 hour later chores")
                    .withUrgencyLevel("1").withDescription("I love you").build();
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
        return new TestTask[]{cs2103Tutorial, dbsInterview, hangOutJoe, statsSoc,
            tuitionPartTime, stringsRehearsal, dinnerAuntie};
    }

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
