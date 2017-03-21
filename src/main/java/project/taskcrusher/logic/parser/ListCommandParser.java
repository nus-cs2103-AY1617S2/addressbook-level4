package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.NoSuchElementException;
import java.util.Optional;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.logic.commands.AddCommand;
import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.ListCommand;
import project.taskcrusher.model.task.Deadline;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class ListCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     */
    public Command parse(String args) {
        ArgumentTokenizer argsTokenizer =
                new ArgumentTokenizer(PREFIX_DATE);
        argsTokenizer.tokenize(args);

        String flag = ListCommand.NO_FLAG;
        Optional<String> rawFlag = argsTokenizer.getPreamble();
        if (rawFlag.isPresent()) {
            flag = rawFlag.get();
        }

        //TODO Modify below for events; will need PrettyTimeParser
        String deadline = Deadline.NO_DEADLINE;
        Optional<String> rawDeadline = argsTokenizer.getValue(PREFIX_DATE);
        if (rawDeadline.isPresent()) {
            deadline = rawDeadline.get();
        }

        switch (flag) {
        case ListCommand.NO_FLAG:
            return listTask(deadline, false, false);
        case ListCommand.TASK_FLAG:
            return listTask(deadline, true, false);
        case ListCommand.EVENT_FLAG:
            //TODO when events supported
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        case ListCommand.OVERDUE_FLAG:
            //TODO return listTask(deadline, true, true);
        case ListCommand.COMPLETE_FLAG:
            //TODO when complete supported
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        default:
            //TODO fix messages
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
        }

    }

    private Command listTask(String deadline, boolean listTasksOnly, boolean listEventsOnly) {
        try {
            return new ListCommand(deadline, listTasksOnly, listEventsOnly);
        } catch (NoSuchElementException nsee) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        } catch (IllegalValueException ive) {
            return new IncorrectCommand(ive.getMessage());
        }
    }

    private void listEvent() {
        //TODO when events are supported
    }

}
