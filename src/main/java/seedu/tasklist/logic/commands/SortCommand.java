package seedu.tasklist.logic.commands;

//@@author A0141993X
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    private final String parameter;

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Sorts task according to the parameter specified. "
            + "Only 1 of the 5. "
            + "Name:n"
            + "Priority:p or StartDate: sd or EndDate:ed or Deadline:d\n"
            + "Parameters: n, p, sd, ed\n"
            + "Example: " + COMMAND_WORD + " n";
    public static final String MESSAGE_FAILURE = "Invalid sorting parameter used!" + MESSAGE_USAGE;
    public static final String MESSAGE_SUCCESS = "Task sorted according to given parameter.";

    public SortCommand(String parameter) {
        switch (parameter) {
        case "d":
            this.parameter = "Date";
            break;
        case "n":
            this.parameter = "Name";
            break;
        case "p":
            this.parameter = "Priority";
            break;
        default:
            this.parameter = null;
            break;
        }
    }

    @Override
    public CommandResult execute() {

        if (parameter == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        model.sortTaskList(parameter);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
