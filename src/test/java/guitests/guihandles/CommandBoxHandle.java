package guitests.guihandles;

import guitests.GuiRobot;
import javafx.css.PseudoClass;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import seedu.onetwodo.ui.CommandBox;

/**
 * A handle to the Command Box in the GUI.
 */
public class CommandBoxHandle extends GuiHandle {

    private static final String COMMAND_INPUT_FIELD_ID = "#commandTextField";

    public CommandBoxHandle(GuiRobot guiRobot, Stage primaryStage, String stageTitle) {
        super(guiRobot, primaryStage, stageTitle);
    }

    /**
     * Clicks on the TextField.
     */
    public void clickOnTextField() {
        guiRobot.clickOn(COMMAND_INPUT_FIELD_ID);
    }

    public void enterCommand(String command) {
        setTextField(COMMAND_INPUT_FIELD_ID, command);
    }

    public String getCommandInput() {
        return getTextFieldText(COMMAND_INPUT_FIELD_ID);
    }

    /**
     * Enters the given command in the Command Box and presses enter.
     */
    public void runCommand(String command) {
        removeDialog();
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(200); // Give time for the command to take effect
    }

    private void removeDialog() {
        guiRobot.type(KeyCode.DELETE).sleep(200);
    }

    public HelpWindowHandle runHelpCommand() {
        enterCommand("help");
        pressEnter();
        pressEnter();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    //@@author A0141138N
    public HelpUGWindowHandle runHelpUGCommand() {
        enterCommand("help ug");
        pressEnter();
        pressEnter();
        return new HelpUGWindowHandle(guiRobot, primaryStage);
    }

    public boolean isErrorStyleApplied() {
        return getNode(COMMAND_INPUT_FIELD_ID).getPseudoClassStates().contains(CommandBox.ERROR_PSEUDOCLASS);
    }

    public void setErrorPseudoStyleClass() {
        PseudoClass errorPseudoClass = PseudoClass.getPseudoClass(CommandBox.ERROR_STYLE_CLASS);
        getNode(COMMAND_INPUT_FIELD_ID).pseudoClassStateChanged(errorPseudoClass, true);
    }

}
