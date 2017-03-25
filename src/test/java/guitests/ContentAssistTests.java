package guitests;

import org.junit.Test;

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
}
