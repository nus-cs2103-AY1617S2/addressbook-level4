package seedu.todolist.logic.parser;

import static seedu.todolist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.todolist.commons.exceptions.IllegalValueException;
import seedu.todolist.logic.commands.Command;
import seedu.todolist.logic.commands.FindCommand;
import seedu.todolist.logic.commands.IncorrectCommand;
import seedu.todolist.model.tag.Tag;
import seedu.todolist.model.tag.UniqueTagList;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser {
    //@@author A0163720M
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns an FindCommand object for execution.
     * @throws IllegalValueException
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_TAG);
        argsTokenizer.tokenize(args);
        // Fetch the keyword string before the prefix
        Optional<String> keywordsString = argsTokenizer.getPreamble();

        // User must enter either the search keyword or parameters with which to search
        if (!keywordsString.isPresent() && !argsTokenizer.getAllValues(PREFIX_TAG).isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        try {
            if (keywordsString.isPresent()) {
                final String[] keywords = keywordsString.get().split("\\s+");
                final Set<String> keywordsSet = new HashSet<>(Arrays.asList(keywords));

                return new FindCommand(keywordsSet);
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
