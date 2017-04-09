package seedu.today.testutil;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.ocpsoft.prettytime.shade.org.apache.commons.lang.time.DateUtils;

import seedu.today.commons.exceptions.IllegalValueException;
import seedu.today.model.TaskManager;
import seedu.today.model.tag.Tag;
import seedu.today.model.tag.UniqueTagList;
import seedu.today.model.task.DateTime;
import seedu.today.model.task.Name;
import seedu.today.model.task.Task;
import seedu.today.model.task.UniqueTaskList;

/**
 * All variations of tasks for our task manager. Use this to build all task test
 * cases.
 */
public class TypicalTasks {
    // @@author A0093999Y
    // For Today Task List
    public Task todayListOverdue, todayListFloat, todayListDeadline, todayListEvent, todayListToday;

    // For Future Task List
    public Task futureListFloat, futureListDeadline, futureListEvent;

    // For Completed Task List
    public Task completedListFloat, completedListDeadline, completedListEvent;

    // Extra Tasks
    public Task extraFloat, extraDeadline;

    // UniqueTagLists
    public UniqueTagList noTags = new UniqueTagList(), familyTags = new UniqueTagList(), workTags = new UniqueTagList(),
            alternateWorkTags = new UniqueTagList();

    // DateTime
    public Optional<DateTime> noDateTime, pastDateTime, todayDateTime, earlierFutureDateTime, laterFutureDateTime;

    // Today and Done booleans
    public final boolean doneTrue = true, todayTrue = true;
    public final boolean doneFalse = false, todayFalse = false;

    public TypicalTasks() {
        try {
            // Initialize UniqueTagLists
            familyTags.add(new Tag("children"));
            familyTags.add(new Tag("pproject"));
            workTags.add(new Tag("project"));
            alternateWorkTags.add(new Tag("lproject"));
            alternateWorkTags.add(new Tag("PROject"));

            // Initialize DateTime, truncate to remove milliseconds
            noDateTime = Optional.empty();
            pastDateTime = Optional
                    .of(new DateTime(DateUtils.truncate(DateUtils.addDays(new Date(), -4), Calendar.MINUTE)));
            todayDateTime = Optional
                    .of(new DateTime(DateUtils.truncate(DateUtils.addMinutes(new Date(), 2), Calendar.MINUTE)));
            earlierFutureDateTime = Optional
                    .of(new DateTime(DateUtils.truncate(DateUtils.addDays(new Date(), 2), Calendar.MINUTE)));
            laterFutureDateTime = Optional
                    .of(new DateTime(DateUtils.truncate(DateUtils.addDays(new Date(), 4), Calendar.MINUTE)));

            // Initialize Tasks
            todayListOverdue = Task.createTask(new Name("Do Math Assignment"), noTags, pastDateTime, noDateTime,
                    doneFalse, todayFalse);
            todayListFloat = Task.createTask(new Name("Get Business Proposal"), workTags, noDateTime, noDateTime,
                    doneFalse, todayTrue);
            todayListDeadline = Task.createTask(new Name("Buy Meier stove"), familyTags, todayDateTime, noDateTime,
                    doneFalse, todayFalse);
            todayListEvent = Task.createTask(new Name("Write english essay"), noTags, earlierFutureDateTime,
                    pastDateTime, doneFalse, todayFalse);
            todayListToday = Task.createTask(new Name("Do CS2222 work"), noTags, laterFutureDateTime, noDateTime,
                    doneFalse, todayTrue);

            futureListFloat = Task.createTask(new Name("Buy Meier rice cooker"), noTags, noDateTime, noDateTime,
                    doneFalse, todayFalse);
            futureListDeadline = Task.createTask(new Name("Complete CS2106 Lab Assignment"), alternateWorkTags,
                    laterFutureDateTime, noDateTime, doneFalse, todayFalse);
            futureListEvent = Task.createTask(new Name("Finish up Philo Assignment"), alternateWorkTags,
                    laterFutureDateTime, earlierFutureDateTime, doneFalse, todayFalse);

            completedListFloat = Task.createTask(new Name("Mark CS1010S assIgnment"), workTags, noDateTime, noDateTime,
                    doneTrue, todayTrue);
            completedListDeadline = Task.createTask(new Name("Goes for a night run"), noTags, todayDateTime, noDateTime,
                    doneTrue, todayFalse);
            completedListEvent = Task.createTask(new Name("Go for Rock concert"), noTags, todayDateTime, pastDateTime,
                    doneTrue, todayFalse);

            extraFloat = Task.createTask(new Name("Golf game"), workTags, noDateTime, noDateTime, doneFalse,
                    todayFalse);
            extraDeadline = Task.createTask(new Name("Go for a night jog"), noTags, todayDateTime, noDateTime,
                    doneFalse, todayFalse);

        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    // @@author
    public void loadTaskManagerWithSampleData(TaskManager ab) {
        for (Task task : getTypicalTasks()) {
            try {
                ab.addTask(task);
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    // @@author A0093999Y
    public Task[] getTypicalTasks() {
        return new Task[] { todayListOverdue, todayListFloat, todayListDeadline, todayListEvent, todayListToday,
            futureListFloat, futureListDeadline, futureListEvent, completedListFloat, completedListDeadline,
            completedListEvent };
    }

    public Task[] getTodayListTasks() {
        return new Task[] { todayListOverdue, todayListFloat, todayListDeadline, todayListEvent, todayListToday };
    }

    public Task[] getFutureListTasks() {
        return new Task[] { futureListFloat, futureListDeadline, futureListEvent };
    }

    public Task[] getCompletedListTasks() {
        return new Task[] { completedListFloat, completedListDeadline, completedListEvent };
    }

    // @@author
    public TaskManager getTypicalTaskManager() {
        TaskManager ab = new TaskManager();
        loadTaskManagerWithSampleData(ab);
        return ab;
    }
}
