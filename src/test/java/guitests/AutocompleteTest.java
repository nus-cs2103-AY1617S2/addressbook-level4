package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class AutocompleteTest extends TaskManagerGuiTest {

    private static String[] COMMANDS_ONE_TAB = { "add", "delete", "edit", "mark", "schedule", "list",
            "help", "find", "undo", "redo", "clear" };
    private static String[] COMMANDS_TWO_TAB = { "unmark" };

    @Test
    public void testAutocompleteCommands() {
        for (String command : COMMANDS_ONE_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 1);
        }

        for (String command : COMMANDS_TWO_TAB) {
            assertCommandAutocompleted(String.valueOf(command.charAt(0)), command, 2);
        }
    }

    private void assertCommandAutocompleted(String input, String expected, int times) {
        commandBox.autocompleteInput(input, times);
        assertEquals(commandBox.getCommandInput(), expected);
    }

}
