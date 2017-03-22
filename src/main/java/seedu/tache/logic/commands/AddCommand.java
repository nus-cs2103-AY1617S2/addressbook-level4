package seedu.tache.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.logic.commands.exceptions.CommandException;
import seedu.tache.model.tag.Tag;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;
import seedu.tache.model.task.Task.RecurInterval;
import seedu.tache.model.task.UniqueTaskList;

/**
 * Adds a task to the task manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task manager. "
            + "Parameters: NAME [;START DATE & TIME] [;END DATE & TIME] [;TAG...]\n"
            + "Example: " + COMMAND_WORD
            + " Orientation week camp; 25/7/16 0800; 28/7/2016 0900; HighPriority; Events";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw name and tags value.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new UniqueTagList(tagSet)
        );
    }

    /**
     * Creates an AddCommand using raw name, start date & time, end date & time, and tags values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String nameStr, String startDateStr, String startTimeStr, Optional<String> endDateStr,
            Optional<String> endTimeStr, Set<String> tagsStr) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tagsStr) {
            tagSet.add(new Tag(tagName));
        }
        Name name = new Name(nameStr);
        Optional<DateTime> startDateTime = Optional.of(new DateTime(startDateStr + startTimeStr));
        Optional<DateTime> endDateTime = Optional.empty();
        String endDate = "";
        String endTime = "";
        if (endDateStr.isPresent()) {
            endDate = endDateStr.get();
        }
        if (endTimeStr.isPresent()) {
            endTime = endTimeStr.get();
        }
        if (!(endDate.equals("") && endTime.equals(""))) {
            endDateTime = Optional.of(new DateTime(endDate + endTime));
        }
        UniqueTagList tagList = new UniqueTagList(tagSet);
        this.toAdd = new Task(name, startDateTime, endDateTime, tagList, true, true, false, RecurInterval.NONE);
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }
}

