package seedu.watodo.logic.commands;

import java.util.Calendar;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.model.task.DateTime;

//@@author A0139872R-reused
/**
 * Lists all overdue tasks and upcoming tasks due the next day in the task manager to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    
    public static final String ARGUMENT = "";

    public static final String MESSAGE_SUCCESS = "Listed all overdue tasks and tasks due tomorrow";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists tasks that fit the specified keyword as a filter to the user. "
            + "Parameters: LIST_TYPE\n" + "Example: " + COMMAND_WORD + " all";
    private static final int DAY_OFFSET = 2;
    private static final int OFFSET = 0;
    private static DateTime tomorrow;
    private Calendar temp;
    
    public ListCommand(){
        temp = Calendar.getInstance();
        temp.add(Calendar.DATE, DAY_OFFSET);
        temp.set(Calendar.HOUR_OF_DAY, OFFSET);
        temp.set(Calendar.MINUTE, OFFSET);
        temp.set(Calendar.SECOND, OFFSET);
        temp.set(Calendar.MILLISECOND, OFFSET);
        try {
          tomorrow = new DateTime(temp.getTime().toString());
        } catch (IllegalValueException e) {
          e.printStackTrace();
        }
    }

    @Override
    public CommandResult execute() {
        try {
            model.updateFilteredByDatesTaskList(null, tomorrow);
        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
