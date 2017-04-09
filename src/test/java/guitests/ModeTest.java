package guitests;

import org.junit.Test;

import javafx.scene.input.KeyCode;
import seedu.doist.ui.CommandBox;

public class ModeTest extends DoistGUITest {

    @Test
    public void testEditingModeAfterLaunch() {
        assertInEditingMode();
    }

    @Test
    public void testTurnOnNavigationMode() {
        typeEsc();
        assertInNavigationMode();
    }

    @Test
    public void testTurnOnEditingMode() {
        typeEsc();
        assertInNavigationMode();

        typeEsc();
        assertInEditingMode();
    }

    private void typeEsc() {
        GuiRobot bot = new GuiRobot();
        bot.press(KeyCode.ESCAPE);
        bot.release(KeyCode.ESCAPE);
    }

    private String getInputBoxContent() {
        return commandBox.getCommandInput();
    }

    private String getMessageBoxContent() {
        return resultDisplay.getText();
    }

    private void assertInNavigationMode() {
        commandBox.enterCommand("some random input");
        System.out.println(getInputBoxContent());
        assert getInputBoxContent().length() == 0;
        assert getMessageBoxContent().equals(CommandBox.NAVIGATION_MODE_MESSAGE);
    }

    private void assertInEditingMode() {
        String input = "some randome input";
        commandBox.enterCommand(input);
        assert getInputBoxContent().equals(input);
        assert !getMessageBoxContent().equals(CommandBox.NAVIGATION_MODE_MESSAGE);
    }
}
