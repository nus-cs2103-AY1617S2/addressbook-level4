package seedu.onetwodo.ui;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.onetwodo.commons.core.LogsCenter;
import seedu.onetwodo.commons.events.ui.DeselectCardsEvent;
import seedu.onetwodo.commons.events.ui.NewResultAvailableEvent;
import seedu.onetwodo.commons.util.FxViewUtil;
import seedu.onetwodo.logic.Logic;
import seedu.onetwodo.logic.commands.CommandResult;
import seedu.onetwodo.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private Stack<String> previousCommands = new Stack<String>();
    private Stack<String> refilledCommands = new Stack<String>();

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
        resetKeyListener();

    }
    @FXML
    private void handleCommandInputChanged() {
        String command = commandTextField.getText();
        previousCommands.push(command);
        handleCommands(command);
    }

    public void handleCommands(String command) {
        try {
            CommandResult commandResult = logic.execute(command);
            setKeyListenerForMutators(command);
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

    public void focus() {
        commandTextField.requestFocus();
    }

    public void undoTextField() {
        commandTextField.undo();
    }

    public void setKeyListener(EventHandler<KeyEvent> ke) {
        commandTextField.setOnKeyPressed(ke);
    }

    private void setKeyListenerForMutators(String command) {
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                raise(new DeselectCardsEvent());
                resetKeyListener();
            }
        });
    }

    public void resetKeyListener() {
        commandTextField.setOnKeyPressed(null);
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                setStyleToIndicateCommandSuccess();
                resetIfUpDownKey(ke);
            }
        });
    }

    private void resetIfUpDownKey(KeyEvent ke) {
        KeyCode keyCode = ke.getCode();
        switch (keyCode) {
        case UP: undoTextArea();
        case DOWN: redoTextArea();
        default: break;
        }
    }

    private void undoTextArea() {
        if (previousCommands.isEmpty()) {
            return;
        }
        String previousCommand = previousCommands.pop();
        refilledCommands.push(previousCommand);
        if (refilledCommands.isEmpty()) {
            commandTextField.setText("");
        } else {
            commandTextField.setText(refilledCommands.peek());
        }
    }

    private void redoTextArea() {
        if (refilledCommands.isEmpty()) {
            return;
        }
        String previousRefilledCommand = refilledCommands.pop();
        previousCommands.push(previousRefilledCommand);
        if (refilledCommands.isEmpty()) {
            commandTextField.setText("");
        } else {
            commandTextField.setText(refilledCommands.peek());
        }
    }

}
