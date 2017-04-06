package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.NoSuchElementException;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;

//@@author A0143076J
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        try {
            //extract dates
            DateTimeParser dateTimeParser = new DateTimeParser();
            dateTimeParser.parse(args);
            String argsWithDatesExtracted = dateTimeParser.getUnparsedArgs();

            //extract tags
            TagsParser tagsParser = new TagsParser();
            tagsParser.parse(argsWithDatesExtracted);
            String argsWithDatesAndTagsExtracted = tagsParser.getUnparsedArgs();

            //extract description
            if (argsWithDatesAndTagsExtracted.isEmpty()) {
                throw new NoSuchElementException();
            }
            String description = argsWithDatesAndTagsExtracted;

            return new AddCommand(description, dateTimeParser.getStartDate(), dateTimeParser.getEndDate(),
                                  tagsParser.getTags(), dateTimeParser.getTaskType());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

}
