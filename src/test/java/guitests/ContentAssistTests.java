
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
    public void testCompleteCommandWordByTappingTab() {
        String input = "view_alia";
        typeTabAfterInput(input);
        assertTrue("view_alias".equals(commandBox.getCommandInput()));
    }

    @Test
    public void testCompleteKeyByTappingTab() {
        String input = "do task \\unde";
        typeTabAfterInput(input);
        assertTrue("do task \\under".equals(commandBox.getCommandInput()));
    }

    @Test
    public void testCompleteUnexistedCommandword() {
        String input = "z";  // there is no command word starting with the letter 'z'
        typeTabAfterInput(input);
        assertTrue("z".equals(commandBox.getCommandInput()));
    }

    @Test
    public void testCompleteUnexistedKey() {
        String input = "do test \\z";  // there is no parameter key starting with the letter 'z'
        typeTabAfterInput(input);
        assertTrue("do test \\z".equals(commandBox.getCommandInput()));
    }

    @Test
    public void testCompleteSingleBackslash() {
        String input = "do test \\";
        typeTabAfterInput(input);
        String content = commandBox.getCommandInput();
        assertTrue(content.charAt(content.length() - 1) != '\\');
    }

    private void typeTabAfterInput(String input) {
        commandBox.enterCommand(input);
        bot.type(KeyCode.TAB);
    }
    private void correctSuggestionsDisplayedWhenEnter(String input, String lastWord) {
        commandBox.enterCommand(input);
        assertCorrectSuggestions(lastWord);
    }
}





