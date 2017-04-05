package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_LOCATION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_OPTION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.Task;

//@@author A0163962X
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final Pattern ADD_COMMAND_PREAMBLE_FORMAT = Pattern.compile("(?<flag>[te])(?<name>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the
     * AddCommand and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_TAG, PREFIX_PRIORITY,
                PREFIX_LOCATION, PREFIX_DESCRIPTION, PREFIX_OPTION);
        argsTokenizer.tokenize(args);

        // extract flag and name from preamble
        if (!argsTokenizer.getPreamble().isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Matcher matcher = ADD_COMMAND_PREAMBLE_FORMAT.matcher(argsTokenizer.getPreamble().get());

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String flag = matcher.group("flag");
        final String name = matcher.group("name");

        // set all remaining properties
        final String date = ParserUtil.setValue(argsTokenizer, PREFIX_DATE, Deadline.NO_DEADLINE);
        final String priority = ParserUtil.setValue(argsTokenizer, PREFIX_PRIORITY, Priority.NO_PRIORITY);
        final String location = ParserUtil.setValue(argsTokenizer, PREFIX_LOCATION, Location.NO_LOCATION);
        final String description = ParserUtil.setValue(argsTokenizer, PREFIX_DESCRIPTION, Description.NO_DESCRIPTION);
        final Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));
        final String option = ParserUtil.setValue(argsTokenizer, PREFIX_OPTION, Parser.NO_OPTION);

        try {
            switch (flag) {
            case Event.EVENT_FLAG:
                List<Timeslot> timeslots = ParserUtil.parseAsTimeslots(date);
                AddCommand eventToAdd = new AddCommand(name, timeslots, location, description, tags);
                if (option.equals(Parser.FORCE_OPTION)) {
                    eventToAdd.force = true;
                }
                return eventToAdd;
            case Task.TASK_FLAG:
                return new AddCommand(name, date, priority, description, tags);
            default:
                return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
            }
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }

    }
}
