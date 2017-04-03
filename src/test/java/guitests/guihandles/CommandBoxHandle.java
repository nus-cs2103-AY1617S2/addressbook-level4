package guitests.guihandles;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import seedu.doit.logic.commands.HelpCommand;

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
        this.guiRobot.clickOn(COMMAND_INPUT_FIELD_ID);
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
        enterCommand(command);
        pressEnter();
        this.guiRobot.sleep(200); // Give time for the command to take effect
    }

    public HelpWindowHandle runHelpCommand() {
        enterCommand(HelpCommand.COMMAND_WORD);
        pressEnter();
        return new HelpWindowHandle(this.guiRobot, this.primaryStage);
    }

    public HelpWindowHandle runNewHelpCommand(String newHelp) {
        enterCommand(newHelp);
        pressEnter();
        return new HelpWindowHandle(this.guiRobot, this.primaryStage);
    }

    public ObservableList<String> getStyleClass() {
        return getNode(COMMAND_INPUT_FIELD_ID).getStyleClass();
    }
}
