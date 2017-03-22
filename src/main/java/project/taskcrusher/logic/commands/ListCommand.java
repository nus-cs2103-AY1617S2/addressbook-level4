package project.taskcrusher.logic.commands;

import java.util.Date;

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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks/events that satisfies the given qualifier "
            + "list: list command with no qualifier lists all tasks and events\n"
            + "list d/DEADLINE: lists all tasks whose deadline is set before DEADLINE"
            + "list d/TIMESLOT: lists all events whose timeslot overlaps with TIMESLOT";

    public static final String MESSAGE_SUCCESS = "Listed all relevant events/tasks";

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
        } else if (dateRange != null) {
            model.updateFilteredEventList(dateRange);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

}
