package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_LOCATION;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.Priority;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser {

    private static final Pattern ADD_COMMAND_FORMAT = Pattern.compile("(?<flag>[te])(?<name>.+)");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE, PREFIX_TAG, PREFIX_PRIORITY, PREFIX_LOCATION, PREFIX_DESCRIPTION);
        argsTokenizer.tokenize(args);

        Matcher matcher;

        try {
            matcher = ADD_COMMAND_FORMAT.matcher(argsTokenizer.getPreamble().get());
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        if (!matcher.matches()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        final String flag = matcher.group("flag");
        final String name = matcher.group("name");

        String date = Deadline.NO_DEADLINE;
        Optional<String> rawDate = argsTokenizer.getValue(PREFIX_DATE);
        if (rawDate.isPresent()) {
            date = rawDate.get();
        }

        String description = Description.NO_DESCRIPTION;
        Optional<String> rawDescription = argsTokenizer.getValue(PREFIX_DESCRIPTION);
        if (rawDescription.isPresent()) {
            description = rawDescription.get();
        }

        Set<String> tags = ParserUtil.toSet(argsTokenizer.getAllValues(PREFIX_TAG));

        switch (flag) {
        case AddCommand.EVENT_FLAG:
            String location = Location.NO_LOCATION;
            Optional<String> rawLocation = argsTokenizer.getValue(PREFIX_LOCATION);
            if (rawLocation.isPresent()) {
                location = rawLocation.get();
            }

            return addEvent(name, date, location, description, tags);
        case AddCommand.TASK_FLAG:
            String priority = Priority.NO_PRIORITY;
            Optional<String> rawPriority = argsTokenizer.getValue(PREFIX_PRIORITY);
            if (rawPriority.isPresent()) {
                priority = rawPriority.get();
            }

            return addTask(name, date, priority, description, tags);
        default:
            //TODO fix messages
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

    }

    private Command addTask(String name, String deadline, String priority, String description, Set<String> tags) {
        try {
            return new AddCommand(
                    name,
                    deadline,
                    priority,
                    description,
                    tags);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private Command addEvent(String name, String timesToParse, String location, String description, Set<String> tags) {

        try {

            if (timesToParse.equals(Deadline.NO_DEADLINE)) {
                throw new IllegalValueException("No deadline provided");
            }

            String[] timeslotsAsStrings = timesToParse.split("\\s+or\\s+");
            List<Timeslot> timeslots = new ArrayList<>();
            for (String t: timeslotsAsStrings) {
                String[] dates = t.split("\\s+to\\s+");
                timeslots.add(new Timeslot(dates[0], dates[1]));
            }

            return new AddCommand(
                    name,
                    timeslots,
                    location,
                    description,
                    tags);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

}
