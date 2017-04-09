package seedu.task.logic.commands;

import java.util.function.Predicate;

import seedu.task.logic.commands.exceptions.CommandException;

//@@author A0163845X
public class FilterCommand extends Command {
    private Predicate<Object> predicate;
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_SUCCESS = "Results filtered";
    public static final String MESSAGE_FAILURE = "Failed to filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + "Filters the list based off of specified flitering criteria. Format: filter [FILTER_TYPE] [FILTER_ARGUMENT] "
            + "\n[FILTER_TYPE] includes: name, desc (description), status, before, after."
            + "\n[FILTER_ARGUMENT] can include a date, a status, a task description, or the name of a task";

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
