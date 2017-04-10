package seedu.geekeep.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.geekeep.commons.core.LogsCenter;
import seedu.geekeep.commons.events.ui.NewResultAvailableEvent;
import seedu.geekeep.commons.util.FxViewUtil;
import seedu.geekeep.logic.Logic;
import seedu.geekeep.logic.commands.CommandResult;
import seedu.geekeep.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    private Optional<String> currentPrefix = Optional.empty();
    private int commandHistoryIndex;
    private List<String> matchingCommands = new ArrayList<>();
    private int matchingCommandIndex;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
        addHistoryEventHandler();
    }

    //@@author A0147622H
    private void addHistoryEventHandler() {
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            KeyCode keyCode = event.getCode();
            if (!(keyCode.equals(KeyCode.UP) || keyCode.equals(KeyCode.DOWN))) {
                if (keyCode.equals(KeyCode.ENTER)) {
                    currentPrefix = Optional.empty();
                }
                return;
            }
            String commandText = commandTextField.getText();
            int caretPosition = commandTextField.getCaretPosition();
            String prefix = commandText.substring(0, caretPosition);
            if (!(currentPrefix.equals(Optional.of(prefix))
                    && matchingCommands.get(matchingCommandIndex).equals(commandText))) {
                resetMatchingCommands(prefix, commandText);
            }
            Optional<String> matchingCommand;
            if (keyCode.equals(KeyCode.UP)) {
                matchingCommand = findPreviousMatchingCommand(prefix, commandText);
            } else {
                matchingCommand = findNextMatchingCommand(prefix);
            }
            if (matchingCommand.isPresent()) {
                commandTextField.setText(matchingCommand.get());
                commandTextField.positionCaret(caretPosition);
            }
            event.consume();
        });
    }

    private void resetMatchingCommands(String prefix, String commandText) {
        currentPrefix = Optional.of(prefix);
        commandHistoryIndex = getCommandHistory().size() - 1;
        matchingCommands.clear();
        matchingCommands.add(commandText);
        matchingCommandIndex = 0;
    }

    private Optional<String> findPreviousMatchingCommand(String prefix, String commandText) {
        logger.fine("Finding previous command that starts with \"" + prefix + "\"");
        if (matchingCommandIndex + 1 < matchingCommands.size()) {
            matchingCommandIndex++;
            return Optional.of(matchingCommands.get(matchingCommandIndex));
        }
        List<String> commandHistory = getCommandHistory();
        while (commandHistoryIndex >= 0) {
            String command = commandHistory.get(commandHistoryIndex);
            commandHistoryIndex--;
            if (command.startsWith(prefix) && !command.equals(commandText)) {
                matchingCommands.add(command);
                matchingCommandIndex++;
                return Optional.of(command);
            }
        }
        return Optional.empty();
    }

    private Optional<String> findNextMatchingCommand(String prefix) {
        logger.fine("Finding next command that starts with \"" + prefix + "\"");
        if (matchingCommandIndex == 0) {
            return Optional.empty();
        }
        matchingCommandIndex--;
        return Optional.of(matchingCommands.get(matchingCommandIndex));
    }

    private List<String> getCommandHistory() {
        return logic.getCommandHistory();
    }

    //@@author
    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    @FXML
    private void handleCommandInputChanged() {
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());

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

}
