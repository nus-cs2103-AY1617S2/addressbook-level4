package seedu.doit.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doit.commons.core.LogsCenter;
import seedu.doit.commons.events.ui.NewResultAvailableEvent;
import seedu.doit.commons.util.FxViewUtil;
import seedu.doit.logic.Logic;
import seedu.doit.logic.commands.CommandResult;
import seedu.doit.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
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
        placeHolderPane.getChildren().add(this.commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(this.commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = this.logic.execute(this.commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            setCommandBoxText("");
            this.logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            this.logger.info("Invalid command: " + this.commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        this.commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        this.commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

    // @@A0138909R
    /**
     * sets the text in the command box
     */
    public void setCommandBoxText(String text) {
        this.commandTextField.setText(text);
    }

    @Subscribe
    /**
     * Listens to keyEvents when command Box is focused
     */
    public void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            // up arrow

            break;
        case DOWN:
            // down arrow

            break;
        default:
            break;
        }
    }

    // @@author

}
