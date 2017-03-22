package seedu.address.ui;

import java.util.HashMap;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.LeftPanelSelecttionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

public class LeftPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "LeftPanel.fxml";
    private HashMap<Label, Integer> labelCount;
    private ObservableList<ReadOnlyTask> taskList;
    private ObservableList<Label> labelList;

    @FXML
    private javafx.scene.control.Label appTitleLabel;

    @FXML
    private javafx.scene.control.Label todayLabel;

    @FXML
    private javafx.scene.control.Label todayCounterLabel;

    @FXML
    private javafx.scene.control.Label calendarLabel;

    @FXML
    private javafx.scene.control.Label labelCounterLabel;

    @FXML
    private javafx.scene.control.Label labelTitleLabel;

    @FXML
    private FontAwesomeIconView todayIconLabel;

    @FXML
    private FontAwesomeIconView calendarIconLabel;

    @FXML
    private FontAwesomeIconView labelArrow;

    @FXML
    private FontAwesomeIconView labelIconLabel;

    @FXML
    private ListView<Label> labelListView;

    public LeftPanel(AnchorPane leftListPlaceholder,
            ObservableList<ReadOnlyTask> taskList,
            ObservableList<Label> labelList) {
        super(FXML);
        this.taskList = taskList;
        this.labelList = labelList;
        initIcons();
        updateLabelCount();
        setTodayListView(taskList);
        setCalendarListView(taskList);
        addToPlaceholder(leftListPlaceholder);
        registerAsAnEventHandler(this);
    }

    public void updateLabelCount() {
        labelCount = new HashMap<Label, Integer>();
        //Set all to 0
        for (Label label : labelList) {
            labelCount.put(label, 0);
        }

        for (ReadOnlyTask task : taskList) {
            for (Label label : task.getLabels()) {
                labelCount.put(label, labelCount.get(label) + 1);
            }
        }

        setConnections(labelList);
    }

    private void initIcons() {
        todayIconLabel.setIcon(FontAwesomeIcon.CALENDAR_ALT);
        calendarIconLabel.setIcon(FontAwesomeIcon.CALENDAR);
        labelIconLabel.setIcon(FontAwesomeIcon.HASHTAG);
        labelArrow.setIcon(FontAwesomeIcon.ANGLE_UP);
    }

    public void setTodayListView(ObservableList<ReadOnlyTask> taskList) {
        todayLabel.setText("Today");
        todayCounterLabel.setText(Integer.toString(taskList.size()));
    }

    public void setCalendarListView(ObservableList<ReadOnlyTask> taskList) {
        calendarLabel.setText("Calendar");
    }

    public void setConnections(ObservableList<Label> labelList) {
        labelCounterLabel.setText(Integer.toString(labelList.size()));
        labelListView.setItems(labelList);
        labelListView.setCellFactory(listView -> new LabelListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    private void setEventHandlerForSelectionChangeEvent() {
        labelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in label left list panel changed to : '" + newValue + "'");
                raise(new LeftPanelSelecttionChangedEvent());
            }
        });
    }

    public void scrollTo(int index) {
        Platform.runLater(() -> {
            labelListView.scrollTo(index);
            labelListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent tmce) {
        updateLabelCount();
        logger.info(LogsCenter.getEventHandlingLogMessage(tmce, "Updating label list"));
    }

    class LabelListViewCell extends ListCell<Label> {

        @Override
        protected void updateItem(Label label, boolean empty) {
            super.updateItem(label, empty);
            if (empty || label == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new LabelCard(label, labelCount.get(label)).getRoot());
            }
        }
    }
}
