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
    private static final int NUMBER_OF_ARGUMENTS_IN_STARTING_TIME_AND_DEADLINE = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String arguments) {

        this.args = arguments;

        // find and remove tags
        String[] tags = getTags();

        // find and remove starting time and deadline if the syntax is "<name>
        // from <starting time> to <deadline>"
        List<Date> startingTimeAndDeadline = getStartingTimeAndDeadline();
        if (startingTimeAndDeadline != null) {
            try {
                return new AddCommand(args.trim(),
                        startingTimeAndDeadline.get(1),
                        startingTimeAndDeadline.get(0), tags);
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

    private String[] getTags() {
        Pattern pattern = Pattern.compile(CliSyntax.TAGS);
        Matcher matcher = pattern.matcher(args);
        ArrayList<String> tags = new ArrayList<String>();
        while (matcher.find()) {
            assert matcher.group().length() > 0;
            tags.add(matcher.group().trim().substring(1));
        }
        args = matcher.replaceAll("");
        return (tags.toArray(new String[0]));
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

    private List<String> getMoreThanOneArguments(String reverseRegex,
            String[] captureGroups) {
        String reverseString = new StringBuilder(args).reverse().toString();
        Pattern pattern = Pattern.compile(reverseRegex);
        Matcher matcher = pattern.matcher(reverseString);
        if (matcher.matches()) {
            List<String> arguments = new ArrayList<String>();
            for (String captureGroup : captureGroups) {
                arguments.add(new StringBuilder(matcher.group(captureGroup))
                        .reverse().toString().trim());
            }
            args = new StringBuilder(matcher.group("rest")).reverse().toString()
                    .trim();
            return arguments;
        } else {
            return null;
        }
    }

    private List<Date> getStartingTimeAndDeadline() {
        String tmpArgs = args;
        List<String> datesString = getMoreThanOneArguments(
                CliSyntax.STARTINGTIME_AND_DEADLINE_REVERSE_REGEX,
                CliSyntax.CAPTURE_GROUPS_OF_EVENT);
        if (datesString == null) {
            args = tmpArgs;
            return null;
        }
        assert datesString
                .size() == NUMBER_OF_ARGUMENTS_IN_STARTING_TIME_AND_DEADLINE;
        List<Date> dates = new ArrayList<Date>();
        for (int i = 0; i < NUMBER_OF_ARGUMENTS_IN_STARTING_TIME_AND_DEADLINE; i++) {
            List<DateGroup> group = new PrettyTimeParser()
                    .parseSyntax(ParserUtil.correctDateFormat(datesString.get(i)
                            + (i == 1 ? CliSyntax.DEFAULT_STARTING_TIME
                                    : CliSyntax.DEFAULT_DEADLINE)));
            if (group == null || group.size() > 2) {
                args = tmpArgs;
                return null;
            } else {
                dates.addAll(group.get(0).getDates());
            }
        }
        if (dates.get(CliSyntax.INDEX_OF_STARTINGTIME)
                .after(dates.get(CliSyntax.INDEX_OF_DEADLINE))) {
            args = tmpArgs;
            return null;
        }
        dates = new PrettyTimeParser().parse(tmpArgs);
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
                .parseSyntax(ParserUtil.correctDateFormat(
                        deadlineString + CliSyntax.DEFAULT_DEADLINE));
        if (group == null || group.get(0).getPosition() != 0 || group.size() > 2
                || group.get(0).getDates().size() > 1) {
            args = tmpArgs;
            return null;
        } else {
            return group.get(0).getDates().get(0);
        }
    }
}
