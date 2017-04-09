package seedu.task.logic.commands;

import java.util.function.Predicate;

import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0163845X
public class FilterCommand extends Command {
    private Predicate<Object> predicate;
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_SUCCESS = "Results filtered";
    public static final String MESSAGE_FAILURE = "Failed to filter";

    public FilterCommand(Predicate<Object> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        // try {
        model.filter(predicate);
        return new CommandResult(MESSAGE_SUCCESS);

    }

}
