//@@author A0131125Y
package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.toluist.TestApp;

/**
 * A handle to the Command autocomplet view in the GUI.
 */
public class CommandAutoCompleteViewHandle extends GuiHandle {
    private static final String COMMAND_AUTO_COMPLETE_VIEW_ID = "#suggestedCommandList";

    public CommandAutoCompleteViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    public List<String> getSuggestions() {
        VBox root = getNode(COMMAND_AUTO_COMPLETE_VIEW_ID);
        return root.getChildren().stream()
                .map(label -> ((Label) label).getText()).collect(Collectors.toList());
    }
}
