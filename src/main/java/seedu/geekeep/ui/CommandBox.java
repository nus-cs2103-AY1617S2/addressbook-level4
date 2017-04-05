package seedu.geekeep.ui;

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

    private String userInput = "";
    private Optional<Integer> commandHistoryIndex = Optional.empty();

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
        addHistoryEventHandler();
    }

    private void addHistoryEventHandler() {
        commandTextField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().equals(KeyCode.UP)) {
                String currentText = commandTextField.getText();
                int caretPosition = commandTextField.getCaretPosition();
                String prefix = currentText.substring(0, caretPosition);
                Optional<String> matchingCommand = previousMatchingCommand(prefix, currentText);
                matchingCommand.ifPresent(text -> commandTextField.setText(text));
                commandTextField.positionCaret(caretPosition);
                event.consume();
            } else if (event.getCode().equals(KeyCode.DOWN)) {
                String currentText = commandTextField.getText();
                int caretPosition = commandTextField.getCaretPosition();
                String prefix = currentText.substring(0, caretPosition);
                Optional<String> matchingCommand = nextMatchingCommand(prefix, currentText);
                commandTextField.setText(matchingCommand.orElse(userInput));
                commandTextField.positionCaret(caretPosition);
                event.consume();
            }
        });
        commandTextField.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.UP) || event.getCode().equals(KeyCode.DOWN)) {
                return;
            }
            userInput = commandTextField.getText();
            commandHistoryIndex = Optional.empty();
        });
    }

    private Optional<String> previousMatchingCommand(String prefix, String currentText) {
        logger.fine("Finding previous command that starts with \"" + prefix + "\"");
        List<String> commandHistory = logic.getCommandHistory();
        int index = commandHistoryIndex.orElse(commandHistory.size()) - 1;
        while (index >= 0) {
            String command = commandHistory.get(index);
            if (command.startsWith(prefix) && !command.equals(currentText)) {
                commandHistoryIndex = Optional.of(index);
                return Optional.of(command);
            }
            index--;
        }
        return Optional.empty();
    }

    private Optional<String> nextMatchingCommand(String prefix, String currentText) {
        logger.fine("Finding next command that starts with \"" + prefix + "\"");
        List<String> commandHistory = logic.getCommandHistory();
        if (!commandHistoryIndex.isPresent()) {
            return Optional.empty();
        }
        int index = commandHistoryIndex.get() + 1;
        while (index < commandHistory.size()) {
            String command = commandHistory.get(index);
            if (command.startsWith(prefix) && !command.equals(currentText)) {
                commandHistoryIndex = Optional.of(index);
                return Optional.of(command);
            }
            index++;
        }
        commandHistoryIndex = Optional.empty();
        return Optional.empty();
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
