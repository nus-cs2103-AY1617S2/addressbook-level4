package seedu.task.logic.commands;


/**
 * Lists all task in KIT to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD_1 = "list";
    public static final String COMMAND_WORD_2 = "ls";
    public static final String COMMAND_WORD_3 = "l";
    public static final String COMMAND_WORD_4 = "done";
   // public static final String COMMAND_WORD_5 = "ld";

    public static final String MESSAGE_LISTBYDONE_SUCCESS = "Listed all done tasks";
    public static final String MESSAGE_USAGE2 = COMMAND_WORD_4 + ": Lists all done tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_4;

    public static final String COMMAND_WORD_NOT_DONE = "notdone";
    public static final String COMMAND_WORD_7 = "undone";
   // public static final String COMMAND_WORD_8 = "lnd";

    public static final String MESSAGE_LISTBYNOTDONE_SUCCESS = "Listed all undone tasks";
    public static final String MESSAGE_USAGE3 = COMMAND_WORD_NOT_DONE + ": Lists all undone tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_1;

    
    public static final String MESSAGE_SUCCESS = "Listed all tasks";
    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Lists all tasks in KIT.\n"
            + "Example: " + COMMAND_WORD_NOT_DONE;

    public static final String MESSAGE_FAIL = "Wrong command format";

    private final int value;
    
    public ListCommand(String args) {
        // TODO Auto-generated constructor stub
        if (args.trim().isEmpty()) {
            this.value = 1;
        }
        else if (args.trim().equals("done")) {
            this.value =2;
        }
        else if (args.trim().equals("undone") || args.trim().equals("notdone")) {
            this.value = 3;
        }
        else if (args.trim().equals("floating") || args.trim().equals("float")) {
            this.value = 4;
        }
        else {
            this.value =0;
        }
            
            
    }
    
    @Override
    public CommandResult execute() {
        model.sortTaskList();
        if(value == 1) {
        model.updateFilteredListToShowAll();
        return new CommandResult(MESSAGE_SUCCESS);
        }
        else if (value == 2) {
        model.updateFilteredTaskList(true);
        return new CommandResult(getMessageForDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        }
        else if (value == 3) {
            model.updateFilteredTaskList(false);
            return new CommandResult(getMessageForUnDoneTaskListShownSummary(model.getFilteredTaskList().size()));
        } else if (value ==4) {
            model.updateFilteredTaskListFloat();
            return new CommandResult(getMessageForFloatingTaskListShownSummary(model.getFilteredTaskList().size()));
        }
        else {
        return  new CommandResult(MESSAGE_FAIL);
    }
    }    

}
