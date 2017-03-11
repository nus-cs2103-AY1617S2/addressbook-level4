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
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private static final int ONLY_SUGGESTION_INDEX = 0;
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    private Autocomplete autocomplete;

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.autocomplete = new Autocomplete();
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
     * Hijacks the tab character for auto-completion
     * @param ke
     */
    @FXML
    private void handleOnKeyPressed(KeyEvent ke) {
        if (ke.getCode() == KeyCode.TAB) {
            String lastToken = extractLastKey();
            List<String> suggestions = autocomplete.getSuggestions(lastToken);
            handleSuggestions(suggestions);
            moveCursorToEnd();
            //Consume the event so the textfield will not go to the next ui component
            ke.consume();
        }
    }

    /**
     * Moves the cursor in commandTextField to the end of the string
     */
    private void moveCursorToEnd() {
        commandTextField.positionCaret(commandTextField.getLength());
    }

    /**
     * Handles suggestions to replace/suggest
     * @param suggestions - list of suggestions
     */
    private void handleSuggestions(List<String> suggestions) {
        if (suggestions.isEmpty()) {
            return;
        } else if (suggestions.size() == 1) { //exactly 1 suggestion
            replaceLastTokenWithSuggestion(suggestions.get(ONLY_SUGGESTION_INDEX));
        } else { //show suggestions in the output box
            showSuggestions(suggestions);
        }

    }

    /**
     * Shows suggestions in the output box
     * @param suggestions - list of suggestions
     */
    private void showSuggestions(List<String> suggestions) {
        logger.info("Suggestions: " + suggestions);
        raise(new NewResultAvailableEvent(suggestions.toString()));
    }

    private void replaceLastTokenWithSuggestion(String suggestion) {
        String commandBoxText = commandTextField.getText();
        String afterReplace = commandBoxText.replace(extractLastKey(), (suggestion + " "));
        commandTextField.setText(afterReplace);
    }

    /**
     * Extracts the last word from the last space
     * @return String from the last space
     */
    private String extractLastKey() {
        String commandBoxText = commandTextField.getText();
        int lastSpaceIndex = getIndexAfterLastSpace(commandBoxText);
        return commandBoxText.substring(lastSpaceIndex);
    }

    /**
     * Returns the last index of a space character of a given input string
     * @return index of the last space, 0 otherwise
     */
    private int getIndexAfterLastSpace(String input) {
        return input.lastIndexOf(" ") > 0 ? input.lastIndexOf(" ") + 1 : 0;
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
