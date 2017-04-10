//@@author A0148037E
package seedu.geekeep.logic.parser;

import seedu.geekeep.logic.commands.Command;
import seedu.geekeep.logic.commands.IncorrectCommand;
import seedu.geekeep.logic.commands.ListCommand;

public class ListCommandParser {
    public Command parse(String args) {
        if (!args.isEmpty()) {
            return new IncorrectCommand(ListCommand.MESSAGE_USAGE);
        } else {
            return new ListCommand();
        }
    }
}
