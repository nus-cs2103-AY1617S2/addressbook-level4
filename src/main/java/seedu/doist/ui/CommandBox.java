package seedu.doist.ui;

import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

import java.util.logging.Logger;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.events.ui.NewResultAvailableEvent;
import seedu.doist.commons.util.FxViewUtil;
import seedu.doist.commons.util.History;
import seedu.doist.logic.Logic;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.RedoCommand;
import seedu.doist.logic.commands.UndoCommand;
import seedu.doist.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String COMMAND_WORD_STYLE = "-fx-fill: orange;";
    private static final String PARAMETER_KEY_STYLE = "-fx-fill: green;";
    private static final String NORMAL_STYLE = "-fx-fill: black;";


    private final Logic logic;
    private final History<String> commandHistory = new History<String>();

    private final KeyCombination undoKeys = new KeyCodeCombination(KeyCode.Z, CONTROL_DOWN);
    private final KeyCombination redoKeys = new KeyCodeCombination(KeyCode.Y, CONTROL_DOWN);

    @FXML
    private InlineCssTextArea commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);

        commandTextField.textProperty().addListener((observable, oldValue, newValue)
            -> highlightSyntax(observable, oldValue, newValue));
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    private void highlightSyntax(ObservableValue<?> value, String oldValue, String newValue) {
        String content = newValue;
        int i = 0;
        while (i < content.length() && content.charAt(i) != ' ') {
            commandTextField.setStyle(i, i + 1, COMMAND_WORD_STYLE);
            i++;
        }
        while (i < content.length()) {
            if (content.charAt(i) == '\\') {
                while (i < content.length() && content.charAt(i) != ' ') {
                    commandTextField.setStyle(i, i + 1, PARAMETER_KEY_STYLE);
                    i++;
                }
            }
            if (i >= content.length()) {
                break;
            }
            commandTextField.setStyle(i, i + 1, NORMAL_STYLE);
            i++;
        }
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            event.consume();
            handleEnterKey();
        } else if (event.getCode() == KeyCode.UP) {
            // up and down arrow key will move the cursor to the position 0
            // use consume() method to marks this Event as consumed. This stops such further propagation.
            event.consume();
            handleUpKey();
        } else if (event.getCode() == KeyCode.DOWN) {
            event.consume();
            handleDownKey();
        } else {  // use control+z and control+y to execute undo and re-do operation
            try {
                if (undoKeys.match(event)) {
                    event.consume();
                    logic.execute(UndoCommand.DEFAULT_COMMAND_WORD);
                } else if (redoKeys.match(event)) {
                    event.consume();
                    logic.execute(RedoCommand.DEFAULT_COMMAND_WORD);
                }
            } catch (CommandException e) {
                // handle command failure
                setStyleToIndicateCommandFailure();
                setCommandInput("");
                logger.info("Invalid command: " + commandTextField.getText());
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        }
    }

    //Handles Down key press
    private void handleDownKey() {
        String userCommandText = commandHistory.getNextState();
        if (userCommandText == null) {
            setCommandInput("");
        } else {
            setCommandInput(userCommandText);
        }
    }

    //Handle Up key press
    private void handleUpKey() {
        String userCommandText = commandHistory.getPreviousState();
        if (userCommandText != null) {
            setCommandInput(userCommandText);
        }
    }

    //Handle Enter key press
    private void handleEnterKey() {
        try {
            String userCommandText = commandTextField.getText();
            restoreCommandHistoryAndAppend(userCommandText);
            CommandResult commandResult = logic.execute(userCommandText);
            // process result of the command
            setStyleToIndicateCommandSuccess();
            setCommandInput("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            setCommandInput("");
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    //Restores the command history pointer
    //Throws exception is 'add' fails
    private void restoreCommandHistoryAndAppend(String userCommandText) {
        commandHistory.restore();
        if (!commandHistory.addToHistory(userCommandText)) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    private void setCommandInput(String string) {
        commandTextField.replaceText(string);

        // move the cursor to the end of the input string
        commandTextField.positionCaret(string.length());
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

}
