package seedu.doist.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.events.ui.NewResultAvailableEvent;
import seedu.doist.commons.util.FxViewUtil;
import seedu.doist.logic.Logic;
import seedu.doist.logic.commands.CommandHistory;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            try {
                String userCommandText = commandTextField.getText();
                CommandHistory.restore();
                if (!CommandHistory.addCommandHistory(userCommandText)) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                CommandResult commandResult = logic.execute(userCommandText);
                // process result of the command
                setStyleToIndicateCommandSuccess();
                commandTextField.setText("");
                logger.info("Result: " + commandResult.feedbackToUser);
                raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

            } catch (CommandException e) {
                // handle command failure
                setStyleToIndicateCommandFailure();
                commandTextField.setText("");
                logger.info("Invalid command: " + commandTextField.getText());
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
        } else if (event.getCode() == KeyCode.UP) {
            String userCommandText = CommandHistory.getPreviousCommand();
            if (userCommandText == null) {
                setCommandInput("");
            } else {
                setCommandInput(userCommandText);
            }
        } else if (event.getCode() == KeyCode.DOWN) {
            String userCommandText = CommandHistory.getNextCommand();
            if (userCommandText == null) {
                setCommandInput("");
            } else {
                setCommandInput(userCommandText);
            }
        }
    }


    private void setCommandInput(String string) {
        commandTextField.setText(string);
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
