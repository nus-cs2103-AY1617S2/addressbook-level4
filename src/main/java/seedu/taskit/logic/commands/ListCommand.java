package seedu.taskit.logic.commands;

import java.util.logging.Logger;
import seedu.taskit.commons.core.LogsCenter;

//@@author A0141872E
/**
 * Lists all tasks in TaskIt to the user based on given parameters.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMMAND_WORD_ALIAS = "l";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all the existing tasks in TaskIt \n"
            + "Parameters: [all, done, undone, overdue, today]\n"
            + "Example: " + COMMAND_WORD + " undone\n" + COMMAND_WORD + " today\n";

    public static final String MESSAGE_SUCCESS_ALL = "Listed all tasks";
    public static final String MESSAGE_SUCCESS_SPECIFIC = "Listed all relevant tasks for %1$s";
    public static final String MESSAGE_NO_TASK_TODAY = "There is no incomplete task for today! Great";

    private String parameter;
    private final Logger logger = LogsCenter.getLogger(ListCommand.class);

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
        logger.info("-------[Executing ListCommands]"+ this.toString() );

        int taskListSize;
        switch (parameter) {
              case "all":
                  model.updateFilteredListToShowAll();
                  return new CommandResult(MESSAGE_SUCCESS_ALL);

              case "today":
                  taskListSize=model.updateFilteredTaskList("today");
                  assert(taskListSize>=0);
                  if(taskListSize==0){
                      return new CommandResult(MESSAGE_NO_TASK_TODAY);
                  }
                  return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, "today"));

              default:
                  model.updateFilteredTaskList(parameter);
                  return new CommandResult(String.format(MESSAGE_SUCCESS_SPECIFIC, parameter));
        }
    }

    @Override
    public String toString() {
        return null;
    }


    //@@author A0141011J
    @Override
    public boolean isUndoable() {
        return true;
    }
}
