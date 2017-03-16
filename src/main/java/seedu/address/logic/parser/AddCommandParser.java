package seedu.address.logic.parser;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.IncorrectCommand;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    static final int LEAST_DATES_FOR_DEADLINE_ONLY_TASK = 1;
    static final int LEAST_DATES_FOR_DEADLINE_AND_STARTING_TIME_TASK = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {

        Matcher matcher;
        String tags[] = {};

        Pattern pattern = Pattern.compile(
                CliSyntax.WILDCARD + CliSyntax.WITH_TAGS + CliSyntax.WILDCARD);
        matcher = pattern.matcher(args);
        if (matcher.matches()) {
            matcher = Pattern.compile(CliSyntax.WITH_TAGS).matcher(args);
            int lastOccuranceEnd = 0;
            int lastOccuranceStart = 0;
            while (matcher.find()) {
                lastOccuranceEnd = matcher.end();
                lastOccuranceStart = matcher.start();
            }
            tags = args.substring(lastOccuranceEnd + 1, args.length())
                    .split(" ");
            args = args.substring(0, lastOccuranceStart);
        }

        // args do not have tags now
        pattern = Pattern.compile(
                CliSyntax.WILDCARD + CliSyntax.WITH_DEADLINE_AND_STARTING_TIME
                        + CliSyntax.WILDCARD);
        matcher = pattern.matcher(args);
        if (matcher.matches()) {
            matcher = Pattern.compile(CliSyntax.WITH_DEADLINE).matcher(args);
            int lastOccuranceEnd = args.length() - 1;
            int lastOccuranceStart = 0;
            while (matcher.find()) {
                lastOccuranceStart = matcher.start();
                lastOccuranceEnd = matcher.end();
            }
            List<Date> dates = new PrettyTimeParser()
                    .parse(args.substring(lastOccuranceEnd + 1, args.length()));
            System.out.println(dates.get(0));
            args = args.substring(0, lastOccuranceStart);
            matcher = Pattern.compile(CliSyntax.WITH_STARTING_TIME)
                    .matcher(args);
            lastOccuranceEnd = args.length() - 1;
            lastOccuranceStart = 0;
            while (matcher.find()) {
                lastOccuranceStart = matcher.start();
                lastOccuranceEnd = matcher.end();
            }
            dates.addAll(new PrettyTimeParser().parse(
                    args.substring(lastOccuranceEnd + 1, args.length())));
            System.out.println(dates.get(1));
            if (dates
                    .size() >= LEAST_DATES_FOR_DEADLINE_AND_STARTING_TIME_TASK) {
                String taskName = args.substring(0, lastOccuranceStart).trim();
                try {
                    return new AddCommand(taskName, dates.get(1), dates.get(0),
                            tags);
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            }
        }

        pattern = Pattern.compile(CliSyntax.WILDCARD
                + CliSyntax.WITH_DEADLINE_ONLY + CliSyntax.WILDCARD);
        matcher = pattern.matcher(args);
        if (matcher.matches()) {
            matcher = Pattern.compile(CliSyntax.WITH_DEADLINE_ONLY)
                    .matcher(args);
            int lastOccuranceEnd = args.length() - 1;
            int lastOccuranceStart = 0;
            while (matcher.find()) {
                lastOccuranceStart = matcher.start();
                lastOccuranceEnd = matcher.end();
            }
            List<Date> dates = new PrettyTimeParser()
                    .parse(args.substring(lastOccuranceEnd + 1, args.length()));
            args = args.substring(0, lastOccuranceStart);
            if (dates.size() >= LEAST_DATES_FOR_DEADLINE_ONLY_TASK) {
                matcher = Pattern.compile(CliSyntax.WITH_DEADLINE_ONLY)
                        .matcher(args);
                int lastOccurance = args.length() - 1;
                while (matcher.find()) {
                    lastOccurance = matcher.start();
                }
                String taskName = args.substring(0, lastOccurance).trim();
                try {
                    return new AddCommand(taskName, dates.get(0), tags);
                } catch (IllegalValueException ive) {
                    return new IncorrectCommand(ive.getMessage());
                }
            }
        }

        try {
            return new AddCommand(args.trim(), tags);
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }

}
