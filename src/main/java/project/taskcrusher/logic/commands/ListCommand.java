package project.taskcrusher.logic.commands;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import project.taskcrusher.model.event.Timeslot;
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
            + "before the deadline, if provided" + "Parameters: [t] [d/DEADLINE] \n" + "Example: " + COMMAND_WORD
            + " t d/tomorrow";
    public static final String MESSAGE_SUCCESS = "Listed all relevant tasks";

    private final Date until;
    private final Timeslot dateRange;

    public ListCommand(Deadline deadline) {
        this.until = deadline.getDate().get();
        this.dateRange = null;
    }

    public ListCommand(Timeslot timeslot) {
        this.dateRange = timeslot;
        this.until = null;
    }

    public ListCommand() {
        this.until = null;
        this.dateRange = null;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert !(until != null && dateRange != null);

        if (until == null && dateRange == null) {
            model.updateFilteredTaskListToShowAll();
            model.updateFilteredEventListToShowAll();
        } else if (until != null) {
            model.updateFilteredTaskList(until);

            // TODO ghetto fix
            Set<String> keywords = new HashSet<String>();
            keywords.add("4yuf5g24f4trfty");
            model.updateFilteredEventList(keywords);

        } else if (dateRange != null) {
            model.updateFilteredEventList(dateRange);

            // TODO ghetto fix
            Set<String> keywords = new HashSet<String>();
            keywords.add("4yuf5g24f4trfty");
            model.updateFilteredTaskList(keywords);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
