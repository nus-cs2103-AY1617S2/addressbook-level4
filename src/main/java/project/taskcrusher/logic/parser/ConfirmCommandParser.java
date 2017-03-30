package project.taskcrusher.logic.parser;

import static project.taskcrusher.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import project.taskcrusher.logic.commands.Command;
import project.taskcrusher.logic.commands.ConfirmCommand;
import project.taskcrusher.logic.commands.IncorrectCommand;
import project.taskcrusher.model.event.Event;

//@@author A0163962X
public class ConfirmCommandParser {

    public static final String FLAG_EVENT_VALIDATION_REGEX = "[" + Event.EVENT_FLAG + "]";

    /**
     * Parses the given {@code String} of arguments in the context of the
     * DeleteCommand and returns an DeleteCommand object for execution.
     */
    public Command parse(String args) {

        String[] preamble = args.trim().split("\\s+");

        if (preamble.length != 3) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        } else if (!preamble[0].matches(ConfirmCommandParser.FLAG_EVENT_VALIDATION_REGEX)) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        } else if (!preamble[1].matches("\\d+") && !preamble[2].matches("\\d+")) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }

        Optional<Integer> index = ParserUtil.parseIndex(preamble[1]);
        if (!index.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }

        Optional<Integer> slot = ParserUtil.parseIndex(preamble[2]);
        if (!slot.isPresent()) {
            return new IncorrectCommand(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ConfirmCommand.MESSAGE_USAGE));
        }

        return new ConfirmCommand(index.get(), slot.get());
    }

}
