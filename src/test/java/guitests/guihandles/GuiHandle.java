package guitests.guihandles;

import java.util.Optional;
import java.util.logging.Logger;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import seedu.toluist.TestApp;
import seedu.toluist.commons.core.LogsCenter;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    private static final Logger logger = LogsCenter.getLogger(GuiHandle.class);
    private static final int RESPONSE_SLEEP_TIME = 500;

    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    /**
     * An optional stage that exists in the App other than the primaryStage, could be a alert dialog, popup window, etc.
     */
    protected Optional<Stage> intermediateStage = Optional.empty();
    protected final String stageTitle;

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    public void focusOnWindow(String stageTitle) {
        logger.info("Focusing " + stageTitle);
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            logger.warning("Can't find stage " + stageTitle + ", Therefore, aborting focusing");
            return;
        }
        intermediateStage = Optional.ofNullable((Stage) window.get());
        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> window.get().requestFocus());
        logger.info("Finishing focus " + stageTitle);
    }

    protected <T extends Node> T getNode(String query) {
        return guiRobot.lookup(query).query();
    }

    protected String getTextFieldText(String filedName) {
        TextField textField = getNode(filedName);
        return textField.getText();
    }

    protected void setTextField(String textFieldId, String newText) {
        guiRobot.clickOn(textFieldId);
        TextField textField = getNode(textFieldId);
        textField.setText(newText);
        guiRobot.sleep(RESPONSE_SLEEP_TIME); // so that the texts stays visible on the GUI for a short period
    }

    public void press(KeyCode... keyCodes) {
        guiRobot.push(keyCodes).sleep(RESPONSE_SLEEP_TIME);
    }

    public void pressEnter() {
        guiRobot.type(KeyCode.ENTER).sleep(RESPONSE_SLEEP_TIME);
    }

    protected String getTextFromLabel(String fieldId, Node parentNode) {
        return ((Label) guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getText();
    }

    public void focusOnSelf() {
        if (stageTitle != null) {
            focusOnWindow(stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        Optional<Window> window = guiRobot.listTargetWindows()
                .stream()
                .filter(w -> w instanceof Stage && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        guiRobot.targetWindow(window.get());
        guiRobot.interact(() -> ((Stage) window.get()).close());
        focusOnMainApp();
    }
}
