package seedu.address.logic.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;

//@@author A0144422R
public class SeperableParser {

    protected static final int NUMBER_OF_ARGUMENTS_IN_STARTING_TIME_AND_DEADLINE = 2;
    String args;

    protected String[] getTags() {
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

    protected String getArgument(String key) {
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

    protected List<String> getMoreThanOneArguments(String regex,
            String[] captureGroups) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(args);
        if (matcher.matches()) {
            List<String> arguments = new ArrayList<String>();
            for (String captureGroup : captureGroups) {
                arguments.add(matcher.group(captureGroup).trim());
            }
            args = matcher.group("rest").trim();
            return arguments;
        } else {
            return null;
        }
    }

    protected List<Date> getStartingTimeAndDeadline() {
        String tmpArgs = args;
        String correctDateTime = "";
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
            List<DateGroup> group = new PrettyTimeParser().parseSyntax(
                    ParserUtil.correctDateFormat(datesString.get(i))

                            + (i == 1 ? CliSyntax.DEFAULT_STARTING_TIME
                                    : CliSyntax.DEFAULT_DEADLINE));

            if (group == null || group.size() > 2 || (!group.get(0).getText()
                    .equals(datesString.get(i))
                    && (!group.get(0).getText().equals(
                            ParserUtil.correctDateFormat(datesString.get(i))
                                    + (i == 1 ? CliSyntax.DEFAULT_STARTING_TIME
                                            : CliSyntax.DEFAULT_DEADLINE))
                            && !group.get(0).getText().equals(ParserUtil
                                    .correctDateFormat(datesString.get(i)))))) {
                args = tmpArgs;
                return null;
            } else {
                dates.addAll(group.get(0).getDates());

                correctDateTime = ((i == 1 ? "from " : "to ")
                        + group.get(0).getText() + " ") + correctDateTime;
            }
        }
        if (dates.get(CliSyntax.INDEX_OF_STARTINGTIME)
                .after(dates.get(CliSyntax.INDEX_OF_DEADLINE))) {
            args = tmpArgs;
            return null;
        }
        dates = new PrettyTimeParser().parse(correctDateTime);
        return dates;
    }

    protected Date getDeadline() {
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
