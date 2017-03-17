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
        commandBox.enterCommand("HE");
        moveCursorRight("HE".length());
        commandBox.pressTab();
        assertEquals("HELP ", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleSuggestions() {
        //Multiple suggestions
        commandBox.enterCommand("EX");
        moveCursorRight("EX".length());
        commandBox.pressTab();
        assertEquals("EX", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_SingleSuggestionAfterWordsPartial() {
        //Single suggestions with words (partial)
        commandBox.enterCommand("randomString ED");
        moveCursorRight("randomString ED".length());
        commandBox.pressTab();
        assertEquals("randomString EDIT", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_SingleSuggestionAfterWords() {
        //Single suggestions with words
        commandBox.enterCommand("randomString EDITL");
        moveCursorRight("randomString EDI".length());
        commandBox.pressTab();
        assertEquals("randomString EDITLABEL ", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleSuggestionBeforeWords() {
        //Auto complete keyword before multiple suggestions
        commandBox.enterCommand("ED randomString");
        moveCursorRight(1);
        commandBox.pressTab();
        assertEquals("EDIT randomString", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_SingleSuggestionBeforeWords() {
        //Auto complete keyword before single suggestion
        commandBox.enterCommand("EDITL randomString");
        moveCursorRight(1);
        commandBox.pressTab();
        assertEquals("EDITLABEL randomString", commandBox.getCommandInput());
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
        commandBox.enterCommand("HE");
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        moveCursorRight("HE".length());
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        assertEquals("HELP ", commandBox.getCommandInput());
    }

    @Test
    public void autocomplete_MultipleTabsAutocompleteEnd() {
        //pressing tab multiple times should not affect the auto completion
        commandBox.enterCommand("randomString ED");
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        moveCursorRight("randomString ED".length());
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        commandBox.pressTab();
        assertEquals("randomString EDIT", commandBox.getCommandInput());
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
