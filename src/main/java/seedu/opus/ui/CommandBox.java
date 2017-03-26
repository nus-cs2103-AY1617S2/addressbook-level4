package seedu.opus.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.opus.commons.core.LogsCenter;
import seedu.opus.commons.events.ui.NewResultAvailableEvent;
import seedu.opus.commons.util.FxViewUtil;
import seedu.opus.logic.Logic;
import seedu.opus.logic.commands.CommandResult;
import seedu.opus.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String EMPTY_STRING = "";

    private final Logic logic;
    private final UserInputHistory history;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
        history = new UserInputHistory();
        registerCursorKeyEventFilter();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            history.saveUserInput(commandTextField.getText());
            CommandResult commandResult = logic.execute(commandTextField.getText());

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText(EMPTY_STRING);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException e) {
            // handle command failure
            logger.info("Invalid command: " + commandTextField.getText());
            commandTextField.setText(EMPTY_STRING);
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
     * Handles cursor key inputs to browse previous user input history text field
     */
    private void registerCursorKeyEventFilter() {
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                commandTextField.setText(history.getPreviousUserInput().orElse(EMPTY_STRING));
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                commandTextField.setText(history.getPrecedingUserInput().orElse(EMPTY_STRING));
            }
            commandTextField.selectEnd();
        });
    }
}
