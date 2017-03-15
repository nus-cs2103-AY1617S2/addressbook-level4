package seedu.address.ui;

import java.util.List;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.autocomplete.Autocomplete;
import seedu.address.logic.autocomplete.AutocompleteTrie;
import seedu.address.logic.commandhistory.CommandHistory;
import seedu.address.logic.commandhistory.CommandHistoryLinkedList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private Autocomplete autocomplete;
    private CommandHistory commandHistory;

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.autocomplete = new AutocompleteTrie();
        this.commandHistory = new CommandHistoryLinkedList();
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
    private void handleCommandInputChanged() {
        try {
            String command = commandTextField.getText();
            CommandResult commandResult = logic.execute(command);

            // process result of the command
            setStyleToIndicateCommandSuccess();
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            commandHistory.addCommand(command);
        } catch (CommandException e) {
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Hijacks the tab character for auto-completion, up/down for iterating through the command
     * @param ke
     */
    @FXML
    private void handleOnKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.TAB) {
            String command = commandTextField.getText();
            int caretPosition = commandTextField.getCaretPosition();
            String wordAtCursor = autocomplete.getWordAtCursor(command, caretPosition);
            List<String> suggestions = autocomplete.getSuggestions(wordAtCursor);

            handleSuggestions(command, caretPosition, suggestions);
            //Consume the event so the textfield will not go to the next ui component
            ke.consume();
        } else if (ke.getCode() == KeyCode.UP) {
            getPreviousCommand();
            ke.consume();
        } else if (ke.getCode() == KeyCode.DOWN) {
            getNextCommand();
            ke.consume();
        }
    }

    /**
     * Gets the next executed command from the current command (if iterated through before)
     */
    private void getNextCommand() {
        String text = commandHistory.next();
        text = text == null ? commandTextField.getText() : text;
        commandTextField.setText(text);
        moveCursorToEndOfField();
    }

    /**
     * Gets the previously executed command from the current command
     */
    private void getPreviousCommand() {
        String text = commandHistory.previous();
        text = text == null ? commandTextField.getText() : text;
        commandTextField.setText(text);
        moveCursorToEndOfField();
    }

    /**
     * Moves the cursor in commandTextField to the end of the string
     */
    private void moveCursorToEndOfField() {
        commandTextField.positionCaret(commandTextField.getLength());
    }

    /**
     * Handles suggestions to replace/suggest
     * @param suggestions - list of suggestions
     */
    private void handleSuggestions(String command, int caretPosition, List<String> suggestions) {
        //if empty or no match
        if (suggestions.isEmpty() || suggestions.size() == AutocompleteTrie.AUTOCOMPLETE_DATA.length) {
            return;
        } else { //show suggestions in the output box
            processSuggestions(command, caretPosition, suggestions);
        }
    }

    /**
     * Shows suggestions in the output box
     */
    private void processSuggestions(String command, int caretPosition, List<String> suggestions) {
        logger.info("Suggestions: " + suggestions);

        String longestString = autocomplete.getLongestString(suggestions);
        int commonSubstringIndex = autocomplete.getCommonSubstringEndIndexFromStart(suggestions);
        String commonSubstring = longestString.substring(0, commonSubstringIndex);

        //Append a space IF AND ONLY IF the auto-completed word is the last word of the command
        String appendCharacter = "";
        int cursorWordEndIndex = autocomplete.getEndIndexOfWordAtCursor(command, caretPosition);
        boolean endHasSpace = false;
        if (cursorWordEndIndex == command.trim().length() && suggestions.size() == 1) {
            //Append a space if there is a space at the end already
            if (command.charAt(command.length() - 1) != ' ') {
                appendCharacter = " ";
            }
            endHasSpace = true;
        }
        replaceCurrentWordWithSuggestion(command, caretPosition, commonSubstring, appendCharacter);

        //Move position caret to after auto completed word
        String currentWord = autocomplete.getWordAtCursor(command, caretPosition);
        int newPositionCaret = (cursorWordEndIndex - currentWord.length() + commonSubstring.length()) +
                                (endHasSpace ? 1 : 0);
        commandTextField.positionCaret(newPositionCaret);

        updateAutocompleteFeedback(suggestions);
    }

    /**
     * Updates the output window to either nothing or a list of suggestions
     */
    private void updateAutocompleteFeedback(List<String> suggestions) {
        if (suggestions.size() > 1) { //Show list of suggestions if more than 1
            raise(new NewResultAvailableEvent(suggestions.toString()));
        } else {
            raise(new NewResultAvailableEvent(""));
        }
    }

    /**
     * Replaces the current word with the suggestion provided
     */
    private void replaceCurrentWordWithSuggestion(String command, int caretPosition,
                                                    String suggestion, String toAppend) {
        commandTextField.setText(autocomplete.replaceCurrentWordWithSuggestion(command, caretPosition,
                                                                                suggestion, toAppend));
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
