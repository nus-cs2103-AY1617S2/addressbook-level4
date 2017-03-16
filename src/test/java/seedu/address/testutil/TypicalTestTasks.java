package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ToDoList;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 *
 */
public class TypicalTestTasks {

    public TestTask CS2103_Tutorial, DBS_Interview, Hang_out_Joe, Stats_soc, Tuition_part_time, Strings_rehearsal, dinner_auntie, MA3269_Quiz, laundry;

    public TypicalTestTasks() {
        try {
            CS2103_Tutorial = new TaskBuilder().withTitle("CS2103 Tutorial").withVenue("COM1-B103")
                    .withStartTime("Tuesday 10:00").withEndTime("Tuesday 11:00")
                    .withTags("lesson").withUrgencyLevel("5").withDescription("I love you").build();
            DBS_Interview = new TaskBuilder().withTitle("DBS Internship interview").withVenue("Raffles Place")
                    .withStartTime("March 31, 9:30").withEndTime("March 31, 11:30")
                    .withTags("interview", "internship","important").withUrgencyLevel("4").withDescription("I love you").build();
            Hang_out_Joe = new TaskBuilder().withTitle("Hang out with Joe").withVenue("313 Somerset")
                    .withStartTime("Saturday 17:00").withEndTime("Saturday 21:00").withUrgencyLevel("3").withDescription("I love you").build();
            Stats_soc = new TaskBuilder().withTitle("Statistics society meeting").withVenue("S16 04-30")
                    .withStartTime("Wednesday 19:00").withEndTime("Wednesday 21:00").withUrgencyLevel("1").withDescription("I love you").build();
            Tuition_part_time = new TaskBuilder().withTitle("Tuition part-time job").withVenue("Jun Wei's house at Jurong Ease Avenue 1")
                    .withStartTime("Next Thursday 19:00").withEndTime("Next Thursday 21:00").withUrgencyLevel("5").withDescription("I love you").build();
            Strings_rehearsal = new TaskBuilder().withTitle("Strings ensemble rehearsal").withVenue("UCC hall")
                    .withStartTime("Friday 9:00").withEndTime("Friday 18:00").withUrgencyLevel("2").withDescription("I love you").build();
            dinner_auntie = new TaskBuilder().withTitle("Dinner with auntie").withVenue("Home")
                    .withStartTime("Friday 19:00").withEndTime("Friday 20:00").withUrgencyLevel("4").withDescription("I love you").build();

            // Manually added
            MA3269_Quiz = new TaskBuilder().withTitle("MA3269 Quiz").withVenue("LT26")
                    .withStartTime("Thursday 12:00").withEndTime("Thursday 14:00").withUrgencyLevel("4").withDescription("I love you").build();
            laundry = new TaskBuilder().withTitle("Do laundry").withVenue("Hostel")
                    .withStartTime("now").withEndTime("1 hour later chores").withUrgencyLevel("2").withDescription("I love you").build();
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
        return new TestTask[]{CS2103_Tutorial, DBS_Interview, Hang_out_Joe, Stats_soc, Tuition_part_time, Strings_rehearsal, dinner_auntie};
    }

    public ToDoList getTypicalToDoList() {
        ToDoList ab = new ToDoList();
        loadToDoListWithSampleData(ab);
        return ab;
    }
}
