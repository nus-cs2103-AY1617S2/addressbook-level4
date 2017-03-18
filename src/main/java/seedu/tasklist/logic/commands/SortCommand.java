package seedu.tasklist.logic.commands;

import java.util.Set;

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    private String parameter;
    private String order = "default";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":Sorts task according to the parameter specified. "
            + "Only 1 of the 5. "
            + "Name:n"
            + "Priority:p or StartDate: sd or EndDate:ed or Deadline:d"
            + "dsc for descending order \n"
            + "Parameters: n/, p/, sd/, ed/ dsc\n"
            + "Example: " + COMMAND_WORD + " n";
    public static final String MESSAGE_FAILURE = "Invalid sorting parameter used!" + MESSAGE_USAGE;
    public static final String MESSAGE_SUCCESS = "Task sorted according to given parameter";

    public static final String SORT_NAME = "n/";
    public static final String SORT_PRIORITY = "p/";
    public static final String SORT_DEADLINE = "d/";
    public static final String SORT_START_DATE = "sd/";
    public static final String SORT_END_DATE = "ed/";
    public static final String SORT_DESCENDING = "dsc";
    
    private Set<String> keyword; 

    public SortCommand(Set<String> args) {
        assert args != null;
        this.keyword = args; 
    }
    
    @Override
    public CommandResult execute() {  
        if (keyword.contains(SORT_NAME)) {
            this.parameter = "Name";
        } else if (keyword.contains(SORT_PRIORITY)) {
            this.parameter = "Priority";
        } else if (keyword.contains(SORT_DEADLINE)) {
            this.parameter = "Deadline";
        } else if (keyword.contains(SORT_START_DATE)) {
            this.parameter = "Start Date";
        } else if (keyword.contains(SORT_END_DATE)) {
            this.parameter = "End Date";
        }

        if (keyword.contains(SORT_DESCENDING)) {
            this.order = "dsc";
        }

        if (this.parameter == null || keyword.size() > 3) {
            return new CommandResult(MESSAGE_FAILURE);
        }
        model.sortTaskList(this.parameter, this.order);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
