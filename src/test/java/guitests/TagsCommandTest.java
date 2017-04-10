package guitests;

import org.junit.Test;

import seedu.onetwodo.logic.commands.ClearCommand;
import seedu.onetwodo.logic.commands.TagsCommand;

public class TagsCommandTest extends ToDoListGuiTest {

    @Test
    public void showTags_nonEmptyList() {
        commandBox.runCommand(TagsCommand.COMMAND_WORD);
        assertResultMessage(TagsCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void showTags_emptyList() {
        commandBox.runCommand(ClearCommand.COMMAND_WORD);
        commandBox.runCommand(TagsCommand.COMMAND_WORD);
        assertResultMessage(TagsCommand.MESSAGE_SUCCESS);
    }
}
