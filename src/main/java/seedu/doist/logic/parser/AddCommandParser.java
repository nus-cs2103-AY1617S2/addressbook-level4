package seedu.doist.logic.parser;

import static seedu.doist.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_AS;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_BY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_EVERY;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_REMIND;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_TO;
import static seedu.doist.logic.parser.CliSyntax.PREFIX_UNDER;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.doist.commons.exceptions.IllegalValueException;
import seedu.doist.logic.commands.AddCommand;
import seedu.doist.logic.commands.Command;
import seedu.doist.logic.commands.IncorrectCommand;
import seedu.doist.model.tag.UniqueTagList;
import seedu.doist.model.task.Description;
import seedu.doist.model.task.Priority;
import seedu.doist.model.task.Task;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final Pattern ADD_COMMAND_REGEX = Pattern.compile("(?<preamble>[^\\\\]*)" +
                                                                     "(?<parameters>((\\\\)(\\S+)(\\s+)([^\\\\]*))*)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        final Matcher matcher = ADD_COMMAND_REGEX.matcher(args.trim());
        if (!matcher.matches() || args.trim().equals("")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String preamble = matcher.group("preamble");
        final String parameters = matcher.group("parameters").trim();
        ArrayList<String> tokens = ParserUtil.getParameterKeysFromString(parameters);

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_FROM, PREFIX_TO, PREFIX_REMIND, PREFIX_EVERY,
                                                                PREFIX_AS, PREFIX_BY, PREFIX_UNDER);

        if (!argsTokenizer.validateTokens(tokens) ||
                (argsTokenizer.validateDate(tokens) == ArgumentTokenizer.DATE_INVALID)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        argsTokenizer.tokenize(parameters);

        try {
            Task taskToAdd = createTaskFromParameters(preamble, argsTokenizer);
            return new AddCommand(taskToAdd);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    /**
     * Creates a task from raw values of parameters.
     *
     * @throws IllegalValueException
     *             if any of the raw values are invalid
     */
    private Task createTaskFromParameters(String preamble, ArgumentTokenizer tokenizer) throws IllegalValueException {
        if (preamble == null || preamble.trim().isEmpty()) {
            throw new IllegalValueException(AddCommand.MESSAGE_NO_DESC);
        }

        UniqueTagList tagList = new UniqueTagList();

        // create task with specified tags
        Optional<String> tagsParameterString = tokenizer.getValue(PREFIX_UNDER);
        if (tagsParameterString.isPresent()) {
            tagList = ParserUtil.parseTagsFromString(tagsParameterString.get());
        }

        Date startDate = null;
        Date endDate = null;
        int dateFormat = tokenizer.getDateFormat();
        switch (dateFormat) {
        case ArgumentTokenizer.DATE_NIL : break;
        case ArgumentTokenizer.DATE_BY : startDate = ParserUtil.parseDate(tokenizer.getValue(PREFIX_BY).get());
                                         endDate = ParserUtil.parseDate(tokenizer.getValue(PREFIX_BY).get()); break;
        case ArgumentTokenizer.DATE_FROM : startDate = ParserUtil.parseDate(tokenizer.getValue(PREFIX_FROM).get());
                                           endDate = ParserUtil.parseDate(tokenizer.getValue(PREFIX_TO).get());
                                           break;
        default : break;
        }

        Task toAdd = new Task(new Description(preamble), tagList, startDate, endDate);
        // set priority
        Optional<Priority> priority = ParserUtil.parsePriority(tokenizer.getValue(PREFIX_AS));
        if (priority.isPresent()) {
            toAdd.setPriority(priority.get());
        }
        return toAdd;
    }
}
