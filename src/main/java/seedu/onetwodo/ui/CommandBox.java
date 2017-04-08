package seedu.onetwodo.ui;

import java.util.Stack;
import java.util.logging.Logger;

import javafx.css.PseudoClass;
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
import seedu.onetwodo.logic.commands.AddCommand;
import seedu.onetwodo.logic.commands.CommandResult;
import seedu.onetwodo.logic.commands.EditCommand;
import seedu.onetwodo.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private Stack<String> previousCommands = new Stack<String>();
    private Stack<String> refilledCommands = new Stack<String>();
    public static final PseudoClass ERROR_PSEUDOCLASS = PseudoClass.getPseudoClass(ERROR_STYLE_CLASS);

    private final Logic logic;
    private EventHandler<KeyEvent> scrollHandler;

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

    //@@author A0143029M
    public void handleCommands(String command) {
        try {
            CommandResult commandResult = logic.execute(command);
            setKeyListenerForMutators(command);
            // process result of the command
            setErrorStyleForCommandResult(false);
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException e) {
            // handle command failure
            setErrorStyleForCommandResult(true);
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Sets the command box error pseudo-style based on command result.
     */
    private void setErrorStyleForCommandResult(boolean isError) {
        commandTextField.pseudoClassStateChanged(ERROR_PSEUDOCLASS, isError);
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

    public void setKeyListenerReleased(EventHandler<KeyEvent> ke) {
        commandTextField.setOnKeyReleased(ke);
    }

    private void setKeyListenerForMutators(String command) {
        switch (command) {
        case AddCommand.COMMAND_WORD:
        case EditCommand.COMMAND_WORD:
            commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent ke) {
                    raise(new DeselectCardsEvent());
                    resetKeyListener();
                }
            });
            break;
        default:
            break;
        }
    }

    public void resetKeyListener() {
        commandTextField.setOnKeyPressed(null);
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                setErrorStyleForCommandResult(false);
                resetIfUpDownKey(ke);
            }
        });
        commandTextField.setOnKeyReleased(null);
        commandTextField.setOnKeyReleased(scrollHandler);
    }

    private void resetIfUpDownKey(KeyEvent ke) {
        KeyCode keyCode = ke.getCode();
        switch (keyCode) {
        case UP:
            undoTextArea();
            break;
        case DOWN:
            redoTextArea();
            break;
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

    public void setScrollKeyListener(EventHandler<KeyEvent> scrollHandler) {
        this.scrollHandler = scrollHandler;
        commandTextField.setOnKeyReleased(scrollHandler);
    }

}
