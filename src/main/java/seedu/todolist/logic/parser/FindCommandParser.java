package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_COMPLETE_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.todolist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.commons.util.StringUtil;
import seedu.todolist.logic.commands.AddCommand;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.commands.FindCommand.FindTime;
import seedu.todolist.logic.commands.IncorrectCommand;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {
    //@@author A0163720M, A0163786N
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws IllegalValueException
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_START_TIME, PREFIX_END_TIME, PREFIX_TAG, PREFIX_COMPLETE_TIME);
        argsTokenizer.tokenize(args);
        // Fetch the keyword string before the prefix
        Optional<String> keywordsString = argsTokenizer.getPreamble();
        Optional<List<String>> tags = argsTokenizer.getAllValues(PREFIX_TAG);
        Optional<String> startTime = argsTokenizer.getValue(PREFIX_START_TIME);
        Optional<String> endTime = argsTokenizer.getValue(PREFIX_END_TIME);
        Optional<String> completeTime = argsTokenizer.getValue(PREFIX_COMPLETE_TIME);

        int paramCounter = keywordsString.isPresent() ? 1 : 0;
        paramCounter += tags.isPresent() ? 1 : 0;
        paramCounter += startTime.isPresent() ? 1 : 0;
        paramCounter += endTime.isPresent() ? 1 : 0;
        paramCounter += completeTime.isPresent() ? 1 : 0;

        // User must enter either the search keyword or parameters with which to search
        if (paramCounter != 1) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        try {
            if (keywordsString.isPresent()) {
                final String[] keywords = keywordsString.get().split("\\s+");
                final Set<String> keywordsSet = new HashSet<>(Arrays.asList(keywords));
                return new FindCommand(keywordsSet);
            } else if (startTime.isPresent()) {
                Date startTimeSet = StringUtil.parseDate(startTime.get(), AddCommand.DATE_FORMAT);
                return new FindCommand(startTimeSet, FindTime.START_TIME);
            } else if (endTime.isPresent()) {
                Date endTimeSet = StringUtil.parseDate(startTime.get(), AddCommand.DATE_FORMAT);
                return new FindCommand(endTimeSet, FindTime.END_TIME);
            } else if (completeTime.isPresent()) {
                Date completeTimeSet = StringUtil.parseDate(completeTime.get(), AddCommand.DATE_FORMAT);
                return new FindCommand(completeTimeSet, FindTime.COMPLETE_TIME);
            } else {
                // Store the individual tag strings in a set
                final Set<String> tagsStrings = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
                final Set<Tag> tagsSet = new HashSet<>();

                for (String tagName : tagsStrings) {
                    tagsSet.add(new Tag(tagName));
                }

                final UniqueTagList uniqueTagList = new UniqueTagList(tagsSet);

                return new FindCommand(uniqueTagList);
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }
    //@@author
}
