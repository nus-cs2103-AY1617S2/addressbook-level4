package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;

//@@author A0147980U
public class ContentAssistTests extends DoistGUITest {
    GuiRobot bot = new GuiRobot();
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
    public void testSuggestionOnCorrectParameterKey() {
        String input = "do task \\fro";
        commandBox.enterCommand(input);
        assertCorrectSuggestions("\\fro");
    }

    @Test
    public void testSuggestionOnIncorrectParameterKey() {
        String input = "do task \\incorrect_key";
        commandBox.enterCommand(input);
        assertCorrectSuggestions(input);
    }

    @Test
    public void testCompleteByTappingTap() {
        String input = "view_alia";
        commandBox.enterCommand(input);
        bot.type(KeyCode.TAB);
        assertTrue("view_alias".equals(commandBox.getCommandInput()));
    }

    @Test
    public void testCompleteKeyByTappingTap() {
        String input = "do task \\unde";
        commandBox.enterCommand(input);
        bot.type(KeyCode.TAB);
        assertTrue("do task \\under".equals(commandBox.getCommandInput()));
    }
}





