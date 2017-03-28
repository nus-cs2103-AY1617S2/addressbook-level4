package seedu.todolist.logic.commands;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.exceptions.CommandException;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;
import seedu.todolist.model.todo.Name;
import seedu.todolist.model.todo.Todo;
import seedu.todolist.model.todo.UniqueTodoList;

/**
 * Adds a todo to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a todo to the todo list. "
            + "Parameters: TODO [t/TAG] \n"
            + "OR: TODO s/STARTTIME e/ENDTIME [t/TAG] \n"
            + "Example: " + COMMAND_WORD
            + " Take dog for walk s/1:00PM 11/11/17 e/2:00PM 11/11/17 t/todoal";

    public static final String MESSAGE_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists in the todo list";
    public static final String MESSAGE_INVALID_STARTTIME = "Invalid start time entered";
    public static final String MESSAGE_INVALID_ENDTIME = "Invalid end time entered";

    private final Todo toAdd;

    //@@author A0163720M
    /**
     * Creates an AddCommand using raw values to create a todo with start time and end time (event)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo,
                      String startTime,
                      String endTime,
                      Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Todo(
                new Name(todo),
                StringUtil.parseDate(startTime, DATE_FORMAT),
                StringUtil.parseDate(endTime, DATE_FORMAT),
                new UniqueTagList(tagSet));
    }
    //@@author

    //@@author A0163720M
    /**
     * Creates an AddCommand using raw values to create a todo with just the end time (deadline)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo,
                      String endTime,
                      Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Todo(
                new Name(todo),
                StringUtil.parseDate(endTime, DATE_FORMAT),
                new UniqueTagList(tagSet));
    }
    //@@author

    /**
     * Creates an AddCommand using raw values to create a floating task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        this.toAdd = new Todo(
                new Name(todo),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTodo(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTodoList.DuplicateTodoException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

    }
}
