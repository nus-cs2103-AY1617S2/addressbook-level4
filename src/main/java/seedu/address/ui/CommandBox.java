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
import seedu.address.logic.Autocomplete;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.Logic;
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
        this.autocomplete = new Autocomplete();
        this.commandHistory = new CommandHistory();
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
            //commandTextField.setText(commandTextField.getText().replaceAll("\\s+$", ""));
            String wordAtCursor = getWordAtCursor();
            List<String> suggestions = autocomplete.getSuggestions(wordAtCursor);
            handleSuggestions(suggestions);
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
    private void handleSuggestions(List<String> suggestions) {
        //if empty or no match
        if (suggestions.isEmpty() || suggestions.size() == Autocomplete.autocompleteData.length) {
            return;
        } else { //show suggestions in the output box
            processSuggestions(suggestions);
        }
    }

    /**
     * Shows suggestions in the output box
     * @param suggestions - list of suggestions
     */
    private void processSuggestions(List<String> suggestions) {
        logger.info("Suggestions: " + suggestions);

        String longestString = getLongestString(suggestions);
        int commonSubstringIndex = getCommonSubstringEndIndexFromStart(suggestions);

        String commonSubstring = longestString.substring(0, commonSubstringIndex);
        String appendCharacter = "";
        int cursorWordEndIndex = getEndIndexOfWordAtCursor();
        String currentWord = getWordAtCursor();
        boolean endHasSpace = false;
        if (cursorWordEndIndex == commandTextField.getText().trim().length() && //is last word of command
                suggestions.size() == 1) { //Only have 1 suggestion
            //Append a space if there is a space at the end already
            if (commandTextField.getText().charAt(commandTextField.getLength() - 1) != ' ') {
                appendCharacter = " ";
            }
            endHasSpace = true;
        }
        replaceCurrentWordWithSuggestion(commonSubstring, appendCharacter);
        commandTextField.positionCaret((cursorWordEndIndex - currentWord.length() + commonSubstring.length()) +
                                        (endHasSpace ? 1 : 0));
        if (suggestions.size() > 1) {
            raise(new NewResultAvailableEvent(suggestions.toString()));
        } else {
            raise(new NewResultAvailableEvent(""));
        }
    }

    /**
     * Returns the character index of the common substring for all Strings in the suggestion
     */
    private int getCommonSubstringEndIndexFromStart(List<String> suggestions) {
        String longestString = getLongestString(suggestions);
        int commonSubstringIndex = 0;
        char currentChar;
        commonSubstring:
        for (int charIndex = 0; charIndex < longestString.length(); charIndex++) {
            currentChar = longestString.charAt(charIndex);
            for (String suggestion : suggestions) {
                if (suggestion.length() <= charIndex || suggestion.charAt(charIndex) != currentChar) {
                    break commonSubstring;
                }
            }
            commonSubstringIndex++;
        }
        return commonSubstringIndex;
    }

    /**
     * Returns the longest string in the list
     */
    private String getLongestString(List<String> suggestions) {
        String longest = "";
        for (String suggestion : suggestions) {
            if (suggestion.length() >= longest.length()) {
                longest = suggestion;
            }
        }
        return longest;
    }

    private void replaceCurrentWordWithSuggestion(String suggestion, String toAppend) {
        StringBuffer commandBoxText = new StringBuffer(commandTextField.getText());
        commandBoxText.replace(getStartIndexOfWordAtCursor(),
                                    getEndIndexOfWordAtCursor(),
                                    (suggestion + toAppend));
        commandTextField.setText(commandBoxText.toString());
    }

    /**
     * Extracts the word at the current cursor (cursor always represents the character on the left of it)
     */
    private String getWordAtCursor() {
        String text = commandTextField.getText();

        if (!text.trim().equals("")) {
            int startIndex = getStartIndexOfWordAtCursor();
            int endIndex = getEndIndexOfWordAtCursor();
            return text.substring(startIndex, endIndex);
        } else {
            return text;
        }

    }

    /**
     * Gets the start index of the word at cursor
     */
    private int getStartIndexOfWordAtCursor() {
        String text = commandTextField.getText();
        int currentPosition = commandTextField.getCaretPosition();
        int startIndex;
        currentPosition = currentPosition - 1 < 0 ? 0 : currentPosition - 1;
        while (currentPosition > 0  &&
                currentPosition < text.length() &&
                !Character.isWhitespace(text.charAt(currentPosition))) {
            currentPosition--;
        }

        //Increment index by 1 if whitespace is met
        if (Character.isWhitespace(text.charAt(currentPosition))) {
            currentPosition++;
        }

        startIndex = currentPosition;
        return startIndex;
    }

    /**
     * Gets the end index of the word at cursor
     */
    private int getEndIndexOfWordAtCursor() {
        String text = commandTextField.getText();
        int currentPosition = commandTextField.getCaretPosition();
        int endIndex;
        while (currentPosition >= 0  &&
                currentPosition < text.length() &&
                !Character.isWhitespace(text.charAt(currentPosition))) {
            currentPosition++;
        }
        endIndex = currentPosition;
        return endIndex;
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
