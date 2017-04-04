package seedu.taskboss.logic.parser;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
import java.util.Set;

import seedu.taskboss.logic.commands.Command;
import seedu.taskboss.logic.commands.IncorrectCommand;
import seedu.taskboss.logic.commands.MarkDoneCommand;

//@@author A0144904H
public class MarkDoneCommandParser {

    public Command parse(String args) {
        if ((args == null) || (args.isEmpty()) || (args.matches(".*[a-z].*"))) {
            return new IncorrectCommand(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkDoneCommand.MESSAGE_USAGE));
        }

        Set<Integer> index = parseIndex(args);

        return new MarkDoneCommand(index);
    }

    private Set<Integer> parseIndex(String indexList) {
        Set<Integer> taskIndex = new HashSet<Integer>();
        String trimmedList = indexList.trim();
        String[] indexes = trimmedList.split("\\s+");

        for (String index : indexes) {
            taskIndex.add(Integer.parseInt(index));
        }

        return taskIndex;
    }
}
