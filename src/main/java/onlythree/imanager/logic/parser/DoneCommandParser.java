package onlythree.imanager.logic.parser;

import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Optional;

import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.DoneCommand;
import onlythree.imanager.logic.commands.IncorrectCommand;

//@@author A0135998H
/**
 * Parses input arguments and creates a new SelectCommand object
 */
public class DoneCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns an DoneCommand object for execution.
     */
    public Command parse(String args) {
        Optional<Integer> index = ParserUtil.parseIndex(args);
        if (!index.isPresent()) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }

        return new DoneCommand(index.get());
    }

}
