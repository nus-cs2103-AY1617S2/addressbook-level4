package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Description;
import seedu.address.model.task.EndTime;
import seedu.address.model.task.StartTime;
import seedu.address.model.task.Task;
import seedu.address.model.task.Title;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.UrgencyLevel;
import seedu.address.model.task.Venue;

/**
 * Adds a Task to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a Task to the to-do list. "
            + "Parameters: TITLE @@VENUE from:STARTTIME to:ENDTIME ul/URGENCYLEVEL d/DESCRIPTION  ##[TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " CS2103 Tutorial @@COM1-B110 from:March 8,10.00am to:March 8, 11.00am ##lesson ##project";

    public static final String MESSAGE_SUCCESS = "New Task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This Task already exists in the address book";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String title, String venue, String starttime, String endtime, String urgencyLevel, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Title(title),
                new Venue(venue),
                new StartTime(starttime),
                new EndTime(endtime),
                new UrgencyLevel(urgencyLevel),
                new Description(description),
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
