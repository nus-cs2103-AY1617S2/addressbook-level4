//@@author A0131125Y
package guitests.guihandles;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import seedu.toluist.TestApp;
import seedu.toluist.commons.util.StringUtil;

/**
 * Provide a handle for TabBarView
 */
public class TabBarViewHandle extends GuiHandle {
    private static final String TAB_BAR_VIEW_ID = "#tabContainer";

    public TabBarViewHandle(GuiRobot guiRobot, Stage primaryStage) {
        super(guiRobot, primaryStage, TestApp.APP_TITLE);
    }

    /**
     * Returns the string shown in the highlighted tab in the tab bar
     */
    public String getHighlightedTabText() {
        FlowPane root = getNode(TAB_BAR_VIEW_ID);
        return root.getChildren().stream()
                   .filter(hBox -> hBox.getStyleClass().contains("selected"))
                   .map(hBox -> Arrays.asList(((HBox) hBox).getChildren().toArray()))
                   .flatMap(List::stream)
                   .map(label -> ((Label) label).getText())
                   .collect(Collectors.joining(StringUtil.EMPTY_STRING));
    }
}
