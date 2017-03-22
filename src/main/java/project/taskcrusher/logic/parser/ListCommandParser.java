package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.ListCommand;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.task.Deadline;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser {

    public static final String FLAG_TASK_OR_EVENT_VALIDATION_REGEX = "[" + ListCommand.TASK_FLAG
            + ListCommand.EVENT_FLAG + "]";
    public static final String FLAG_OVERDUE_OR_COMPLETE_VALIDATION_REGEX = "[" + ListCommand.OVERDUE_FLAG
            + ListCommand.COMPLETE_FLAG + "]";

    private String taskOrEventFlag;
    private String overdueOrCompleteFlag;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * ListCommand and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE);
        argsTokenizer.tokenize(args);

        String taskOrEventFlag = ListCommand.NO_FLAG;

        // TODO this flag is currently unused
        String overdueOrCompleteFlag = ListCommand.NO_FLAG;

        Optional<String> rawFlag = argsTokenizer.getPreamble();
        if (rawFlag.isPresent()) {
            String[] flags = rawFlag.get().split("\\s+");
            if (flags.length > 2) {
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
            }

            try {
                assignFlag(flags[0]);
                if (flags.length > 1) {
                    assignFlag(flags[1]);
                }
            } catch (IllegalValueException ive) {
                return new IncorrectCommand(ive.getMessage());
            }
        }

        String date = Deadline.NO_DEADLINE;
        Optional<String> rawDate = argsTokenizer.getValue(PREFIX_DATE);
        if (rawDate.isPresent()) {
            date = rawDate.get();
        }

        switch (taskOrEventFlag) {
        case ListCommand.NO_FLAG:
            return listAll();
        case ListCommand.TASK_FLAG:
            return listTasks(date);
        case ListCommand.EVENT_FLAG:
            return listEvents(date);
        default:
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }
    }

    private Command listTasks(String date) {
        try {
            return new ListCommand(new Deadline(date));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Command listEvents(String date) {
        try {

            // TODO refactor this later into Util? also used in AddCommandParser
            String[] timeslotsAsStrings = date.split("\\s+or\\s+");
            List<Timeslot> timeslots = new ArrayList<>();
            for (String t : timeslotsAsStrings) {
                String[] dates = t.split("\\s+to\\s+");
                timeslots.add(new Timeslot(dates[0], dates[1]));
            }

            if (timeslots.size() != 1) {
                throw new IllegalValueException("SHOULD HAVE ONLY ONE TIMESLOT... FIX THIS MESSAGE LATER");
            }

            return new ListCommand(timeslots.get(0));

        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Command listAll() {
        return new ListCommand();
    }

    private boolean isTaskOrEventFlag(String flag) {
        return flag.matches(FLAG_TASK_OR_EVENT_VALIDATION_REGEX);
    }

    private boolean isOverdueOrCompleteFlag(String flag) {
        return flag.matches(FLAG_OVERDUE_OR_COMPLETE_VALIDATION_REGEX);
    }

    private void assignFlag(String flag) throws IllegalValueException {
        if (isTaskOrEventFlag(flag)) {
            this.taskOrEventFlag = flag;
        } else if (isOverdueOrCompleteFlag(flag)) {
            this.overdueOrCompleteFlag = flag;
        } else {
            throw new IllegalValueException("EDIT THIS");
        }
    }
}
