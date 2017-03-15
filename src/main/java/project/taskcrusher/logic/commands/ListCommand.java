package project.taskcrusher.logic.commands;

import java.util.Date;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.task.Deadline;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String TASK_FLAG = "t";
    public static final String EVENT_FLAG = "e";
    public static final String OVERDUE_FLAG = "o";
    public static final String COMPLETE_FLAG = "c";
    public static final String NO_FLAG = "";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks in the active list "
            + "before the deadline, if provided"
            + "Parameters: [t] [d/DEADLINE] \n"
            + "Example: " + COMMAND_WORD + "t d/tomorrow";
    public static final String MESSAGE_SUCCESS = "Listed all relevant tasks";

    //TODO some values here reserved for later
    private final Date start;
    private final Date end;
    private boolean listTasksOnly;
    private boolean listEventsOnly;

    public ListCommand(String deadline, boolean listTasksOnly, boolean listEventsOnly) throws IllegalValueException {
        Deadline parsedDeadline = new Deadline(deadline);
        if (parsedDeadline.hasDeadline()) {
            this.start = parsedDeadline.getDate().get();
            this.end = parsedDeadline.getDate().get();
        } else { //TODO only a temporary solution
            this.start = null;
            this.end = null;
        }
        //TODO won't use for now
        this.listTasksOnly = listTasksOnly;
        this.listEventsOnly = listEventsOnly;
    }

    @Override
    public CommandResult execute() {

        if (this.start == null) {
            model.updateFilteredListToShowAll();
        } else {
            model.updateFilteredTaskList(start);
        }

        //TODO updateFilteredTaskList(Date a, Date b) and updateFilteredEventList(Date a, Date b)
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
