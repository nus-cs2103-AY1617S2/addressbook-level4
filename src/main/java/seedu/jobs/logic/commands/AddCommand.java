package seedu.jobs.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.jobs.commons.exceptions.IllegalValueException;
import seedu.jobs.logic.commands.exceptions.CommandException;
import seedu.jobs.model.tag.Tag;
import seedu.jobs.model.tag.UniqueTagList;
import seedu.jobs.model.task.Description;
import seedu.jobs.model.task.Name;
import seedu.jobs.model.task.Period;
import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;
import seedu.jobs.model.task.UniqueTaskList;

/**
 * Adds a task to the JOBS.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to JOBS. "
            + "Parameters: add name/TASK_NAME [start/START_TIME end/END_TIME recur/PERIOD desc/DESCRIPTION tag/TAGS] \n"
            + "Example: " + COMMAND_WORD
            + " name/tutorial start/13/01/17 11:00 end/13/01/17 12:00 recur/7 desc/\"Tutorial of CS2103\" tag/CS2103";

    public static final String MESSAGE_SUCCESS = "New task added: \n %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in JOBS";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(Optional<String> name, Optional<String> starttime, Optional<String> endtime,
            Optional<String> description, Set<String> tags, Optional<String>period)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Time(starttime),
                new Time(endtime),
                new Description(description),
                new UniqueTagList(tagSet), new Period(period));
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
