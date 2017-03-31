package seedu.tache.ui;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.fxml.FXML;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
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

    private final Logic logic;

    @FXML
    private TextField commandTextField;

    public CommandBox(AnchorPane commandBoxPlaceholder, Logic logic) {
        super(FXML);
        this.logic = logic;
        addToPlaceholder(commandBoxPlaceholder);
        setAutocomplete();
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

    //@@author A0142255M
    /**
     * Sets autocomplete functionality for user commands.
     * Uses ControlsFX Autocomplete TextField function.
     */
    private void setAutocomplete() {
        String[] possibleCommands = {"add ", "clear", "complete ", "delete ", "edit ", "exit", "find ",
                                        "help", "list", "save ", "select ", "load ", "undo" };
        //AutoCompletionBinding<String> binding = TextFields.bindAutoCompletion(commandTextField, possibleCommands);
        AutoCompletionBinding<String> binding = TextFields.bindAutoCompletion(commandTextField, sr -> {
            ArrayList<String> commands = new ArrayList<String>();
            for (String str : possibleCommands) {
                String userInput = sr.getUserText();
                if (!userInput.equals("") && str.startsWith(userInput)) {
                    commands.add(str);
                }
            }
            return commands;
        });
        binding.setMaxWidth(100);
        binding.setDelay(50);
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
        commandTextField.getStyleClass().add(ERROR_STYLE_CLASS);
    }

}
