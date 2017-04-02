package typetask.logic.commands;

import java.util.Calendar;
import java.util.Date;

//@@author A0139154E
public class ListTodayCommand extends Command {
    public static final String COMMAND_WORD = "listtoday";

    public static final String MESSAGE_SUCCESS = "Today's task(s) listed!";

    private Calendar today = Calendar.getInstance();
    private Date date = new Date();


    @Override
    public CommandResult execute() {
        today.setTime(date);
        model.updateFilteredTaskList(today);
        return new CommandResult(MESSAGE_SUCCESS);
    }

}