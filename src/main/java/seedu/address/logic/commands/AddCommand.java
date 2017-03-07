package seedu.address.logic.commands;

import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Adds a todo to the todo list.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a todo to the todo list. "
            + "Parameters: TODO [t/TAG] \n"
            + "OR: TODO d/DEADLINE [t/TAG] \n"
            + "OR: TODO s/STARTTIME e/ENDTIME [t/TAG] \n"
            + "Example: " + COMMAND_WORD
            + " Take dog for walk s/11-11-17/5:00pm e/11-11-17/6:00pm t/personal";

    public static final String MESSAGE_SUCCESS = "New todo added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This todo already exists in the todo list";

    private final ToDo toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public AddCommand(String todo, String starttime, String endtime, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toAdd = new ToDo(
                new Name(name
                new StartTime(starttime),
                new EndTime(endtime),
                new UniqueTagList(tagSet)
        );
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addToDo(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueToDoList.DuplicateToDoException e) {
            throw new CommandException(MESSAGE_DUPLICATE_TODO);
        }

    }

}
