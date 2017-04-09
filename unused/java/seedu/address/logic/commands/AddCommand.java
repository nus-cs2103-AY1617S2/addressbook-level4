package seedu.task.logic.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.StringUtil;
import seedu.task.logic.commands.exceptions.CommandException;
import seedu.task.model.tag.Tag;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.EndDate;
import seedu.task.model.task.Group;
import seedu.task.model.task.Name;
import seedu.task.model.task.StartDate;
import seedu.task.model.task.Task;
import seedu.task.model.task.UniquePersonList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the todo list. "
            + "Parameters: NAME [s/START DATE] [d/DEADLINE] g/GROUP \n "
            + "Start date and deadline are not necessary. \n"
            + "Cannot have a start date without an end date. \n"
            + "Example: " + COMMAND_WORD
            + " study english s/today d/tomorrow g/learning";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_PASSED = "The end date of the new task has passed!";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the todo list";

    private Task toAdd;

    /**
     * Creates an AddCommand using raw values. This case is only have the
     * deadline
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    //@@author A0164032U
    public AddCommand(String name, String end, String group) throws IllegalValueException {
        this.toAdd = new DeadlineTask(
                new Name(name),
                new EndDate(end),
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }

    /*
     * Constructor: floating task without starting date and end date
     */
    //@@author A0164032U
    public AddCommand(String name, String group) throws IllegalValueException {
        this.toAdd = new FloatingTask(
                new Name(name),
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
                new Group(group),
                UniqueTagList.build(Tag.TAG_INCOMPLETE)
                );
    }
    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addPerson(toAdd);

            String message = MESSAGE_SUCCESS + (toAdd.hasPassed() ? "\n" + MESSAGE_PASSED : "");
            return new CommandResult(String.format(message, toAdd));
        } catch (UniquePersonList.DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

    }
}
