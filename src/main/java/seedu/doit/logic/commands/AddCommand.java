package seedu.doit.logic.commands;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import seedu.doit.commons.core.EventsCenter;
import seedu.doit.commons.events.ui.JumpToListRequestEvent;
import seedu.doit.commons.exceptions.IllegalValueException;
import seedu.doit.logic.commands.exceptions.CommandException;
import seedu.doit.model.item.Description;
import seedu.doit.model.item.EndTime;
import seedu.doit.model.item.Name;
import seedu.doit.model.item.Priority;
import seedu.doit.model.item.StartTime;
import seedu.doit.model.item.Task;
import seedu.doit.model.tag.Tag;
import seedu.doit.model.tag.UniqueTagList;

//@@author A0146809W
/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Task manager. "
        + "Parameters: TASK NAME p/PRIORITY  e/END DATE  d/ADDITIONAL DESCRIPTION [t/TAG]...\n" + "Example: "
        + COMMAND_WORD + " CS3230 Assignment1 p/high e/tomorrow 23:59 d/Prove bubble sort A t/CS3230\n"
        + "add Hackathon p/med s/next monday 9am e/next tuesday d/create chatbot  t/hackathon\n"
        + "add Food p/low d/for myself t/secret";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the Task Manager";
    public static final String MESSAGE_INVALID_START_TIME = "Start Time not earlier then End Time!";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values for task.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String dueDate, String text, Set<String> tags)
        throws IllegalValueException {

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new Priority(priority), new EndTime(dueDate), new Description(text),
            new UniqueTagList(tagSet));
    }

    /**
     * Creates an AddCommand using raw values for event.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String startDate, String dueDate, String text, Set<String> tags)
        throws IllegalValueException {

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        StartTime startTime;
        EndTime endTime;
        this.toAdd = new Task(new Name(name), new Priority(priority), startTime = new StartTime(startDate),
            endTime = new EndTime(dueDate), new Description(text), new UniqueTagList(tagSet));

        if (!isStartTimeEarlier(startTime.getDateTimeObject(), endTime.getDateTimeObject())) {
            throw new IllegalValueException(MESSAGE_INVALID_START_TIME);
        }
    }

    /**
     * Creates an AddCommand using raw values for floating task.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, String text, Set<String> tags) throws IllegalValueException {

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(new Name(name), new Priority(priority), new Description(text), new UniqueTagList(tagSet));
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert this.model != null;
        try {
            this.model.addTask(this.toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(
                    this.model.getFilteredTaskList().indexOf(this.toAdd)));
            return new CommandResult(String.format(MESSAGE_SUCCESS, this.toAdd));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }
    }

    /**
     * Checks if the Start Time is Earlier or equal to End Time.
     *
     * @param startTime Start Time of Event
     * @param endTime   End Time of Event
     * @return boolean return is start time earlier than end time.
     */
    private boolean isStartTimeEarlier(LocalDateTime startTime, LocalDateTime endTime) {
        return startTime.isBefore(endTime);
    }

}
