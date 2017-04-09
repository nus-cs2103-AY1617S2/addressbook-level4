package seedu.doist.ui;

import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

import java.util.logging.Logger;

import org.fxmisc.richtext.InlineCssTextArea;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.SplitPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.doist.commons.core.EventsCenter;
import seedu.doist.commons.core.LogsCenter;
import seedu.doist.commons.events.ui.JumpToListRequestEvent;
import seedu.doist.commons.events.ui.NewResultAvailableEvent;
import seedu.doist.commons.util.FxViewUtil;
import seedu.doist.commons.util.History;
import seedu.doist.logic.Logic;
import seedu.doist.logic.commands.CommandResult;
import seedu.doist.logic.commands.RedoCommand;
import seedu.doist.logic.commands.UndoCommand;
import seedu.doist.logic.commands.exceptions.CommandException;
import seedu.doist.ui.util.CommandAutoCompleteController;
import seedu.doist.ui.util.CommandHighlightController;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    public static final String SUCCESS_STYLE_CLASS = "white";

    private final Logic logic;
    private final History<String> commandHistory = new History<String>();
    private String unEnteredInput = "";

    private final KeyCombination undoKeys = new KeyCodeCombination(KeyCode.Z, CONTROL_DOWN);
    private final KeyCombination redoKeys = new KeyCodeCombination(KeyCode.Y, CONTROL_DOWN);

    private CommandHighlightController highlightManager = CommandHighlightController.getInstance();
    private CommandAutoCompleteController autoCompleteManager = CommandAutoCompleteController.getInstance();

    private boolean navigationMode = false;
    private int currentIndex = -1;

    private ChangeListener<? super String> highlightListener = (observable, oldValue, newValue)
        -> highlightAndSuggestCompletion();
    private ChangeListener<? super String> disableInputListener = (observable, oldValue, newValue)
        -> removeInput();

    public static final String NAVIGATION_MODE_MESSAGE = "quick navigation mode\n\nj: down\nk: up";
    public static final String EDITING_MODE_MESSAGE = "editing mode";

    @FXML
    private InlineCssTextArea commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);

        commandTextField.textProperty().addListener(highlightListener);
    }

    //@@author A0147980U
    private void highlightAndSuggestCompletion() {
        highlightManager.highlight(commandTextField);
        autoCompleteManager.suggestCompletion(commandTextField, logic);
    }
    //@@author

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    //@@author A0147620L
    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (navigationMode) {
            handleKeyPressedInNavigationMode(event);
        } else {
            handleKeyPressedInEditingMode(event);
        }
    }

    //@@author A0147980U
    private void handleKeyPressedInNavigationMode(KeyEvent event) {
        event.consume();
        if (event.getCode() == KeyCode.J) {
            if (currentIndex + 1 < logic.getFilteredTaskList().size()) {
                currentIndex++;
            }
        } else if (event.getCode() == KeyCode.K) {
            if (currentIndex - 1 >= 0) {
                currentIndex--;
            }
        } else if (event.getCode() == KeyCode.ESCAPE) {
            currentIndex = -1;
            turnOnEditingMode();
        }
        EventsCenter.getInstance().post(new JumpToListRequestEvent(currentIndex));
    }

    private void handleKeyPressedInEditingMode(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            event.consume();
            turnOnNavigationMode();
        } else if (event.getCode() == KeyCode.ENTER) {
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
        } else if (event.getCode() == KeyCode.TAB) {  // auto complete
            event.consume();
            completeWithSelectedSuggestion();
        } else if (undoKeys.match(event)) {  // use control+z and control+y to execute undo and re-do operation
            event.consume();
            handleCtrlZKeyCombination();
        } else if (redoKeys.match(event)) {
            event.consume();
            handleCtrlYKeyCombination();
        }
    }

    private void turnOnNavigationMode() {
        navigationMode = true;
        commandTextField.textProperty().removeListener(highlightListener);
        commandTextField.textProperty().addListener(disableInputListener);
        logger.info("turn on navigation mode");
        raise(new NewResultAvailableEvent(NAVIGATION_MODE_MESSAGE));
    }

    private void turnOnEditingMode() {
        navigationMode = false;
        commandTextField.textProperty().removeListener(disableInputListener);
        commandTextField.textProperty().addListener(highlightListener);
        logger.info("turn on editing mode");
        raise(new NewResultAvailableEvent(EDITING_MODE_MESSAGE));
    }

    private void removeInput() {
        commandTextField.clear();
    }

    //@@author A0147620L
    //Handles Down key press
    private void handleDownKey() {
        String userCommandText = commandHistory.getNextState();
        if (userCommandText == null) {
            setCommandInput(unEnteredInput);
        } else {
            setCommandInput(userCommandText);
        }
    }

    //Handle Up key press
    private void handleUpKey() {
        if (commandHistory.isAtMostRecentState()) {
            unEnteredInput = commandTextField.getText();
        }
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
    //@@author

    //@@author A0147980U
    //Handle Control + z key combination for undo operation
    private void handleCtrlZKeyCombination() {
        try {
            CommandResult commandResult  = logic.execute(UndoCommand.DEFAULT_COMMAND_WORD);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException e) { /* DEFAULT_COMMAND_WORD will not cause exception */ }
    }

    //Handle Control + y key combination for re-do operation
    private void handleCtrlYKeyCombination() {
        try {
            CommandResult commandResult  = logic.execute(RedoCommand.DEFAULT_COMMAND_WORD);
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
        } catch (CommandException e) { /* DEFAULT_COMMAND_WORD will not cause exception */ }
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
        commandTextField.clear();
        commandTextField.appendText(string);

        // move the cursor to the end of the input string
        commandTextField.positionCaret(string.length());
    }

    private void completeWithSelectedSuggestion() {
        ContextMenu suggestionList = (ContextMenu) commandTextField.getPopupWindow();
        if (suggestionList.isShowing() && !suggestionList.getItems().isEmpty()) {
            int cursorPosition = commandTextField.getCaretPosition();
            // -1 means trailing space will NOT be discarded
            String[] words = commandTextField.getText(0, cursorPosition).split(" +", -1);
            String lastWord = words[words.length - 1].replaceAll("\\\\", "\\\\\\\\");
            String suggestion = suggestionList.getItems().get(0).getText();
            if ("find".equals(words[0])) {
                handleFindTab(cursorPosition, words, suggestion);
            } else {
                String remainingString = suggestion.replaceAll(lastWord, "");
                commandTextField.insertText(cursorPosition, remainingString);
            }
        }
    }

    //@@author A0147620L
    /** Method to special handle text-completion for 'Find' command */
    private void handleFindTab(int cursorPosition, String[] words, String suggestion) {
        StringBuilder s = new StringBuilder();
        for (int i = 1; i < words.length; i++) {
            s.append(words[i]).append(" ");
        }
        if (!(s.toString().contains(suggestion))) {
            commandTextField.deleteText(cursorPosition - s.toString().length(), cursorPosition);
            commandTextField.insertText(commandTextField.getCaretPosition(), suggestion);
        }
    }
    //@@author

    //@@author A0140887W
    /**
     * Sets the command box style to indicate a successful command.
     */
    private void setStyleToIndicateCommandSuccess() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
        // Only add success style if don't already have
        if (!commandTextField.getStyleClass().contains(SUCCESS_STYLE_CLASS)) {
            commandTextField.getStyleClass().add(SUCCESS_STYLE_CLASS);
        }
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        commandTextField.getStyleClass().remove(SUCCESS_STYLE_CLASS);
        // Only add error style if don't already have
        if (!commandTextField.getStyleClass().contains(ERROR_STYLE_CLASS)) {
            commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
        }
    }
}
