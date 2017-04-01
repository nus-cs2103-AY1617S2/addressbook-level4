package seedu.taskit.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.taskit.commons.core.LogsCenter;
import seedu.taskit.commons.events.ui.MenuBarPanelSelectionChangedEvent;
import seedu.taskit.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.taskit.logic.commands.ListCommand;
import seedu.taskit.model.task.ReadOnlyTask;
import seedu.taskit.ui.TaskListPanel.TaskListViewCell;

//@@author A0141872E
public class MenuBarPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(MenuBarPanel.class);
    private static final String FXML = "MenuBarPanel.fxml";
    
    private HBox panel;
    
    private String command = null;
    private final String MENU_FLOATING_TASK = "Floating Tasks";
    private final String MENU_EVENT_TASK = "Event Tasks";
    private final String MENU_DEADLINE_TASK = "Deadline Tasks";
    private final String MENU_TODAY_TASK = "Today Tasks";
    
    @FXML
    private ListView<String> menuBarView;
    
    private final ObservableList<String> menuBarItems = FXCollections.observableArrayList(MENU_FLOATING_TASK, 
            MENU_EVENT_TASK, MENU_DEADLINE_TASK, MENU_TODAY_TASK);

    public MenuBarPanel(AnchorPane menuBarPanelPlaceholder) {
        super(FXML);
        setConnection();
        addToPlaceHolder(menuBarPanelPlaceholder);
    }

    private void setConnection() {
        menuBarView.setItems(menuBarItems); 
        menuBarView.setCellFactory(listView -> new MenuBarViewCell());
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
        placeHolderPane.getChildren().add(panel);
    }
    
    public String getNavigationCommand(String menuItem) {
        switch (menuItem) {
            case MENU_FLOATING_TASK:
                command = ListCommand.COMMAND_WORD + " floating";
                return command;
            case MENU_EVENT_TASK:
                command = ListCommand.COMMAND_WORD + " event";
                return command;
            case MENU_DEADLINE_TASK:
                command = ListCommand.COMMAND_WORD + " deadline";
                return command;
            case MENU_TODAY_TASK:
                command = ListCommand.COMMAND_WORD + " today";
                return command;
            default:
                return ListCommand.COMMAND_WORD + " all";
        }
    }
    
    class MenuBarViewCell extends ListCell<String> {

        @Override
        protected void updateItem(String li, boolean empty) {
            super.updateItem(li, empty);

            if (empty || li == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MenuBarCard(li).getRoot());
            }
        }
    }

}
