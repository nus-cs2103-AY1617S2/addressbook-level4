//@@author A0131125Y
package seedu.toluist.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for SwitchController
 */
public class SwitchControllerTest extends ControllerTest {
    protected Controller controllerUnderTest() {
        return new SwitchController();
    }

    @Test
    public void getCommandWord() {
        assertEquals(Arrays.asList(SwitchController.COMMAND_WORD),
                Arrays.asList(new SwitchController().getCommandWords()));
    }

    @Test
    public void matchesCommand() {
        // With no tab provided
        assertTrue(controller.matchesCommand("switch  "));
        assertTrue(controller.matchesCommand("switch"));

        // with valid tab provided
        assertTrue(controller.matchesCommand("switch c"));

        // with invalid tab provided
        assertTrue(controller.matchesCommand("switch ccc   "));
    }

    @Test
    public void tokenize() {
        HashMap<String, String> tokensNoArgument = new HashMap<>();
        tokensNoArgument.put(SwitchController.PARAMETER_TAB, null);
        assertEquals(tokensNoArgument, controller.tokenize("switch  "));

        HashMap<String, String> tokensOneArgument = new HashMap<>();
        tokensOneArgument.put(SwitchController.PARAMETER_TAB, "abc");
        assertEquals(tokensOneArgument, controller.tokenize("switch abc"));
    }
}
