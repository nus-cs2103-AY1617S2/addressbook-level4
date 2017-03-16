package seedu.toluist.controller;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import seedu.toluist.ui.Ui;

/**
 * Tests for LoadController
 */
public class LoadControllerTest extends ControllerTest {
    protected Controller controllerUnderTest(Ui renderer) {
        return new LoadController(renderer);
    }

    @Test
    public void getCommandWord() {
        assertEquals(Arrays.asList(LoadController.COMMAND_WORD),
                Arrays.asList(LoadController.getCommandWords()));
    }

    @Test
    public void matchesCommand() {
        // With no storage provided
        assertTrue(controller.matchesCommand("load  "));
        assertTrue(controller.matchesCommand("load"));

        // with valid storage provided
        assertTrue(controller.matchesCommand("load aFile.json"));

        // with invalid storage provided
        assertTrue(controller.matchesCommand("load sfas?////sffsf.json"));

        // with more than 1 parameters
        assertFalse(controller.matchesCommand("load a b c"));
    }

    @Test
    public void tokenize() {
        HashMap<String, String> tokensNoArgument = new HashMap<>();
        tokensNoArgument.put(StoreController.STORE_DIRECTORY, null);
        assertEquals(tokensNoArgument, controller.tokenize("load"));

        HashMap<String, String> tokensOneArgument = new HashMap<>();
        tokensOneArgument.put(StoreController.STORE_DIRECTORY, "abc");
        assertEquals(tokensOneArgument, controller.tokenize("load abc"));
    }
}
