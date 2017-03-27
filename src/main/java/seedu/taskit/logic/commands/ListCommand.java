package seedu.taskit.logic.commands;

import static seedu.taskit.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

//@author A0141872E
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the existing tasks in TaskIt \n"
            + "Parameters: [all, done, undone, overdue, today]\n" 
            + "Example: " + COMMAND_WORD + " undone\n" + COMMAND_WORD + " today\n";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Listed all relevant tasks for %1$s";
    public static final String MESSAGE_NO_TASK_TODAY = "There is no incomplete task for today! Great";

    private String parameter;
    
    /**
     * List all relevant tasks in TaskIt.
     * 
     * @param args the requested parameter
     */
    public ListCommand (String parameter) {
        this.parameter = parameter;  
    }

    @Override
    public CommandResult execute() {
        int taskListSize;
        switch (parameter) {
              case "all":
                  model.updateFilteredListToShowAll();
                  return new CommandResult(MESSAGE_SUCCESS_ALL);
          
              case "done":
                  model.updateFilteredTaskList("done");
                  return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, "done"));
          
              case "undone":
                  model.updateFilteredTaskList("undone");
                  return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, "undone"));
          
              case "overdue":
                  model.updateFilteredTaskList("overdue");
                  return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, "overdue"));
          
              case "today":
                  taskListSize=model.updateFilteredTaskList("today");
                  assert(taskListSize>=0);
                  if(taskListSize==0){
                      return new CommandResult(MESSAGE_NO_TASK_TODAY);
                  }
                  return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, "today"));
            
              default:
                  return new CommandResult(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
        }
    }

}
