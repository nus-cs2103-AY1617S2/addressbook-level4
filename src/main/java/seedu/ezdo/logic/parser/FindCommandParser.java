package seedu.ezdo.logic.parser;

import static seedu.ezdo.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.KEYWORDS_ARGS_FORMAT;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_DUEDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_STARTDATE;
import static seedu.ezdo.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;

import seedu.ezdo.commons.exceptions.IllegalValueException;
import seedu.ezdo.logic.commands.Command;
import seedu.ezdo.logic.commands.FindCommand;
import seedu.ezdo.logic.commands.IncorrectCommand;
import seedu.ezdo.logic.parser.ArgumentTokenizer.Prefix;
import seedu.ezdo.model.tag.Tag;
import seedu.ezdo.model.todo.Priority;
import seedu.ezdo.model.todo.TaskDate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements CommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * FindCommand and returns an FindCommand object for execution.
     */
    @Override
    public Command parse(String args) {
        final Matcher matcher = KEYWORDS_ARGS_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        assert args != null;
 
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_PRIORITY, PREFIX_STARTDATE, PREFIX_DUEDATE,
                PREFIX_TAG);
        argsTokenizer.tokenize(args);
        String namesToMatch = argsTokenizer.getPreamble().orElse(""); //eat sleep rave relax
        String[] splitNames = namesToMatch.split("\\s+");
        Optional<Priority> findPriority;
        Optional<TaskDate> findStartDate;
        Optional<TaskDate> findDueDate;
        Set<String> findTags;
        try {
            findPriority = ParserUtil.parsePriority(getOptionalValue(argsTokenizer, PREFIX_PRIORITY));
            findStartDate = ParserUtil.parseStartDate(getOptionalValue(argsTokenizer, PREFIX_STARTDATE), true);
            findDueDate = ParserUtil.parseDueDate(getOptionalValue(argsTokenizer, PREFIX_DUEDATE), true);
            findTags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
            
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
        
        Set<String> keywords = new HashSet<String>(Arrays.asList(splitNames));
        return new FindCommand(keywords, findPriority, findStartDate, findDueDate, findTags);
    }

    private Optional<String> getOptionalValue(ArgumentTokenizer tokenizer, Prefix prefix) {
        Optional<String> optionalString;
        if (!tokenizer.getValue(prefix).isPresent()) {
            optionalString = Optional.empty();
        } else {
            optionalString = Optional.of(tokenizer.getValue(prefix).get());
        }
        return optionalString;
    }

}
