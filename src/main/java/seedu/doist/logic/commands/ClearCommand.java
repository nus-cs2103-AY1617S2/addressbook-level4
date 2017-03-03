package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.doist.model.TodoList;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {
	
	public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("clear"));
    public static final String DEFAULT_COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TodoList());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
