package project.taskcrusher.logic.commands;

import java.util.List;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.parser.ParserUtil;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.task.Deadline;

//@@author A0163962X
/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String COMPLETE_FLAG = "complete";
    public static final String ALL_FLAG = "all";
    public static final String NO_FLAG = "";

    public static final String MESSAGE_MULTIPLE_DATERANGES = "Multiple date ranges supplied. Supply only one range.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists tasks/events that satisfies the given qualifier "
            + "list: list command with no qualifier lists active todos only\n" + "list c: lists only complete events"
            + "list all: lists all events, active and complete"
            + "list d/DEADLINE: lists all todos whose set before now to DEADLINE"
            + "list d/TIMESLOT: lists all todos whose timeslot overlaps with TIMESLOT";

    // TODO fix this message eventually
    public static final String MESSAGE_SUCCESS = "Listed all relevant events/tasks";

    private Timeslot dateRange;
    private boolean showActiveOnly;
    private boolean showCompleteOnly;

    public ListCommand(String date, boolean showActiveOnly, boolean showCompleteOnly) throws IllegalValueException {
        if (date.equals(Deadline.NO_DEADLINE)) {
            this.dateRange = null;
        } else {
            this.dateRange = parseDateRange(date);
        }
        this.showActiveOnly = showActiveOnly;
        this.showCompleteOnly = showCompleteOnly;

        if (dateRange != null && (showCompleteOnly)) { // showActiveOnly allowed
                                                       // as default
            throw new IllegalValueException(MESSAGE_USAGE);
        }
    }

    @Override
    public CommandResult execute() {
        assert model != null;
        assert !(showActiveOnly && showCompleteOnly);

        if (dateRange != null) {
            model.updateFilteredLists(dateRange);
        } else if (!showActiveOnly && !showCompleteOnly) {
            model.updateFilteredListsShowAll();
        } else if (showCompleteOnly) {
            model.updateFilteredListsToShowCompleteToDo();
        } else if (showActiveOnly) {
            model.updateFilteredListsToShowActiveToDo();
        } else if (dateRange != null) {
            model.updateFilteredLists(dateRange);
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

        List<Timeslot> timeslots = ParserUtil.parseForList(date);
        if (timeslots.size() > 1) {
            throw new IllegalValueException(MESSAGE_MULTIPLE_DATERANGES);
        }
        dateRange = timeslots.get(0);

        return dateRange;
    }

}
