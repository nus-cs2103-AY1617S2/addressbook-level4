//@@author A0131125Y
package seedu.toluist.controller;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for StoreController
 */
public class StoreControllerTest extends ControllerTest {
    protected Controller controllerUnderTest() {
        return new StoreController();
    }

    @Test
    public void getCommandWord() {
        assertEquals(Arrays.asList(StoreController.COMMAND_WORD),
                Arrays.asList(new StoreController().getCommandWords()));
    }

    @Test
    public void matchesCommand() {
        // With no storage provided
        assertTrue(controller.matchesCommand("save  "));
        assertTrue(controller.matchesCommand("save"));

        // with valid storage provided
        assertTrue(controller.matchesCommand("save aFile.json"));

        // with invalid storage provided
        assertTrue(controller.matchesCommand("save sfas?////sffsf.json"));

        // with more than 1 parameters
        assertFalse(controller.matchesCommand("save a b c"));
    }

    @Test
    public void tokenize() {
        HashMap<String, String> tokensNoArgument = new HashMap<>();
        tokensNoArgument.put(StoreController.PARAMETER_STORE_DIRECTORY, null);
        assertEquals(tokensNoArgument, controller.tokenize("save"));

        HashMap<String, String> tokensOneArgument = new HashMap<>();
        tokensOneArgument.put(StoreController.PARAMETER_STORE_DIRECTORY, "abc");
        assertEquals(tokensOneArgument, controller.tokenize("save abc"));
    }
}
