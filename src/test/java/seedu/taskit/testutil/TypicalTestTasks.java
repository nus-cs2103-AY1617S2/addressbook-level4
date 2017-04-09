// @@author A0163996J

package seedu.taskit.testutil;

import seedu.taskit.commons.exceptions.IllegalValueException;
import seedu.taskit.model.TaskManager;
import seedu.taskit.model.task.Task;
import seedu.taskit.model.task.UniqueTaskList;
import seedu.taskit.model.task.Date;

public class TypicalTestTasks {
    public TestTask hw1, hw2, lunch, interview, meeting, shopping, assignment, cleaning,
    gymming, golfing, today, to, from, date,deadline, internship;

    public TypicalTestTasks() {
        try {
            interview = new TaskBuilder().withTitle("Interview for big company")
                    .withPriority("high")
                    .withTags("career").build();
            hw1 = new TaskBuilder().withTitle("Do HW 1")
                    .withPriority("medium")
                    .withTags("school").build();
            hw2 = new TaskBuilder().withTitle("Do HW 2")
                    .withPriority("medium")
                    .withTags("school").build();
            lunch = new TaskBuilder().withTitle("Lunch with Bob")
                    .withPriority("low")
                    .withTags("leisure", "friends").build();
            shopping = new TaskBuilder().withTitle("Shopping with friends")
                    .withPriority("low")
                    .withTags("leisure", "friends").build();
            //@@author A0097141H
            cleaning = new TaskBuilder().withTitle("Clean room")
                    .withPriority("low")
                    .withTags("home").withDone("done").build();
            gymming = new TaskBuilder().withTitle("Go to the gym")
                    .withPriority("low")
                    .withTags("health").withDone("done").build();
            //@@author

            // Manually added
            meeting = new TaskBuilder().withTitle("Software Engineering Meeting")
                    .withPriority("low")
                    .withTags("school").build();
            assignment = new TaskBuilder().withTitle("CS3230 Assignment")
                    .withPriority("low")
                    .withTags("school").build();
            golfing = new TaskBuilder().withTitle("Golf with Cher")
                    .withPriority("medium")
                    .withTags("school").withEnd(new Date("today")).build();
            today = new TaskBuilder().withTitle("today movie night")
                    .withPriority("low")
                    .withTags("leisure").build();
            to = new TaskBuilder().withTitle("to")
                    .withPriority("low")
                    .withTags("testing").build();
            from = new TaskBuilder().withTitle("from")
                    .withPriority("low").withEnd("3pm")
                    .withTags("testing").build();
            date = new TaskBuilder().withTitle("date")
                    .withPriority("low").withStart("3pm")
                    .withEnd("6pm").build();
            deadline = new TaskBuilder().withTitle("project deadline")
                    .withPriority("low")
                    .withEnd("11pm").build();
            internship = new TaskBuilder().withTitle("internship")
                    .withPriority("low")
                    .withEnd("May 30").build();

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadAddressBookWithSampleData(TaskManager ab) {
        for (TestTask task : new TypicalTestTasks().getTypicalTasks()) {
            try {
                ab.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    //@@author A0097141H
    public TestTask[] getTypicalTasks() {
        return new TestTask[]{hw1, hw2, lunch, interview, shopping, cleaning, gymming};
    }
    //@@author

    //@@author A0141872E
    public TestTask[] getUndoneTypicalTasks(){
        return new TestTask[]{hw1, hw2, lunch, interview, shopping, cleaning, gymming};
    }

    public TestTask[] getFloatingTypicalTasks(){
        return new TestTask[]{hw1, hw2, lunch, interview, shopping, cleaning, gymming};
    }//@@author

    public TaskManager getTypicalAddressBook() {
        TaskManager ab = new TaskManager();
        loadAddressBookWithSampleData(ab);
        return ab;
    }
}
