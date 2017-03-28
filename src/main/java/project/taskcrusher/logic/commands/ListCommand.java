package project.taskcrusher.logic.commands;

import java.util.List;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.parser.ParserUtil;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.task.Deadline;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String OVERDUE_FLAG = "o";
    public static final String COMPLETE_FLAG = "c";
    public static final String NO_FLAG = "";

    public static final String MESSAGE_MULTIPLE_DATERANGES = "Multiple date ranges supplied. Supply only one range.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks/events that satisfies the given qualifier "
            + "list: list command with no qualifier lists all tasks and events\n"
            + "list d/DEADLINE: lists all tasks whose deadline is set before DEADLINE"
            + "list d/TIMESLOT: lists all events whose timeslot overlaps with TIMESLOT";

    // TODO fix this message eventually
    public static final String MESSAGE_SUCCESS = "Listed all relevant events/tasks";

    private Timeslot dateRange;
    private boolean showOverdueOnly;
    private boolean showCompleteOnly;

    public ListCommand(String date, boolean showOverdueOnly, boolean showCompleteOnly) throws IllegalValueException {
        if (date.equals(Deadline.NO_DEADLINE)) {
            this.dateRange = null;
        } else {
            this.dateRange = parseDateRange(date);
        }
        this.showOverdueOnly = showOverdueOnly;
        this.showCompleteOnly = showCompleteOnly;
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert !(showOverdueOnly && showCompleteOnly);

        if (dateRange == null) {
            model.updateFilteredTaskListToShowAll();
            model.updateFilteredEventListToShowAll();
        } else {
            // model.updateFilteredTaskList(until);
            model.updateFilteredEventList(dateRange);
        }

        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     *
     * @param date
     * @return
     * @throws IllegalValueException
     */
    private Timeslot parseDateRange(String date) throws IllegalValueException {
        Timeslot dateRange;

        try {
            dateRange = new Timeslot(date);
            return dateRange;
        } catch (IllegalValueException ive) {
            List<Timeslot> timeslots = ParserUtil.parseAsTimeslots(date);
            if (timeslots.size() > 1) {
                throw new IllegalValueException(MESSAGE_MULTIPLE_DATERANGES);
            }
            dateRange = timeslots.get(0);
            return dateRange;
        }
    }

}
