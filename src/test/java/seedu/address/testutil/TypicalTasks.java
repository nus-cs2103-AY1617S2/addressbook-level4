package seedu.address.testutil;

import java.util.Date;
import java.util.Optional;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.TaskManager;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.DateTime;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

// @author A0093999Y
/**
 * All variations of tasks for our task manager
 */
public class TypicalTasks {

    // For Today Task List
    public Task todayListOverdue, todayListFloat, todayListDeadline, todayListEvent, todayListToday;

    // For Future Task List
    public Task futureListFloat, futureListDeadline, futureListEvent;

    // For Completed Task List
    public Task completedListFloat, completedListDeadline, completedListEvent;

    // UniqueTagLists
    public UniqueTagList noTags = new UniqueTagList(), familyTags = new UniqueTagList(), workTags = new UniqueTagList(),
            workAndFamilyTags = new UniqueTagList();

    // DateTime
    public Optional<DateTime> noDateTime, pastDateTime, todayDateTime, earlierFutureDateTime, laterFutureDateTime;

    // Today and Done booleans
    public final boolean doneTrue = true, todayTrue = true;
    public final boolean doneFalse = false, todayFalse = false;

    public TypicalTasks() {
        try {
            // Initialize UniqueTagLists
            familyTags.add(new Tag("spouse"));
            familyTags.add(new Tag("children"));
            workTags.add(new Tag("project"));
            workAndFamilyTags.add(new Tag("spouse"));
            workAndFamilyTags.add(new Tag("children"));
            workAndFamilyTags.add(new Tag("project"));

            // Initialize DateTime
            noDateTime = Optional.empty();
            pastDateTime = Optional.of(new DateTime(new Date(Long.MIN_VALUE)));
            todayDateTime = Optional.of(new DateTime(new Date()));
            earlierFutureDateTime = Optional.of(new DateTime(new Date(Long.MAX_VALUE - 1000)));
            laterFutureDateTime = Optional.of(new DateTime(new Date(Long.MAX_VALUE)));

            // Initialize Tasks
            todayListOverdue = Task.createTask(new Name("Do Math Assignment"), noTags, pastDateTime, noDateTime,
                    doneFalse, todayFalse);
            todayListFloat = Task.createTask(new Name("Get Business Proposal"), workTags, noDateTime, noDateTime,
                    doneFalse, todayTrue);
            todayListDeadline = Task.createTask(new Name("Buy Meier stove"), familyTags, todayDateTime, noDateTime,
                    doneFalse, todayFalse);
            todayListEvent = Task.createTask(new Name("Write english essay"), workTags, pastDateTime,
                    laterFutureDateTime, doneFalse, todayFalse);

            futureListFloat = Task.createTask(new Name("Buy Meier rice cooker"), familyTags, noDateTime, noDateTime,
                    doneFalse, todayFalse);
            futureListDeadline = Task.createTask(new Name("Complete CS2106 Lab Assignment"), workAndFamilyTags,
                    laterFutureDateTime, noDateTime, doneFalse, todayFalse);
            futureListEvent = Task.createTask(new Name("Complete CS2106 Lab Assignment"), workAndFamilyTags,
                    earlierFutureDateTime, laterFutureDateTime, doneFalse, todayFalse);

            completedListFloat = Task.createTask(new Name("Mark CS1010S"), workTags, noDateTime, noDateTime, doneTrue,
                    todayTrue);
            completedListDeadline = Task.createTask(new Name("Go for a night run"), noTags, todayDateTime, noDateTime,
                    doneTrue, todayFalse);
            completedListEvent = Task.createTask(new Name("Go for Rock concert"), noTags, todayDateTime, pastDateTime,
                    doneTrue, todayFalse);
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadTaskManagerWithSampleData(TaskManager ab) {
        for (Task task : new TypicalTasks().getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public Task[] getTypicalTasks() {
        return new Task[] { todayListOverdue, todayListFloat, todayListDeadline, todayListEvent, todayListToday,
                futureListFloat, futureListDeadline, futureListEvent, completedListFloat, completedListDeadline,
                completedListEvent };
    }

    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
