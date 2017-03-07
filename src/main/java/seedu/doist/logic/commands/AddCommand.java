package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
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

    public static final String MESSAGE_USAGE = info().getUsageTextForCommandWords() + ": Adds a task to Doist "
            + "Parameters: TASK_DESCRIPTION  [\\from START_TIME] [\\to END_TIME] [\\as PRIORITY] [\\under TAG...]"
            + "Example: " + DEFAULT_COMMAND_WORD + "Group meeting \\from 1600 \\to 1800 \\as IMPORTANT \\under school ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This task already exists in the to-do list";

    private final Task toAdd;

    /**
     * Creates an AddCommand using raw values.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    public AddCommand(String preamble, Map<String, java.util.List<String>> parameters) throws IllegalValueException {
        if (preamble == null || preamble.trim().isEmpty()) {
            throw new IllegalValueException("No arguments passed");
        }
        java.util.List<String> tags = parameters.get("\\under");
        final Set<Tag> tagSet = new HashSet<>();

        if (tags != null && tags.size() > 0) {
            String allTags = tags.get(0).trim();
            String[] extractedTags = allTags.split(" ");
            for (String extractedTag : extractedTags) {
                tagSet.add(new Tag(extractedTag));
            }
        }
        this.toAdd = new Task(new Description(preamble), new UniqueTagList(tagSet));
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
