//@@author A0139925U
package seedu.tache.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.tache.logic.commands.Command;
import seedu.tache.logic.commands.EditCommand;
import seedu.tache.logic.commands.IncorrectCommand;

public class EditCommandParserTest {

    @Test
    public void parse_invalidStructuredArguments_failure() {
        Command invalidStructuredArgs = new EditCommandParser().parse("test;");
        assertIncorrectCommandType(invalidStructuredArgs);

        Command invalidStructuredArgsWithNoParameter = new EditCommandParser().parse("1;");
        assertIncorrectCommandType(invalidStructuredArgsWithNoParameter);

        Command invalidStructuredArgsWithInvalidParameter = new EditCommandParser().parse("1; invalid today;");
        assertIncorrectCommandType(invalidStructuredArgsWithInvalidParameter);

        Command invalidStructuredArgsWithNoIndex = new EditCommandParser().parse("ssasdt asadasdas;");
        assertIncorrectCommandType(invalidStructuredArgsWithNoIndex);
    }

    @Test
    public void parse_validStructuredArguments_success() {
        Command validStructuredArgsSingleParameter = new EditCommandParser().parse("1; ed today;");
        assertEditCommandType(validStructuredArgsSingleParameter);

        Command validStructuredArgsMultiParameter = new EditCommandParser().parse("1; ed today; st 7pm;");
        assertEditCommandType(validStructuredArgsMultiParameter);
    }

    @Test
    public void parse_invalidNaturalArguments_failure() {
        Command invalidStructuredArgs = new EditCommandParser().parse("test");
        assertIncorrectCommandType(invalidStructuredArgs);

        Command invalidStructuredArgsWithNoParameter = new EditCommandParser().parse("1");
        assertIncorrectCommandType(invalidStructuredArgsWithNoParameter);

        Command invalidStructuredArgsWithInvalidParameter = new EditCommandParser().parse("1 invalid to today");
        assertIncorrectCommandType(invalidStructuredArgsWithInvalidParameter);

        Command invalidStructuredArgsWithoutTo = new EditCommandParser().parse("1 change ssasdt asadasdas;");
        assertIncorrectCommandType(invalidStructuredArgsWithoutTo);

        Command invalidStructuredArgsWithNoIndex = new EditCommandParser().parse("change ssasdt asadasdas;");
        assertIncorrectCommandType(invalidStructuredArgsWithoutTo);
    }

    @Test
    public void parse_validNaturalArguments_success() {
        Command validNaturalArgsSingleParameter = new EditCommandParser().parse("1 change ed to today");
        assertEditCommandType(validNaturalArgsSingleParameter);

        Command validNaturalArgsMultiParameter = new EditCommandParser().parse("1 change tag to one two three");
        assertEditCommandType(validNaturalArgsMultiParameter);
    }

    private void assertIncorrectCommandType(Command command) {
        boolean isSameType = false;
        isSameType = command instanceof IncorrectCommand;
        assertTrue(isSameType);
    }

    private void assertEditCommandType(Command command) {
        boolean isSameType = false;
        isSameType = command instanceof EditCommand;
        assertTrue(isSameType);
    }

}
