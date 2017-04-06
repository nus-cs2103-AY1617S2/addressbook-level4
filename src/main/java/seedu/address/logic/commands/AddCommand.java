package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Date;
import seedu.address.model.task.DeadlineTask;
import seedu.address.model.task.Email;
import seedu.address.model.task.EndDate;
import seedu.address.model.task.FloatingTask;
import seedu.address.model.task.Group;
import seedu.address.model.task.Name;
import seedu.address.model.task.StartDate;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniquePersonList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list. "
            + "Parameters: NAME [s/START DATE] [d/DEADLINE] g/GROUP \n "
            + "Start date and deadline are not necessary. \n" + "Example: " + COMMAND_WORD
            + " study english s/01.01 d/03.21 g/learning";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values. This case is only have the
     * deadline
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    //@@author A0164032U
    public AddCommand(String name, String end, String group) throws IllegalValueException {
        this.toAdd = new DeadlineTask(new Name(name),
                new EndDate(end),
                null,
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }

    /*
     * Constructor: floating task without starting date and end date
     */
    //@@author A0164032U
    public AddCommand(String name, String group) throws IllegalValueException {
        this.toAdd = new FloatingTask(new Name(name),
                null,
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }
    
    //@@author A0164032U
    public AddCommand(String name, String start, String end, String group) throws IllegalValueException {
        this.toAdd = new Task(
                new Name(name),
                new StartDate(start),
                new EndDate(end),
                null,
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addPerson(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }

}
