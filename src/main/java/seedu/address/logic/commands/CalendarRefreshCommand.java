package seedu.address.logic.commands;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.UpdateCalendarEvent;
import seedu.address.logic.commands.exceptions.CommandException;


//@@author A0124377A
/**
 * Refresh calendar schedule to this week
 */
public class CalendarRefreshCommand extends Command {
    private final Logger logger = LogsCenter.getLogger(CalendarRefreshCommand.class);

    public static final String COMMAND_WORD = "refresh";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "\n"
        + "Refresh calendar schedule to current week\n";

    private static final String MESSAGE_SUCCESS = "Calendar showing current week.";
    private LocalDateTime currentDateTime;
    private int weeksAhead;
    private static final int REFRESH_WEEKS = 0;

    public CalendarRefreshCommand() {
        currentDateTime = LocalDateTime.now();
        this.weeksAhead = REFRESH_WEEKS;
    }

    @Override
    public CommandResult execute() throws CommandException {
        logger.info("-------[Executing CalendarViewCommand]" + this.toString());
        EventsCenter.getInstance().post(new UpdateCalendarEvent(currentDateTime, weeksAhead));
        return new CommandResult(String.format(MESSAGE_SUCCESS));

    }
}
