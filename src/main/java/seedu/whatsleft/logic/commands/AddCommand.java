package seedu.whatsleft.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.whatsleft.commons.core.EventsCenter;
import seedu.whatsleft.commons.core.UnmodifiableObservableList;
import seedu.whatsleft.commons.events.ui.JumpToCalendarEventEvent;
import seedu.whatsleft.commons.events.ui.JumpToCalendarTaskEvent;
import seedu.whatsleft.commons.events.ui.JumpToEventListRequestEvent;
import seedu.whatsleft.commons.events.ui.JumpToTaskListRequestEvent;
import seedu.whatsleft.commons.exceptions.IllegalValueException;
import seedu.whatsleft.logic.commands.exceptions.CommandException;
import seedu.whatsleft.model.ModelManager;
import seedu.whatsleft.model.ReadOnlyWhatsLeft;
import seedu.whatsleft.model.activity.ByDate;
import seedu.whatsleft.model.activity.ByTime;
import seedu.whatsleft.model.activity.Description;
import seedu.whatsleft.model.activity.EndDate;
import seedu.whatsleft.model.activity.EndTime;
import seedu.whatsleft.model.activity.Event;
import seedu.whatsleft.model.activity.Location;
import seedu.whatsleft.model.activity.Priority;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.activity.ReadOnlyTask;
import seedu.whatsleft.model.activity.StartDate;
import seedu.whatsleft.model.activity.StartTime;
import seedu.whatsleft.model.activity.Task;
import seedu.whatsleft.model.activity.UniqueEventList;
import seedu.whatsleft.model.activity.UniqueTaskList;
import seedu.whatsleft.model.activity.UniqueEventList.DuplicateEventException;
import seedu.whatsleft.model.activity.UniqueTaskList.DuplicateTaskException;
import seedu.whatsleft.model.tag.Tag;
import seedu.whatsleft.model.tag.UniqueTagList;


/**
 * Adds an activity to WhatsLeft.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";


    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an activity to WhatsLeft. "
            + "Parameters: DESCRIPTION p/PRIORITY l/LOCATION sd/STARTDATE ed/ENDDATE st/STARTTIME"
            + " et/ENDTIME bd/BYDATE bt/BYTIME \n"
            + "Event must have sd/STARTDATE, Task/Deadline must have p/PRIORITY \n"
            + "Example: " + COMMAND_WORD
            + " Project Discussion p/high l/discussion room t/formal";

    public static final String MESSAGE_SUCCESS = "New activity added: %1$s";
    public static final String MESSAGE_SUCCESS_WITH_CLASH = "New activity added but with possible clash! : %1$s";
    public static final String MESSAGE_DUPLICATE_ACTIVITY = "This activity already exists in WhatsLeft";
    public static final String MESSAGE_CLASH_TIMING = "This event clashes with another event";
    public static final String MESSAGE_ILLEGAL_EVENT_END_DATETIME = "End Date/Time cannot be before Start Date/Time!";

    private final Event toAddEvent;
    private final Task toAddTask;
    //@@author A0110491U
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String description, String priority, String starttime, String startdate, String endtime,
            String enddate, String bydate, String bytime, String location, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        if (priority != null) {
            this.toAddTask = new Task(
                    new Description(description),
                    new Priority(priority),
                    new ByTime(bytime),
                    new ByDate(bydate),
                    new Location(location),
                    new UniqueTagList(tagSet),
                    Task.DEFAULT_TASK_STATUS);
            this.toAddEvent = null;
        } else {
            this.toAddEvent = new Event(
                    new Description(description),
                    new StartTime(starttime),
                    new StartDate(startdate),
                    new EndTime(endtime),
                    new EndDate(enddate),
                    new Location(location),
                    new UniqueTagList(tagSet));
            this.toAddTask = null;
            if (!Event.isValideEndDateTime(toAddEvent.getEndTime(), toAddEvent.getEndDate(),
                    toAddEvent.getStartTime(), toAddEvent.getStartDate())) {
                throw new IllegalValueException(MESSAGE_ILLEGAL_EVENT_END_DATETIME);
            }
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.storePreviousCommand("");
            ReadOnlyWhatsLeft currState = model.getWhatsLeft();
            ModelManager.setPreviousState(currState);
            if (toAddTask == null) {
                return addingEvent();
            } else if (toAddEvent == null) {
                return addingTask();
            }
        } catch (UniqueEventList.DuplicateEventException | UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_ACTIVITY);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddTask));

    }

    //@@author A0110491U
    /**
     * @return CommandResult of adding a Task
     * @throws DuplicateTaskException if duplicate task is found
     */
    private CommandResult addingTask() throws DuplicateTaskException {
        model.addTask(toAddTask);
        UnmodifiableObservableList<ReadOnlyTask> lastShownList = model.getFilteredTaskList();
        EventsCenter.getInstance().post(new JumpToTaskListRequestEvent(lastShownList.indexOf(toAddTask)));
        EventsCenter.getInstance().post(new JumpToCalendarTaskEvent(toAddTask));
        model.storePreviousCommand("add");
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddTask));
    }

    //@@author A0110491U
    /**
     * @return CommandResult of adding an Event
     * @throws DuplicateEventException if duplicate event is found
     */
    private CommandResult addingEvent() throws DuplicateEventException {
        model.addEvent(toAddEvent);
        UnmodifiableObservableList<ReadOnlyEvent> lastShownList = model.getFilteredEventList();
        EventsCenter.getInstance().post(new JumpToEventListRequestEvent(lastShownList.indexOf(toAddEvent)));
        if (!toAddEvent.isOver()) {
            EventsCenter.getInstance().post(new JumpToCalendarEventEvent(toAddEvent));
        }
        model.storePreviousCommand("add");
        if (model.eventHasClash(toAddEvent)) {
            return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_CLASH, toAddEvent));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAddEvent));
    }

}
