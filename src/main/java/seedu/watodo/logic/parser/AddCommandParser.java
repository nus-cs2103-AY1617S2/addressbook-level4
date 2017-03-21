package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_ON;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    public enum TaskType { FLOAT, DEADLINE, EVENT };

    private TaskType type;
    private String description;
    private String startDate = null;
    private String endDate = null;

    public static final String MESSAGE_INVALID_NUM_DATETIME = "Too many/few dateTime arguments!";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        try {
            String argsWithDatesExtracted = extractDateArgs(args);
            ArgumentTokenizer tagsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
            tagsTokenizer.tokenize(argsWithDatesExtracted);
            description = trimArgsOfTags(argsWithDatesExtracted);
            return new AddCommand(description, startDate, endDate,
                    ParserUtil.toSet(tagsTokenizer.getAllValues(PREFIX_TAG)), type);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    private String extractDateArgs(String args) throws IllegalValueException {

        ArgumentTokenizer datesTokenizer = new ArgumentTokenizer(PREFIX_BY, PREFIX_ON, PREFIX_FROM, PREFIX_TO);
        datesTokenizer.tokenize(args);

        boolean hasByPrefix = datesTokenizer.getValue(PREFIX_BY).isPresent();
        boolean hasOnPrefix = datesTokenizer.getValue(PREFIX_ON).isPresent();
        boolean hasFromPrefix = datesTokenizer.getValue(PREFIX_FROM).isPresent();
        boolean hasToPrefix = datesTokenizer.getValue(PREFIX_TO).isPresent();
        
        type = checkTaskType(hasByPrefix, hasOnPrefix, hasFromPrefix, hasToPrefix);

        if (type.equals(TaskType.DEADLINE) || type.equals(TaskType.EVENT)) {
            extractDates(datesTokenizer.getValue(PREFIX_BY).orElse(null), 
                         datesTokenizer.getValue(PREFIX_ON).orElse(null),
                         datesTokenizer.getValue(PREFIX_FROM).orElse(null), 
                         datesTokenizer.getValue(PREFIX_TO).orElse(null));
        }
        return trimArgsOfDates(args, startDate, endDate);
    }

    /**
     * Checks the type of task(floating, deadline or event) to be added based on
     * the DATETIME parameters entered by the user.
     * 
     * @throws IllegalValueException if too many or too few dateTime args are
     *             entered
     */
    private TaskType checkTaskType(boolean hasBy, boolean hasOn, boolean hasFrom, boolean hasTo)
            throws IllegalValueException {
        if (!hasBy && !hasOn && !hasFrom && !hasTo) {
            return TaskType.FLOAT;
        }
        if ((hasBy && !hasOn && !hasFrom && !hasTo) || (!hasBy && hasOn && !hasFrom && !hasTo)) {
            return TaskType.DEADLINE;
        }
        if ((!hasBy && !hasOn && hasFrom && hasTo) || (!hasBy && hasOn && !hasFrom && hasTo)) {
            return TaskType.EVENT;
        }
        throw new IllegalValueException(MESSAGE_INVALID_NUM_DATETIME);
    }

    private void extractDates(String... args) throws IllegalValueException {
        List<String> datesInText = new ArrayList<String>();
        for (String arg : args) {
            if (arg != null) {
                String dateText = DateTimeParser.parse(arg);
                datesInText.add(dateText);
            }
        }
        if (datesInText.size() == 1) {
            endDate = datesInText.get(0);
        }
        if (datesInText.size() == 2) {
            startDate = datesInText.get(0);
            endDate = datesInText.get(1);
        }
    }

    private String trimArgsOfDates(String args, String startDate, String endDate) {

        args = args.replaceAll(PREFIX_BY.getPrefix(), "");
        args = args.replaceAll(PREFIX_ON.getPrefix(), ""); 
        args = args.replaceAll(PREFIX_FROM.getPrefix(), ""); 
        args = args.replaceAll(PREFIX_TO.getPrefix(), "");
        if (startDate != null) {
            args = args.replaceAll(startDate, "");
        }
        if (endDate != null) {
            args = args.replaceAll(endDate, "");
        }
        return args.trim();
    }
    
    private String trimArgsOfTags(String args) {
        return args.replaceAll(PREFIX_TAG.getPrefix()+"(\\S+)", ""); 
    }
    
}
