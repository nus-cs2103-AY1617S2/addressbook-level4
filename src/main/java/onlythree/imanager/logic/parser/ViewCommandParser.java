package onlythree.imanager.logic.parser;

import static onlythree.imanager.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import onlythree.imanager.logic.commands.Command;
import onlythree.imanager.logic.commands.IncorrectCommand;
import onlythree.imanager.logic.commands.ViewCommand;

//@@author A0135998H
/**
 * Parses input arguments and creates a new ViewCommand object
 */
public class ViewCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns an ViewCommand object for execution.
     */
    public Command parse(String args) {
        String typeOfList = args.trim().toLowerCase();

        if (!ViewCommand.isValidCommand(typeOfList)) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        return new ViewCommand(typeOfList);
    }

}

