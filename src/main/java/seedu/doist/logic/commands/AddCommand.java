package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.model.tag.Tag;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("add", "do"));
    public static final String DEFAULT_COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = info().getUsageTextForCommandWords()
            + ": Adds a person to the address book. "
            + "Parameters: NAME p/PHONE e/EMAIL a/ADDRESS  [t/TAG]...\n" + "Example: " + DEFAULT_COMMAND_WORD
            + " John Doe p/98765432 e/johnd@gmail.com a/311, Clementi Ave 2, #02-25 t/friends t/owesMoney";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the to-do list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String name, String priority, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = makeTagSet(tags);
        this.toAdd = new Task(new Description(name), new Priority(priority), new UniqueTagList(tagSet));
    }

    public AddCommand(String name, Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = makeTagSet(tags);
        this.toAdd = new Task(new Description(name), new UniqueTagList(tagSet));
    }

    /**
     * @return a set of tags created by making tags with a set of strings
     */
    private Set<Tag> makeTagSet(Set<String> tags) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            model.addTask(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (UniqueTaskList.DuplicateTaskException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
