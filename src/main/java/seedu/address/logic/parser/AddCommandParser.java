package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private String args;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String arguments) {

        this.args = arguments;
        String tags[] = {};

        // find and remove tags
        String tagsString = getArgument(CliSyntax.TAGS);
        System.out.println("tags: " + tagsString);
        if (tagsString != null) {
            tags = tagsString.split("\\s+");
        }

        // find and remove starting time and deadline if the syntax is "<name>
        // from <starting time> to <deadline>"
        List<Date> startingTimeAndDeadline = getStartingTimeAndDeadline();
        if (startingTimeAndDeadline != null) {
            try {
                return new AddCommand(args.trim(),
                        startingTimeAndDeadline
                                .get(CliSyntax.INDEX_OF_DEADLINE),
                        startingTimeAndDeadline
                                .get(CliSyntax.INDEX_OF_STARTINGTIME),
                        tags);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                AddCommand.MESSAGE_USAGE));
            }
        }

        // find and remove starting time and deadline if the syntax is "<name>
        // due <deadline>"
        Date deadline = getDeadline();
        if (deadline != null) {
            try {
                return new AddCommand(args.trim(), deadline, tags);
            } catch (IllegalValueException e) {
                return new IncorrectCommand(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                AddCommand.MESSAGE_USAGE));
            }
        }
        try {
            return new AddCommand(args.trim(), tags);
        } catch (IllegalValueException e) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private String getArgument(String key) {
        String reverseString = new StringBuilder(args).reverse().toString();
        String reverseKey = new StringBuilder(key).reverse().toString();
        Pattern pattern = Pattern.compile(
                CliSyntax.END_OF_A_WORD + reverseKey + CliSyntax.END_OF_A_WORD);
        Matcher matcher = pattern.matcher(reverseString);
        if (matcher.find()) {
            String arg = args
                    .substring(reverseString.length() - matcher.start());
            args = args.substring(0, reverseString.length() - matcher.end());
            return arg.trim();
        } else {
            return null;
        }
    }

    private String[] getMoreThanOneArguments(String[] keys) {
        String reverseString = new StringBuilder(args).reverse().toString();
        String reverseKeysRegex = new StringBuilder(
                String.join(CliSyntax.END_OF_A_WORD_REVERSE, keys)).reverse()
                        .toString();
        Pattern pattern = Pattern.compile(reverseKeysRegex);
        Matcher matcher = pattern.matcher(reverseString);
        if (matcher.find()) {
            String[] arguments = new String[keys.length];
            for (int i = keys.length - 1; i >= 0; i--) {
                arguments[i] = new StringBuilder(getArgument(keys[i])).reverse()
                        .toString().trim();
            }
            return arguments;
        } else {
            return null;
        }
    }

    private List<Date> getStartingTimeAndDeadline() {
        String tmpArgs = args;
        String[] datesString = getMoreThanOneArguments(
                CliSyntax.STARTINGTIME_AND_DEADLINE);
        if (datesString == null) {
            args = tmpArgs;
            return null;
        }
        assert (datesString.length == 2);
        List<Date> dates = new ArrayList<Date>();
        for (int i = 0; i < 1; i++) {
            List<DateGroup> group = new PrettyTimeParser().parseSyntax(
                    dates.get(i) + (i == 0 ? CliSyntax.DEFAULT_STARTING_TIME
                            : CliSyntax.DEFAULT_DEADLINE));
            if (group == null || group.get(0).getPosition() != 0
                    || group.size() > 1
                    || group.get(0).getDates()
                            .get(CliSyntax.INDEX_OF_DEADLINE) != null
                    || group.get(0).getDates().get(CliSyntax.INDEX_OF_DEADLINE)
                            .after(group.get(0).getDates()
                                    .get(CliSyntax.INDEX_OF_STARTINGTIME))) {
                args = tmpArgs;
                return null;
            } else {
                dates.addAll(group.get(0).getDates());
            }
        }
        return dates;
    }

    private Date getDeadline() {
        String tmpArgs = args;
        String deadlineString = getArgument(CliSyntax.DEADLINE_ONLY);
        if (deadlineString == null) {
            args = tmpArgs;
            return null;
        }
        List<DateGroup> group = new PrettyTimeParser()
                .parseSyntax(deadlineString + CliSyntax.DEFAULT_DEADLINE);
        if (group == null || group.get(0).getPosition() != 0 || group.size() > 2
                || group.get(0).getDates().size() > 1) {
            args = tmpArgs;
            return null;
        } else {
            return group.get(0).getDates().get(0);
        }
    }
}
