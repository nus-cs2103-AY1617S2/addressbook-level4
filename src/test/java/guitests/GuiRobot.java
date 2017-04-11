package guitests;

import org.testfx.api.FxRobot;

import javafx.scene.input.KeyCodeCombination;
import seedu.task.testutil.TestUtil;

/**
 * Robot used to simulate user actions on the GUI. Extends {@link FxRobot} by adding some customized
 * functionality and workarounds.
 */
public class GuiRobot extends FxRobot {

    @Override
    public GuiRobot push(KeyCodeCombination keyCodeCombination) {
        return (GuiRobot) super.push(TestUtil.scrub(keyCodeCombination));
    }

}
