//@@author A0131125Y
package seedu.toluist.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

/**
 * Tests for ExitController
 */
public class ExitControllerTest extends ControllerTest {
    protected Controller controllerUnderTest() {
        return new ExitController();
    }

    @Test
    public void getCommandWord() {
        assertEquals(Arrays.asList(ExitController.COMMAND_WORD_EXIT, ExitController.COMMAND_WORD_QUIT),
                    Arrays.asList(new ExitController().getCommandWords()));
    }

    @Test
    public void matchesCommand() {
        assertTrue(controller.matchesCommand("exit "));
        assertTrue(controller.matchesCommand("exit"));
        assertTrue(controller.matchesCommand("quit"));
        assertTrue(controller.matchesCommand(" quit"));
    }

    @Test
    public void tokenize() {
        assertEquals(new HashMap<>(), controller.tokenize("exit"));
        assertEquals(new HashMap<>(), controller.tokenize("quit "));
    }
}
