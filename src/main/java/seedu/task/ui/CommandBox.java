package seedu.task.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.task.commons.core.LogsCenter;
import seedu.task.commons.events.ui.NewResultAvailableEvent;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.FxViewUtil;
import seedu.task.logic.CommandList;
import seedu.task.logic.Logic;
import seedu.task.logic.commands.CommandResult;
import seedu.task.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String BLANK_TEXT = "";
    private CommandList commandList;

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.commandList = CommandList.getInstance();
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
    private void handleCommandInputChanged() throws IllegalValueException {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

            commandList.addToList(commandTextField.getText());
            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
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

    //@@author A0141928B
    @FXML
    private void handleKeyPressed(KeyEvent key) {
        KeyCode keyCode = key.getCode();
        if (keyCode == KeyCode.UP || keyCode == KeyCode.KP_UP) {
            goToPreviousCommand();
            //Don't let the up key move the caret to the left
            key.consume();
        } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.KP_DOWN) {
            goToNextCommand();
            //Don't let the down key move the caret to the right
            key.consume();
        }
    }

    private void goToPreviousCommand() {
        String previousCommand = getPreviousCommand();
        commandTextField.replaceText(0, commandTextField.getLength(), previousCommand);
        commandTextField.end(); //Move caret to the end of the command
    }

    private void goToNextCommand() {
        String nextCommand = getNextCommand();
        commandTextField.replaceText(0, commandTextField.getLength(), nextCommand);
        commandTextField.end(); //Move caret to the end of the command
    }

    private String getPreviousCommand() {
        //commandList is sorted in reverse chronological order (most recent commands first)
        if (commandList.iterator.hasNext()) {
            return commandList.iterator.next();
        }
        return commandTextField.getText();
    }

    private String getNextCommand() {
        //commandList is sorted in reverse chronological order (most recent commands first)
        if (commandList.iterator.hasPrevious()) {
            return commandList.iterator.previous();
        }
        return BLANK_TEXT;
    }
}
