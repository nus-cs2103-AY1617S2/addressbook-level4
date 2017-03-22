package seedu.watodo.logic.parser;

import static seedu.watodo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.watodo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import seedu.watodo.commons.exceptions.IllegalValueException;
import seedu.watodo.logic.commands.AddCommand;
import seedu.watodo.logic.commands.Command;
import seedu.watodo.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private DateTimeParser dateTimeParser;
    private String description;
    private Set<String> tags;

    /** Creates an AddCommandParser object that contains a dateTimeParser object */
    public AddCommandParser() {
        dateTimeParser = new DateTimeParser();
        tags = new HashSet<String>();
    }

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        try {
            dateTimeParser.parse(args);
            String argsWithDatesExtracted = dateTimeParser.trimArgsOfDates(args);

            extractTags(argsWithDatesExtracted);
            String argsWithDatesAndTagsExtracted = trimArgsOfTags(argsWithDatesExtracted);
            description = argsWithDatesAndTagsExtracted;

            return new AddCommand(description, dateTimeParser.getStartDate(), dateTimeParser.getEndDate(),
                                  tags, dateTimeParser.getTaskType());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

    /**
     *
     * @param argsWithDatesExtracted
     */
    private void extractTags(String argsWithDatesExtracted) {
        ArgumentTokenizer tagsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        tagsTokenizer.tokenize(argsWithDatesExtracted);
        if (tagsTokenizer.getAllValues(PREFIX_TAG).isPresent()) {
            List<String> tags = tagsTokenizer.getAllValues(PREFIX_TAG).get();
            List<String> parsedTags = new ArrayList<String>();
            for (String tag : tags) {
                parsedTags.add(tag.split("[\\s+]", 2)[0]);
            }
            this.tags = ParserUtil.toSet(Optional.of(parsedTags));
        }
    }

    /**
     *
     */
    private String trimArgsOfTags(String args) {
        return args.replaceAll(PREFIX_TAG.getPrefix() + "(\\S+)", "");
    }

}
