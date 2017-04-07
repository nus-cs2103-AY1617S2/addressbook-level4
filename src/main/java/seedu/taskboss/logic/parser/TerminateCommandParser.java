package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.TerminateCommand;

//@@author A0144904H
public class TerminateCommandParser {

    public Command parse(String args) {
        if ((args == null) || (args.isEmpty()) || (args.matches(".*[a-z].*"))) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));
        }

        Set<Integer> index;
        try {
            index = parseIndex(args);
        } catch (Exception e) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TerminateCommand.MESSAGE_USAGE));
        }

        return new TerminateCommand(index);
    }

    private Set<Integer> parseIndex(String indexList) throws Exception {
        Set<Integer> taskIndex = new HashSet<Integer>();
        String trimmedList = indexList.trim();
        String[] indexes = trimmedList.split("\\s+");

        try {
            for (String index : indexes) {
                taskIndex.add(Integer.parseInt(index));
            }
        } catch (NumberFormatException e) {
            throw new Exception();
        }

        return taskIndex;
    }
}
