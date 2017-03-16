package seedu.tasklist.logic.commands;

import seedu.tasklist.logic.commands.exceptions.CommandException;

public class SortCommand extends Command{
    
    public static final String COMMAND_WORD = "sort";
    private final String parameter;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Sorts task according to the parameter specified. Only 1 of the 4. Name:n"
            + "Priority:p + StartDate: sd or EndDate:ed\n"
            + "Parameters: n, p, sd, ed\n"
            + "Example: " + COMMAND_WORD + "n";

    
    public SortCommand(String parameter) {
        this.parameter = parameter;
    }

    @Override
    public CommandResult execute() throws CommandException {
       
        return null;
    }

}
