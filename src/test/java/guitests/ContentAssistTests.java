package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;

//@@author A0147980U
public class ContentAssistTests extends DoistGUITest {
    @Test
    public void testCorrectSuggestionsDisplayed() {
        String input = "f";
        commandBox.enterCommand(input);
        assertCorrectSuggestions(input);
    }

    @Test
    public void testCorrectSuggestionsDisplayedWithCompleteCommandWord() {
        String input = "fin";
        commandBox.enterCommand(input);
        assertCorrectSuggestions(input);
    }

    @Test
    public void testCorrectSingleSuggestionsDisplayedWithCompleteCommandWord() {
        String input = "find";
        commandBox.enterCommand(input);
        assertCorrectSuggestions(input);
    }

    @Test
    public void testNoSuggestionsDisplayedBecuaseOfSpace() {
        String input = "find ";
        commandBox.enterCommand(input);
        assertCorrectSuggestions(input);
    }

    @Test
    public void testNoSuggestionsDisplayedBecuaseOfNoMatch() {
        String input = "no suggesion will match me";
        commandBox.enterCommand(input);
        assertCorrectSuggestions(input);
    }

    @Test
    public void testCompleteByTappingTap() {
        String input = "reset_alia";
        commandBox.enterCommand(input);
        (new GuiRobot()).press(KeyCode.TAB);
        assertTrue("reset_alias".equals(commandBox.getCommandInput()));
    }

}
