package t15b1.taskcrusher.logic.commands;

import java.util.Date;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String TASK_FLAG = "t";
    public static final String EVENT_FLAG = "e";
    public static final String OVERDUE_FLAG = "o";
    public static final String COMPLETE_FLAG = "c";

    public static final String MESSAGE_SUCCESS = "Listed all relevant tasks";
    
    private final Date start;
    private final Date end;
    private boolean listTasksOnly;
    private boolean listEventsOnly;
    
    public ListCommand(Date start, Date end, boolean listTasks, boolean listEvents) {
    	this.start = start;
    	this.end = end;
    	this.listTasksOnly = listTasks;
    	this.listEventsOnly = listEvents;    	    	
    }
    
    @Override
    public CommandResult execute() {
    	
    	assert !(listTasksOnly && listEventsOnly);
    	
    	if (listTasksOnly) {
    		
    	} else if (listEventsOnly) {
    		
    	}
    	
        model.updateFilteredListToShowAll(); //TODO updateFilteredTaskList(Date a, Date b) and updateFilteredEventList(Date a, Date b)
        return new CommandResult(MESSAGE_SUCCESS);
    }
    
}
