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
import seedu.doist.model.task.Task;
import seedu.doist.model.task.UniqueTaskList;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("add", "do"));
    public static final String DEFAULT_COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = getUsageTextForCommandWords() + ": Adds a task to Doist "
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
    public AddCommand(ArrayList<String> arguments) throws IllegalValueException {
        if(arguments.isEmpty()){
            throw new IllegalValueException("No arguments passed");
        }
        final Set<Tag> tagSet = new HashSet<>();
        
        this.toAdd = new Task(new Description(arguments.get(0)), new UniqueTagList(tagSet));
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

    /**
     * @return a string containing all the command words to be shown in the
     *         usage message, in the format of (word1|word2|...)
     */
    protected static String getUsageTextForCommandWords() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        if (!commandWords.contains(DEFAULT_COMMAND_WORD)) {
            sb.append(DEFAULT_COMMAND_WORD + "|");
        }
        for (String commandWord : commandWords) {
            sb.append(commandWord + "|");
        }
        sb.setCharAt(sb.length() - 1, ')');
        return sb.toString();
    }

    public static boolean canCommandBeTriggeredByWord(String word) {
        return commandWords.contains(word) || DEFAULT_COMMAND_WORD.equals(word);
    }
}
