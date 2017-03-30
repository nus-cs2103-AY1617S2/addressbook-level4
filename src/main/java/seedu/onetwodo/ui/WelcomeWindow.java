//@@author A0141138N
package seedu.onetwodo.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import javafx.stage.Stage;

import seedu.onetwodo.commons.core.GuiSettings;
import seedu.onetwodo.logic.Logic;

public class WelcomeWindow extends UiPart<Region> {

    private static final String FXML = "WelcomeWindow.fxml";

    public TodayTaskList todayTaskListPanel;
    public Logic logic;

    @FXML
    private AnchorPane todayTaskListPanelPlaceholder;

    public WelcomeWindow(Logic logic) {
        super(FXML);

        // Set dependencies
        this.logic = logic;

        // Configure the UI
        fillInnerParts();
    }

    private void fillInnerParts() {
        todayTaskListPanel = new TodayTaskList(getTodayListPlaceholder(), logic.getFilteredTaskList());
        todayTaskListPanel.scrollTo(0);
    }

    private AnchorPane getTodayListPlaceholder() {
        return todayTaskListPanelPlaceholder;
    }

}
