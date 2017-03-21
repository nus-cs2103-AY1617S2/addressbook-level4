package guitests;

import static seedu.taskboss.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.Test;

import seedu.taskboss.logic.commands.RenameCategoryCommand;
import seedu.taskboss.logic.parser.RenameCategoryCommandParser;

public class RenameCategoryCommandTest extends TaskBossGuiTest {

    @Test
    public void rename_unsuccessful() {
        //old category name == new category name
        commandBox.runCommand("name friends friends");
        assertResultMessage(RenameCategoryCommandParser.ERROR_SAME_FIELDS);

        //invalid number of fields
        commandBox.runCommand("name friends bestfriends forever");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RenameCategoryCommand.MESSAGE_USAGE));

        //category name with a single non-alphanumerical character
        commandBox.runCommand("name owesMoney myMoney!");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameCategoryCommandParser.ERROR_NON_ALPHANUMERIC));

        //category name with all non-alphanumerical characters
        commandBox.runCommand("name owesMoney !!!");
        assertResultMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RenameCategoryCommandParser.ERROR_NON_ALPHANUMERIC));

        //specified category to be re-named does not exist
        commandBox.runCommand("name superman batman");
        assertResultMessage("[superman] " + RenameCategoryCommand.MESSAGE_DOES_NOT_EXIST_CATEGORY);
    }
}
