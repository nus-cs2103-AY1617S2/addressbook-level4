package seedu.doist.logic.commands;

import seedu.doist.model.TodoList;

/**
 * Clears the to-do list.
 */
public class ClearCommand extends Command {

    public static final String DEFAULT_COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "To-do list has been cleared!";

    @Override
    public CommandResult execute() {
        assert model != null;
        model.resetData(new TodoList());
        return new CommandResult(MESSAGE_SUCCESS, true);
    }

    public static CommandInfo info() {
        return new CommandInfo(Command.getAliasList(DEFAULT_COMMAND_WORD), DEFAULT_COMMAND_WORD);
    }
}
