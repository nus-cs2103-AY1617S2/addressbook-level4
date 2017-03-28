package seedu.todolist.logic.commands;

import static seedu.todolist.commons.core.GlobalConstants.DATE_FORMAT;

import java.util.Date;
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
    public AddCommand(String todo, String startTime, String endTime, Set<String> tags) throws IllegalValueException {
        try {
            // Parse through the set of tags
            final Set<Tag> tagSet = new HashSet<>();
            for (String tagName : tags) {
                tagSet.add(new Tag(tagName));
            }

            // Check for existence of each of the fields
            Name name = (todo != null) ? new Name(todo) : null;
            Date start = (startTime != null) ? StringUtil.parseDate(startTime, DATE_FORMAT) : null;
            Date end = (endTime != null) ? StringUtil.parseDate(endTime, DATE_FORMAT) : null;
            UniqueTagList tagList = new UniqueTagList(tagSet);

            // Todo(name, start_time, end_time, complete_time, taglist)
            this.toAdd = new Todo(name, start, end, null, tagList);
        } catch (IllegalValueException e) {
            throw e;
        }
    }
    //@@author

    //@@author A0163720M
    /**
     * Creates an AddCommand using raw values to create a todo with just the end time (deadline)
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, String endTime, Set<String> tags) throws IllegalValueException {
        // Cannot throw an exception since there's only one line in the constructor
        // and the first line must be the call to the constructor, not try{}
        this(todo, null, endTime, tags);
    }
    //@@author

    //@@author A0163720M
    /**
     * Creates an AddCommand using raw values to create a floating task
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, Set<String> tags) throws IllegalValueException {
        // Cannot throw an exception since there's only one line in the constructor and the first line cannot be try{}
        this(todo, null, null, tags);
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
