
package guitests;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import javafx.scene.input.KeyCode;

//@@author A0147980U
public class ContentAssistTests extends DoistGUITest {
    GuiRobot bot = new GuiRobot();
    @Test
    public void testCorrectSuggestionsDisplayed() {
        correctSuggestionsDisplayedWhenEnter("f", "f");
    }

    @Test
    public void testCorrectSuggestionsDisplayedWithCompleteCommandWord() {
        correctSuggestionsDisplayedWhenEnter("fin", "fin");
    }

    @Test
    public void testCorrectSingleSuggestionsDisplayedWithCompleteCommandWord() {
        correctSuggestionsDisplayedWhenEnter("find", "find");
    }

    @Test
    public void testNoSuggestionsDisplayedBecuaseOfSpace() {
        correctSuggestionsDisplayedWhenEnter("find ", "find");
    }

    @Test
    public void testNoSuggestionsDisplayedBecuaseOfNoMatch() {
        correctSuggestionsDisplayedWhenEnter("no suggesion will match me", "me");
    }

    @Test
    public void testSuggestionOnCorrectParameterKey() {
        correctSuggestionsDisplayedWhenEnter("do task \\fro", "\\fro");
    }

    @Test
    public void testSuggestionOnIncorrectParameterKey() {
        correctSuggestionsDisplayedWhenEnter("do task \\incorrect_key", "\\incorrect_key");
    }

    @Test
    public void testCompleteByTappingTab() {
        String input = "view_alia";
        commandBox.enterCommand(input);
        bot.type(KeyCode.TAB);
        assertTrue("view_alias".equals(commandBox.getCommandInput()));
    }

    @Test
    public void testCompleteKeyByTappingTab() {
        String input = "do task \\unde";
        commandBox.enterCommand(input);
        bot.type(KeyCode.TAB);
        assertTrue("do task \\under".equals(commandBox.getCommandInput()));
    }

    private void correctSuggestionsDisplayedWhenEnter(String input, String lastWord) {
        commandBox.enterCommand(input);
        assertCorrectSuggestions(lastWord);
    }
}





