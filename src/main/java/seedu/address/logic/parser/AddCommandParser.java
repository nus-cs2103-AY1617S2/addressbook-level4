package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LABEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RECURRENCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_END;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMEINTERVAL_START;

import java.util.NoSuchElementException;
import java.util.Optional;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.task.Recurrence;

//@@author A0162877N
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser extends Parser {

    public static final int VALID_DATEARR_SIZE = 1;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_LABEL);
        argsTokenizer.tokenize(args);
        try {
            String title = argsTokenizer.getPreamble().get();

            if (args.contains(PREFIX_TIMEINTERVAL_START.getPrefix())
                    && args.contains(PREFIX_TIMEINTERVAL_END.getPrefix())) {
                argsTokenizer = new ArgumentTokenizer(PREFIX_TIMEINTERVAL_START, PREFIX_TIMEINTERVAL_END, PREFIX_LABEL,
                        PREFIX_RECURRENCE);
                argsTokenizer.tokenize(args);
                String startDT = argsTokenizer.getValue(PREFIX_TIMEINTERVAL_START).get();
                String endDT = argsTokenizer.getValue(PREFIX_TIMEINTERVAL_END).get();
                Boolean isRecurring = false;

                if (isDateParseable(startDT) && isDateParseable(endDT)) {
                    title = args.substring(0, args.lastIndexOf("from"));
                    if (!args.contains(PREFIX_RECURRENCE.getPrefix())) {
                        return new AddCommand(title, startDT, endDT,
                            ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)), isRecurring, Optional.empty());
                    } else {
                        isRecurring = true;
                        return new AddCommand(title, startDT, endDT,
                                ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)), isRecurring,
                                    Optional.ofNullable(new Recurrence(argsTokenizer
                                            .getValue(PREFIX_RECURRENCE).get())));
                    }
                }
            }

            if (args.contains(PREFIX_DEADLINE.getPrefix())) {
                argsTokenizer = new ArgumentTokenizer(PREFIX_DEADLINE, PREFIX_LABEL, PREFIX_RECURRENCE);
                argsTokenizer.tokenize(args);
                String deadline = argsTokenizer.getValue(PREFIX_DEADLINE).get();
                Boolean isRecurring = false;

                if (isDateParseable(deadline)) {
                    title = argsTokenizer.getPreamble().get();
                    if (!args.contains(PREFIX_RECURRENCE.getPrefix())) {
                        return new AddCommand(title, deadline.trim(),
                            ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)), isRecurring, Optional.empty());
                    } else {
                        isRecurring = true;
                        return new AddCommand(title, deadline.trim(),
                                ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)), isRecurring,
                                Optional.ofNullable(new Recurrence(argsTokenizer.getValue(PREFIX_RECURRENCE).get())));
                    }
                }
            }
            return new AddCommand(title, ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_LABEL)));
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (Exception e) {
            return new IncorrectCommand(e.getMessage());
        }
    }

}
