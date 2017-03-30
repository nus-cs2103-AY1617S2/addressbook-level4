//@@author A0141138N
package seedu.onetwodo.ui;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

import seedu.onetwodo.logic.Logic;

public class WelcomeWindow extends UiPart<Region> {

    private static final String FXML = "WelcomeWindow.fxml";
    public static final String WELCOME = "Hey there! Here are your tasks for the day!";
    public static final String DEFAULT = "Hurray! You have no tasks for the day!";

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
