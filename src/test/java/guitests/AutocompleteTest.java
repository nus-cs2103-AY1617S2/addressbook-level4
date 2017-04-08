package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author A0124368A
public class AutocompleteTest extends TaskManagerGuiTest {

    private static final String[] COMMANDS_ONE_TAB = { "add", "delete", "edit", "mark", "save", "list",
        "help", "find", "undo", "redo", "clear" };
    private static final String[] COMMANDS_TWO_TAB = { "schedule", "exit" };
    private static final String[] COMMANDS_THREE_TAB = { "sync" };
    private static final String[] COMMANDS_FOUR_TAB = { "sort" };

    @Test
    public void testAutocompleteEmptyCommand() {
        // Empty command should not autocomplete
        assertCommandAutocompleted("", "", 1);
    }

    @Test
    public void testInvalidCommand() {
        // Invalid commands should not autocomplete
        assertCommandAutocompleted("invalid", "invalid", 1);
    }

    @Test
    public void testAutocompleteCommands() {
        for (String command : COMMANDS_ONE_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 1);
        }

        for (String command : COMMANDS_TWO_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 2);
        }

        for (String command : COMMANDS_THREE_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 3);
        }

        for (String command : COMMANDS_FOUR_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 4);
        }
    }

    private void assertCommandAutocompleted(String input, String expected, int times) {
        commandBox.autocompleteInput(input, times);
        assertEquals(commandBox.getCommandInput(), expected);
    }

}
//@@author
