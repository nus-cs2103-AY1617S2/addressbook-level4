package seedu.address.logic.commands;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartEndDateTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;

/**
 * Adds a task to the task list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the task list. "
            + "Parameters: NAME  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " exam from 10 Jan 8pm to 10 Jan 10pm t/CS2010";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task list";

    private final Task toAdd;

    //@@author A0140023E
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, Optional<String> deadlineDateTimeArgs, Optional<String> startDateTimeArgs,
                      Optional<String> endDateTimeArgs, Set<String> tags) throws IllegalValueException {
        // TODO the init method names may be improved
        final Optional<Deadline> deadline = initDeadline(deadlineDateTimeArgs);
        final Optional<StartEndDateTime> startEndDateTime =
                initStartEndDateTime(startDateTimeArgs, endDateTimeArgs);
        final UniqueTagList tagList = initTagList(tags);

        // maybe to rearrange the tag to be before
        this.toAdd = new Task(
                new Name(name),
                deadline,
                startEndDateTime,
                tagList
        );
    }

    /**
     * Returns an Optional of {@link Deadline} if deadline arguments are present, otherwise return an empty Optional.
     * @throws IllegalValueException if the deadline string cannot be parsed as date
     */
    private Optional<Deadline> initDeadline(Optional<String> deadlineDateTimeArgs)
            throws IllegalValueException {
        if (deadlineDateTimeArgs.isPresent()) {
            ZonedDateTime deadlineDateTime = ParserUtil.parseDateTimeString(deadlineDateTimeArgs.get());
            return Optional.of(new Deadline(deadlineDateTime));
        }
        return Optional.empty();
    }

    /**
     * Returns an Optional of {@link StartEndDateTime} if Start and End Date time arguments are
     * present, otherwise return an empty Optional.
     * @throws IllegalValueException if a StartEndDateTime cannot be constructed
     */
    private Optional<StartEndDateTime> initStartEndDateTime(Optional<String> startDateTimeArgs,
            Optional<String> endDateTimeArgs) throws IllegalValueException {
        if (startDateTimeArgs.isPresent()) {
            if (!endDateTimeArgs.isPresent()) {
                throw new IllegalValueException("End date must exist if there is a start date");
                // TODO currently not worth the effort but can consider allowing endDateTime to be
                // optional in the future
            }

            ZonedDateTime startDateTime = ParserUtil.parseDateTimeString(startDateTimeArgs.get());
            ZonedDateTime endDateTime = ParserUtil.parseDateTimeString(endDateTimeArgs.get());
            return Optional.of(new StartEndDateTime(startDateTime, endDateTime));
        }
        return Optional.empty();
    }

    /**
     * Returns initialized tags as a {@link UniqueTagList}
     * @throws IllegalValueException if there is a tag name that is invalid in the given tags set
     */
    private UniqueTagList initTagList(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return new UniqueTagList(tagSet);
    }

    //@@author
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
