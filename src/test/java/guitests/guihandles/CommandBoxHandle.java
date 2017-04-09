package guitests.guihandles;

import java.util.ArrayList;
import java.util.List;

import org.fxmisc.richtext.InlineCssTextArea;

import guitests.GuiRobot;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import seedu.doist.ui.util.CommandAutoCompleteController;
import seedu.doist.ui.util.ContentAssistPopupWindow;

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
        enterCommand(command);
        pressEnter();
        guiRobot.sleep(GuiHandleSetting.COMMAND_RUN_TIME); //Give time for the command to take effect
    }

    public HelpWindowHandle runHelpCommand() {
        enterCommand("help");
        pressEnter();
        return new HelpWindowHandle(guiRobot, primaryStage);
    }

    public ObservableList<String> getStyleClass() {
        return getNode(COMMAND_INPUT_FIELD_ID).getStyleClass();
    }

    //@@author A0147980U
    public ContentAssistPopupWindow getContentAssistWindow() {
        InlineCssTextArea inputBox = (InlineCssTextArea) getNode(COMMAND_INPUT_FIELD_ID);
        CommandAutoCompleteController.getInstance().attachSuggestionWindowIfNecessary(inputBox);
        ContentAssistPopupWindow contentAssistWindow = (ContentAssistPopupWindow) inputBox.getPopupWindow();
        return contentAssistWindow;
    }

    public List<String> getContentAssistItemTexts() {
        ContentAssistPopupWindow contentAssistWindow = getContentAssistWindow();
        ArrayList<String> itemtTexts = new ArrayList<String>();
        for (MenuItem item : contentAssistWindow.getItems()) {
            itemtTexts.add(item.getText());
        }
        return itemtTexts;
    }

    public List<String> getWordListWithStyle(String styleInCSS) {
        InlineCssTextArea inputBox = (InlineCssTextArea) getNode(COMMAND_INPUT_FIELD_ID);
        ArrayList<String> wordList = new ArrayList<String>();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < inputBox.getLength(); i++) {
            if (inputBox.getText().charAt(i) == ' ') {
                if (sb.toString().length() > 0) {
                    wordList.add(sb.toString());
                    sb = new StringBuilder();
                }
            } else if (inputBox.getStyleOfChar(i).equals(styleInCSS)) {
                sb.append(inputBox.getText().charAt(i));
            }
        }

        return wordList;
    }
}














