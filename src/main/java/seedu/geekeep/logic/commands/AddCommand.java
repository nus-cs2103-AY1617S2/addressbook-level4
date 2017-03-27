package seedu.geekeep.logic.commands;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.geekeep.commons.exceptions.IllegalValueException;
import seedu.geekeep.logic.commands.exceptions.CommandException;
import seedu.geekeep.model.tag.Tag;
import seedu.geekeep.model.tag.UniqueTagList;
import seedu.geekeep.model.task.DateTime;
import seedu.geekeep.model.task.Location;
import seedu.geekeep.model.task.Task;
import seedu.geekeep.model.task.Title;
import seedu.geekeep.model.task.UniqueTaskList;

/**
 * Adds a task to the Task Manager.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the Task Manager. "
            + "Parameters: TITLE s/STARTING_DATETIME e/ENDING_DATETIME l/LOCATION [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " Meeting 1 s/01-04-17 1630 e/01-04-17 1730 l/311, Clementi Ave 2, #02-25 t/friends";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in GeeKeep";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, Optional<String> startDateTime, Optional<String> endDateTime,
            Optional<String> location, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        DateTime start = null;
        DateTime end = null;
        Location loc = null;

        if (startDateTime.isPresent()) {
            start = new DateTime(startDateTime.get());
        }
        if (endDateTime.isPresent()) {
            end = new DateTime(endDateTime.get());
        }
        if (location.isPresent()) {
            loc = new Location(location.get());
        }

        this.toAdd = new Task(
                new Title(title),
                start,
                end,
                loc,
                new UniqueTagList(tagSet)
        );
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
