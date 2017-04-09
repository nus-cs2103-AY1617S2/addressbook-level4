//@@author A0150120H
package seedu.tache.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.logic.commands.AddCommand;
import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.IncorrectCommand;

public class AddCommandParserTest {

    @Test
    public void parse_emptyArgument_failure() {
        Command invalidFormat = new AddCommandParser().parse("");
        assertIncorrectCommandType(invalidFormat);
    }

    @Test
    public void parse_correctArgument_success() {
        Command correctArgument = new AddCommandParser().parse("study form 10am to 10pm everday");
        assertAddCommandType(correctArgument);
    }

    private void assertIncorrectCommandType(Command command) {
        boolean isSameType = false;
        isSameType = command instanceof IncorrectCommand;
        assertTrue(isSameType);
    }

    private void assertAddCommandType(Command command) {
        boolean isSameType = false;
        isSameType = command instanceof AddCommand;
        assertTrue(isSameType);
    }
}
