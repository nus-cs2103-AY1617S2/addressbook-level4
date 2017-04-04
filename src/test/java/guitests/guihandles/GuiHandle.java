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
import seedu.doit.TestApp;
import seedu.doit.commons.core.LogsCenter;

/**
 * Base class for all GUI Handles used in testing.
 */
public class GuiHandle {
    protected final GuiRobot guiRobot;
    protected final Stage primaryStage;
    protected final String stageTitle;
    private final Logger logger = LogsCenter.getLogger(this.getClass());
    /**
     * An optional stage that exists in the App other than the primaryStage,
     * could be a alert dialog, popup window, etc.
     */
    protected Optional<Stage> intermediateStage = Optional.empty();

    public GuiHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        this.guiRobot = guiRobot;
        this.primaryStage = primaryStage;
        this.stageTitle = stageTitle;
        focusOnSelf();
    }

    public void focusOnWindow(String stageTitle) {
        this.logger.info("Focusing " + stageTitle);
        Optional<Window> window = this.guiRobot.listTargetWindows().stream()
                .filter(w -> (w instanceof Stage) && ((Stage) w).getTitle().equals(stageTitle)).findAny();

        if (!window.isPresent()) {
            this.logger.warning("Can't find stage " + stageTitle + ", Therefore, aborting focusing");
            return;
        }
        this.intermediateStage = Optional.ofNullable((Stage) window.get());
        this.guiRobot.targetWindow(window.get());
        this.guiRobot.interact(() -> window.get().requestFocus());
        this.logger.info("Finishing focus " + stageTitle);
    }

    protected <T extends Node> T getNode(String query) {
        return this.guiRobot.lookup(query).query();
    }

    protected String getTextFieldText(String filedName) {
        TextField textField = getNode(filedName);
        return textField.getText();
    }

    protected void setTextField(String textFieldId, String newText) {
        this.guiRobot.clickOn(textFieldId);
        TextField textField = getNode(textFieldId);
        textField.setText(newText);
        this.guiRobot.sleep(500); // so that the texts stays visible on the GUI
                                  // for a short period
    }

    public void pressEnter() {
        this.guiRobot.type(KeyCode.ENTER).sleep(500);
    }

    public void pressUp() {
        this.guiRobot.type(KeyCode.UP).sleep(500);
    }

    public void pressDown() {
        this.guiRobot.type(KeyCode.DOWN).sleep(500);
    }

    protected String getTextFromLabel(String fieldId, Node parentNode) {
        return ((Label) this.guiRobot.from(parentNode).lookup(fieldId).tryQuery().get()).getText();
    }

    public void focusOnSelf() {
        if (this.stageTitle != null) {
            focusOnWindow(this.stageTitle);
        }
    }

    public void focusOnMainApp() {
        this.focusOnWindow(TestApp.APP_TITLE);
    }

    public void closeWindow() {
        Optional<Window> window = this.guiRobot.listTargetWindows().stream()
                .filter(w -> (w instanceof Stage) && ((Stage) w).getTitle().equals(this.stageTitle)).findAny();

        if (!window.isPresent()) {
            return;
        }

        this.guiRobot.targetWindow(window.get());
        this.guiRobot.interact(() -> ((Stage) window.get()).close());
        focusOnMainApp();
    }
}
