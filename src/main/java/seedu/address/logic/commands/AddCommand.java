package seedu.address.logic.commands;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.todo.Name;
import seedu.address.model.todo.Todo;
import seedu.address.model.todo.UniqueTodoList;


/**
 * Adds a todo to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a todo to the todo list. "
            + "Parameters: TODO [t/TAG] \n"
            + "OR: TODO s/STARTTIME e/ENDTIME [t/TAG] \n"
            + "Example: " + COMMAND_WORD
            + " Take dog for walk s/11-11-17T5:00 e/11-11-17T6:00 t/todoal";

    public static final String MESSAGE_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_TODO = "This todo already exists in the todo list";

    private final Todo toAdd;

    /**
     * Creates an AddCommand using raw values.
     * * Only adds floating task for now
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws ParseException
     */
    public AddCommand(String todo,
                      String startTime,
                      String endTime,
                      Set<String> tags)
            throws IllegalValueException, ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        //String inputString = "11-11-2012";
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd'T'HH:mm");

        this.toAdd = new Todo(
                new Name(todo),
                dateFormat.parse(startTime),
                dateFormat.parse(endTime),
                new UniqueTagList(tagSet));
    }

    /**
     * Creates an AddCommand using raw values.
     * * Only adds floating task for now
     *
     * @throws IllegalValueException if any of the raw values are invalid
     * @throws ParseException
     */
    public AddCommand(String todo,
                      String endTime,
                      Set<String> tags)
            throws IllegalValueException, ParseException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }

        //String inputString = "11-11-2012";
        DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd'T'HH:mm");

        this.toAdd = new Todo(
                new Name(todo),
                dateFormat.parse(endTime),
                new UniqueTagList(tagSet));
    }

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
