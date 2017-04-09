//@@author A0131125Y
package seedu.toluist.controller;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for AliasController
 */
public class AliasControllerTest extends ControllerTest {
    protected Controller controllerUnderTest() {
        return new AliasController();
    }

    @Test
    public void getCommandWord() {
        assertEquals(Arrays.asList(AliasController.COMMAND_WORD),
                Arrays.asList(new AliasController().getCommandWords()));
    }

    @Test
    public void matchesCommand() {
        assertTrue(controller.matchesCommand("alias a add"));
        assertTrue(controller.matchesCommand("alias d1 delete 1"));
        assertFalse(controller.matchesCommand("alias a"));
        assertFalse(controller.matchesCommand("alias"));
    }

    @Test
    public void tokenize() {
        HashMap<String, String> tokensForAliasingOneWord = new HashMap<>();
        tokensForAliasingOneWord.put(AliasController.PARAMETER_ALIAS, "a");
        tokensForAliasingOneWord.put(AliasController.PARAMETER_COMMAND, "add");
        assertEquals(tokensForAliasingOneWord, controller.tokenize("alias a add"));

        HashMap<String, String> tokensForAliasingMultipleWords = new HashMap<>();
        tokensForAliasingMultipleWords.put(AliasController.PARAMETER_ALIAS, "u1");
        tokensForAliasingMultipleWords.put(AliasController.PARAMETER_COMMAND, "update 1");
        assertEquals(tokensForAliasingMultipleWords, controller.tokenize("alias u1   update 1"));
    }
}
