package seedu.ezdo.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.ezdo.commons.core.LogsCenter;
import seedu.ezdo.commons.events.ui.NewResultAvailableEvent;
import seedu.ezdo.commons.util.FxViewUtil;
import seedu.ezdo.logic.Logic;
import seedu.ezdo.logic.commands.CommandResult;
import seedu.ezdo.logic.commands.exceptions.CommandException;

/**
 * For the user to input commands.
 */
public class CommandBox extends UiPart<Region> {
    private static final String MESSAGE_INVALID_COMMAND = "Invalid command: ";
    private static final String MESSAGE_RESULT = "Result: ";
    private static final String TEXT_NO_COMMAND_INPUT = "";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String SUCCESS_STYLE_CLASS = "success";
    public static final String MESSAGE_COMMANDBOX_TOOLTIP = "Command Box\nYou can type your commands here.";

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        commandTextField.setTooltip(new Tooltip(MESSAGE_COMMANDBOX_TOOLTIP));
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

  //@@author A0139177W
    /**
     * Executes the command input by the user.
     */
    @FXML
    public void handleCommandInputChanged() {
        // reset command text field color if it is changed.
        resetCommandFieldListener();

        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            commandTextField.setText(TEXT_NO_COMMAND_INPUT);
            setStyleToIndicateCommandSuccess();
            logger.info(MESSAGE_RESULT + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info(MESSAGE_INVALID_COMMAND + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Add command field listener to reset command text field color if it is changed.
     */
    private void resetCommandFieldListener() {
        commandTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
            commandTextField.getStyleClass().remove(SUCCESS_STYLE_CLASS);
        });
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().add(SUCCESS_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }
  //@@author
}

