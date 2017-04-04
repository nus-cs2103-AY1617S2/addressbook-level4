package seedu.taskit.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.events.ui.MenuBarPanelSelectionChangedEvent;
import seedu.taskit.commons.util.FxViewUtil;

//@@author A0141872E
public class MenuBarPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(MenuBarPanel.class);
    private static final String FXML = "MenuBarPanel.fxml";
    
    public static final String MENU_FLOATING_TASK = "Floating Tasks";
    public static final String MENU_EVENT_TASK = "Event Tasks";
    public static final String MENU_DEADLINE_TASK = "Deadline Tasks";
    public static final String MENU_TODAY_TASK = "Today Tasks";
    public static final String MENU_OVERDUE_TASK = "Overdue Tasks";
    
    private static final String FLOATING_TASK_ICON_PATH = "/images/floatingtask_icon.png";
    private static final String EVENT_TASK_ICON_PATH = "/images/event_icon.png";
    private static final String DEADLINE_TASK_ICON_PATH = "/images/deadline_icon.png";
    private static final String TODAY_TASK_ICON_PATH = "/images/today_icon.png";
    private static final String TODAY_OVERDUE_ICON_PATH = "/images/overdue_icon.png";
    
    @FXML
    private ListView<String> menuBarView;
    
    private final ObservableList<String> menuBarItems = FXCollections.observableArrayList(MENU_FLOATING_TASK, 
            MENU_EVENT_TASK, MENU_DEADLINE_TASK, MENU_TODAY_TASK,MENU_OVERDUE_TASK);
    private final String[] iconPaths = {FLOATING_TASK_ICON_PATH,EVENT_TASK_ICON_PATH, DEADLINE_TASK_ICON_PATH,
            TODAY_TASK_ICON_PATH,TODAY_OVERDUE_ICON_PATH};

    public MenuBarPanel(AnchorPane menuBarPanelPlaceholder) {
        super(FXML);
        setConnection();
        addToPlaceHolder(menuBarPanelPlaceholder);
    }

    private void setConnection() {
        menuBarView.setItems(menuBarItems); 
        menuBarView.setCellFactory(listView -> new MenuBarCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        menuBarView.getSelectionModel().selectedItemProperty()
        .addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in Menu Bar panel changed to : '" + newValue + "'");
                raise(new MenuBarPanelSelectionChangedEvent(newValue));
            }
        });
    }

    private void addToPlaceHolder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }
    
    class MenuBarCell extends ListCell<String> {
        @Override
        protected void updateItem(String label, boolean empty) {
            super.updateItem(label, empty);

            if (empty || label == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MenuBarCard(label,iconPaths[getIndex()]).getRoot());
            }
        }
    }

}
