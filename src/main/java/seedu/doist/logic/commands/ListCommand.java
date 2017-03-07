package seedu.doist.logic.commands;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static ArrayList<String> commandWords = new ArrayList<>(Arrays.asList("list", "ls"));
    public static final String DEFAULT_COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";

    @Override
    public CommandResult execute() {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    public static CommandInfo info() {
        return new CommandInfo(commandWords, DEFAULT_COMMAND_WORD);
    }
}
