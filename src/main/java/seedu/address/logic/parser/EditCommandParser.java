package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;
import org.ocpsoft.prettytime.nlp.parse.DateGroup;
import org.ocpsoft.prettytime.shade.edu.emory.mathcs.backport.java.util.Arrays;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTaskDescriptor;
import seedu.address.logic.commands.IncorrectCommand;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.Name;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditCommandParser {

    private String args;
    private static final int NUMBER_OF_ARGUMENTS_IN_STARTING_TIME_AND_DEADLINE = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditCommand and returns an EditCommand object for execution.
     */
    public Command parse(String args, LogicManager logic) {
        assert args != null;
        this.args = args.trim();
        String[] indexAndArguments = this.args.split("\\s+", 2);
        if (indexAndArguments.length < 2) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }

        EditTaskDescriptor editTaskDescriptor = new EditTaskDescriptor();
        Optional<String> index = ParserUtil.parseIndex(indexAndArguments[0]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        this.args = indexAndArguments[1];
        String tags[] = getTags();
        try {
            editTaskDescriptor.setTags(parseTagsForEdit(Arrays.asList(tags)));
        } catch (IllegalValueException e1) {
            return new IncorrectCommand(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        // find and remove tags

        // find and remove starting time and deadline if the syntax is "<name>
        // from <starting time> to <deadline>"
        List<Date> startingTimeAndDeadline = getStartingTimeAndDeadline();
        if (startingTimeAndDeadline != null) {
            editTaskDescriptor.setStartingTime(startingTimeAndDeadline
                    .get(CliSyntax.INDEX_OF_STARTINGTIME));
            editTaskDescriptor.setDeadline(
                    startingTimeAndDeadline.get(CliSyntax.INDEX_OF_DEADLINE));
        } else {
            // find and remove starting time and deadline if the syntax is
            // "<name> due <deadline>"
            Date deadline = getDeadline();
            if (deadline != null) {
                editTaskDescriptor.setDeadline(deadline);
            }
        }
        if (!this.args.trim().equals("")) {
            try {
                editTaskDescriptor.setName(new Name(this.args.trim()));
            } catch (IllegalValueException e) {
                return new IncorrectCommand(e.getMessage());
            }
        }
        return new EditCommand(logic.parseUIIndex(index.get()),
                editTaskDescriptor);
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

    private List<String> getMoreThanOneArguments(String reverseRegex) {
        String reverseString = new StringBuilder(args).reverse().toString();
        Pattern pattern = Pattern.compile(reverseRegex);
        Matcher matcher = pattern.matcher(reverseString);
        if (matcher.matches()) {
            int length = matcher.groupCount();
            List<String> arguments = new ArrayList<String>();
            for (int i = length - 1; i >= 0; i--) {
                arguments.add(new StringBuilder(matcher.group(i)).reverse()
                        .toString());
            }
            args = matcher.group(length);
            return arguments;
        } else {
            return null;
        }
    }

    private List<Date> getStartingTimeAndDeadline() {
        String tmpArgs = args;
        List<String> datesString = getMoreThanOneArguments(
                CliSyntax.STARTINGTIME_AND_DEADLINE_REVERSE_REGEX);
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
                            + (i == 0 ? CliSyntax.DEFAULT_STARTING_TIME
                                    : CliSyntax.DEFAULT_DEADLINE)));
            if (group == null || group.get(0).getPosition() != 0
                    || group.size() > 2) {
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

    /**
     * Parses {@code Collection<String> tags} into an
     * {@code Optional<UniqueTagList>} if {@code tags} is non-empty. If
     * {@code tags} contain only one element which is an empty string, it will
     * be parsed into a {@code Optional<UniqueTagList>} containing zero tags.
     */
    private UniqueTagList parseTagsForEdit(Collection<String> tags)
            throws IllegalValueException {
        assert tags != null;

        if (tags.isEmpty()) {
            return null;
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("")
                ? Collections.emptySet() : tags;
        return ParserUtil.parseTags(tagSet);
    }

}
