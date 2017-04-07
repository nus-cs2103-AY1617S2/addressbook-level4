package seedu.task.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.task.commons.core.EventsCenter;
import seedu.task.commons.events.ui.JumpToListRequestEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.Date;
import seedu.task.model.task.Location;
import seedu.task.model.task.Name;
import seedu.task.model.task.Remark;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniqueTaskList;

/**
 * Adds a task to KIT.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD_1 = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Adds a task to the task manager. "
            + "Parameters: NAME s/START e/END r/REMARK l/LOCATION  [t/TAG]...\n"
            + "Example: " + COMMAND_WORD_1
            + " John owes money s/02-03-2017 e/03-03-2017 r/john owes me $100 l/john's house t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the task manager";

    private final Task toAdd;

    // @@author A0140063X
    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String name, String startDate, String endDate, String remark, String location, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new Task(
                new Name(name),
                new Date(startDate),
                new Date(endDate),
                new Remark(remark),
                new Location(location), new UniqueTagList(tagSet),
                false, "");
    }

    // @@author
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(0));
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
