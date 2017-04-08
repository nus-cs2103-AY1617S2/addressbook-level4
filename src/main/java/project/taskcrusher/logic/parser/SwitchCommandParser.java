package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_DATE;
import static project.taskcrusher.logic.parser.CliSyntax.PREFIX_OPTION;

import java.util.Optional;

import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.logic.commands.SwitchCommand;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.Task;

//@@author A0163962X
public class SwitchCommandParser {

    public static final String FLAG_EVENT_VALIDATION_REGEX = "[" + Task.TASK_FLAG + Event.EVENT_FLAG + "]";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        ArgumentTokenizer argsTokenizer = new ArgumentTokenizer(PREFIX_DATE, PREFIX_OPTION);
        argsTokenizer.tokenize(args);

        if (!argsTokenizer.getPreamble().isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }

        String[] preamble = argsTokenizer.getPreamble().get().trim().split("\\s+");

        if (preamble.length != 2) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        } else if (!preamble[0].matches(SwitchCommandParser.FLAG_EVENT_VALIDATION_REGEX)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        } else if (!preamble[1].matches("\\d+")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = ParserUtil.parseIndex(preamble[1]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchCommand.MESSAGE_USAGE));
        }

        final String date = ParserUtil.setValue(argsTokenizer, PREFIX_DATE, Deadline.NO_DEADLINE);
        final String option = ParserUtil.setValue(argsTokenizer, PREFIX_OPTION, Parser.NO_OPTION);

        SwitchCommand switched = new SwitchCommand(preamble[0], index.get(), date);

        if (option.equals(Parser.FORCE_OPTION)) {
            switched.force = true;
        }

        return switched;
    }

}
