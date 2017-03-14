package seedu.toluist.controller;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.toluist.ui.Ui;

import java.util.Arrays;

/**
 * Tests for ExitController
 */
public class ExitControllerTest extends ControllerTest {
    protected Controller controllerUnderTest(Ui renderer) {
        return new ExitController(renderer);
    }

    @Test
    public void getCommandWord() {
        assertEquals(Arrays.asList(ExitController.COMMAND_WORD_EXIT, ExitController.COMMAND_WORD_QUIT),
                    Arrays.asList(ExitController.getCommandWords()));
    }

    @Test
    public void matchesCommand() {
        assertTrue(controller.matchesCommand("exit "));
        assertTrue(controller.matchesCommand("exit"));
        assertTrue(controller.matchesCommand("quit"));
        assertFalse(controller.matchesCommand(" quit"));
    }

    @Test
    public void tokenize() {
        assertEquals(null, controller.tokenize("exit"));
        assertEquals(null, controller.tokenize("quit "));
    }
}
