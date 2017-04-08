package seedu.tache.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.ui.NewResultAvailableEvent;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.logic.Logic;
import seedu.tache.logic.commands.CommandResult;
import seedu.tache.logic.commands.exceptions.CommandException;

public class CommandBox extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private static final String FXML = "CommandBox.fxml";
    public static final String ERROR_STYLE_CLASS = "error";
    //@@author A0142255M
    private static ArrayList<String> userInputs = new ArrayList<String>();
    private static int currentUserInputIndex = userInputs.size();
    //@@author

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
        setAutocomplete();
        setSaveCommandHistory();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        placeHolderPane.getChildren().add(commandTextField);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(commandTextField, 0.0, 0.0, 0.0, 0.0);
    }

    //@@author A0142255M
    /**
     * Executes the user command and adds it to the list of previous commands for retrieval.
     * Handles command success as well as command failure.
     */
    @FXML
    private void handleCommandInputChanged() {
        try {
            String userInput = commandTextField.getText();
            userInputs.add(userInput);
            currentUserInputIndex = userInputs.size();
            CommandResult commandResult = logic.execute(userInput);

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
     * Sets autocomplete functionality for user commands.
     * Uses ControlsFX Autocomplete TextField function.
     */
    private void setAutocomplete() {
        String[] possibleCommands = {"add ", "clear", "complete ", "delete ", "edit ", "exit", "find ",
                                        "help", "list", "save ", "select ", "load ", "undo" };
        AutoCompletionBinding<String> binding = TextFields.bindAutoCompletion(commandTextField, sr -> {
            ArrayList<String> commands = new ArrayList<String>();
            for (String str : possibleCommands) {
                String userInput = sr.getUserText();
                if ((!userInput.equals("") && str.startsWith(userInput) && !userInput.equals(str))) {
                    commands.add(str);
                }
            }
            return commands;
        });
        binding.setMaxWidth(100);
        binding.setDelay(50);
    }

    /**
     * Keeps track of previous commands executed and retrieves them with Up and Down keys.
     * Sets the caret of the text field right at the end of the command retrieved.
     */
    private void setSaveCommandHistory() {
        commandTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String userInput;
                if (userInputs.isEmpty()) {
                    return;
                }
                if (event.getCode() == KeyCode.UP && currentUserInputIndex >= 0) {
                    currentUserInputIndex--;
                    userInput = userInputs.get(currentUserInputIndex);
                    setTextAndCaret(userInput);
                }
                if (event.getCode() == KeyCode.DOWN && currentUserInputIndex < userInputs.size() - 1) {
                    currentUserInputIndex++;
                    userInput = userInputs.get(currentUserInputIndex);
                    setTextAndCaret(userInput);
                }
            }

            private void setTextAndCaret(String userInput) {
                if (userInput.length() > 0) {
                    commandTextField.setText(userInput);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            commandTextField.positionCaret(userInput.length());
                        }
                    });
                }
            }
        });
    }
    //@@author


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
        ObservableList<String> styleClass = commandTextField.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        styleClass.add(ERROR_STYLE_CLASS);
    }

}
