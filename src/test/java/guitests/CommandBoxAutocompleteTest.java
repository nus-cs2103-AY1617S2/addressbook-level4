package guitests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

//@@author A0140042A
/**
 * A test class that tests auto complete function on the GUI text field itself
 */
public class CommandBoxAutocompleteTest extends TaskManagerGuiTest {

    @Test
    public void autocomplete_SingleSuggestion() {
        //Single suggestion
        commandBox.enterCommand("he");
        moveCursorRight("he".length());
        commandBox.pressTab();
        assertEquals("help ", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleSuggestions() {
        //Multiple suggestions
        commandBox.enterCommand("ex");
        moveCursorRight("ex".length());
        commandBox.pressTab();
        assertEquals("ex", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_SingleSuggestionAfterWordsPartial() {
        //Single suggestions with words (partial)
        commandBox.enterCommand("randomString ed");
        moveCursorRight("randomString ed".length());
        commandBox.pressTab();
        assertEquals("randomString edit", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_SingleSuggestionAfterWords() {
        //Single suggestions with words
        commandBox.enterCommand("randomString editl");
        moveCursorRight("randomString edi".length());
        commandBox.pressTab();
        assertEquals("randomString editlabel ", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleSuggestionBeforeWords() {
        //Auto complete keyword before multiple suggestions
        commandBox.enterCommand("ed randomString");
        moveCursorRight(1);
        commandBox.pressTab();
        assertEquals("edit randomString", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_SingleSuggestionBeforeWords() {
        //Auto complete keyword before single suggestion
        commandBox.enterCommand("editl randomString");
        moveCursorRight(1);
        commandBox.pressTab();
        assertEquals("editlabel randomString", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_NonExistentString() {
        //Nonexistent string
        commandBox.enterCommand("randomString nonExistentStri");
        moveCursorRight("randomString nonExis".length());
        commandBox.pressTab();
        assertEquals("randomString nonExistentStri", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_EmptyText() {
        //Empty text
        commandBox.enterCommand("");
        commandBox.pressTab();
        assertEquals("", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleTabsAutocomplete() {
        //pressing tab multiple times should not affect the auto completion
        commandBox.enterCommand("he");
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        moveCursorRight("he".length());
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        assertEquals("help ", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleTabsAutocompleteEnd() {
        //pressing tab multiple times should not affect the auto completion
        commandBox.enterCommand("randomString ed");
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        moveCursorRight("randomString ed".length());
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        assertEquals("randomString edit", commandBox.getCommandInput());
    }


    /**
     * Moves the cursor to the right {@value count} times
     * @param count - number of times to move right
     */
    public void moveCursorRight(int count) {
        for (int i = 0; i < count; i++) {
            commandBox.pressRight();
        }
    }
}
