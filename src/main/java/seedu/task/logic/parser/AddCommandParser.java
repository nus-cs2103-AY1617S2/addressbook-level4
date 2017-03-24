package seedu.task.logic.parser;

import static seedu.task.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.NattyDateUtil;
import seedu.task.logic.commands.AddCommand;
import seedu.task.logic.commands.Command;
import seedu.task.logic.commands.IncorrectCommand;
import seedu.task.model.UndoManager;

//@@author A0146789H
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final String ARGUMENTS_PATTERN =
            "^(?<description>.+?)(?:\\s+from\\s+(?<startdate>.+?))?(?:\\s+to\\s+(?<enddate>.+?))?(?<tags>(?:\\s+#\\w+)+)?$";
    private static final Pattern ARGUMENTS_FORMAT = Pattern.compile(ARGUMENTS_PATTERN, Pattern.CASE_INSENSITIVE);

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    private static final String logPrefix = "[AddCommandParser]";


    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {

        String taskName;
        String startDateString;
        String endDateString;
        String tagsString;
        Date startDate = null;
        Date endDate = null;

        Set<String> tagSet = new HashSet<String>();

        try {
            // Extract the tokens from the argument string.
            final Matcher matcher = ARGUMENTS_FORMAT.matcher(args);
            if (!matcher.matches()) {
                throw new NoSuchElementException();
            }

            taskName = matcher.group("description");
            startDateString = Optional.ofNullable(matcher.group("startdate")).orElse("");
            endDateString = Optional.ofNullable(matcher.group("enddate")).orElse("");
            tagsString = Optional.ofNullable(matcher.group("tags")).orElse("").trim();

            // Log tokens for debugging.
            logger.info(String.format("%s taskName: '%s', startDateString: '%s', endDateString: '%s', tags: '%s'", logPrefix,
                    taskName, startDateString, endDateString, tagsString));

            // Convert the String to Date objects
            startDate = NattyDateUtil.parseSingleDate(startDateString);
            endDate = NattyDateUtil.parseSingleDate(endDateString);

            // Log the dates for debugging.
            if (startDate != null) {
                logger.info(String.format("%s startDate: %s", logPrefix, startDate.toString()));
            }
            if (endDate != null) {
                logger.info(String.format("%s endDate: %s", logPrefix, endDate.toString()));
            }

            // Add each tag to the tag set.
            for (String i : tagsString.split("\\s+")) {
                if (i.length() > 1) {
                    tagSet.add(i.substring(1));
                }
            }

            // Log the tags
            logger.info(String.format("%s tagSet: %s", logPrefix, tagSet.toString()));

            // Add the undo entry after successfully parsing an AddCommand
            UndoManager.pushCommand(AddCommand.COMMAND_WORD);

            return new AddCommand(
                    taskName,
                    startDate,
                    endDateString,
                    false,
                    tagSet);
        } catch (NoSuchElementException nsee) {
            // TODO: This needs to be changed to the default case of search.
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
