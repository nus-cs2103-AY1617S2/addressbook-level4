//@@author A0131125Y
package guitests;

import static junit.framework.TestCase.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import javafx.scene.input.KeyCode;

/**
 * Gui tests for command autocomplete
 */
public class CommandAutoCompleteTest extends ToLuistGuiTest {

    @Test
    public void autoComplete_sameThingTwiceFromMultipleOptions() {
        commandBox.focus();
        mainGui.press(KeyCode.S);
        List<String> expected1 = Arrays.asList(new String[] { "save", "sort", "switch" });
        assertEquals(expected1, commandAutoCompleteView.getSuggestions());

        mainGui.press(KeyCode.TAB);
        mainGui.press(KeyCode.ENTER);
        assertEquals("save", commandBox.getCommandInput());

        for (int i = 0; i < 3; i++) {
            mainGui.press(KeyCode.BACK_SPACE);
        }
        mainGui.press(KeyCode.TAB);
        mainGui.press(KeyCode.ENTER);
        assertEquals("save", commandBox.getCommandInput());
    }
}
