package seedu.address.ui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.TaskManagerChangedEvent;
import seedu.address.commons.events.ui.LeftPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.LeftPanelTodaySelectionChangedEvent;
import seedu.address.commons.util.FxViewUtil;
import seedu.address.model.label.Label;
import seedu.address.model.task.ReadOnlyTask;

public class LeftPanel extends UiPart<Region> {

    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);
    private static final String FXML = "LeftPanel.fxml";
    private HashMap<Label, Integer> labelCount;
    private ObservableList<ReadOnlyTask> taskList;
    private ObservableList<Label> labels;

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

    @FXML
    private HBox todayHeader;

    @FXML
    private HBox calendarHeader;

    //@@author A0162877N
    public LeftPanel(AnchorPane leftListPlaceholder,
            ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        this.taskList = taskList;
        initIcons();
        updateLabelCount();
        setTodayListView(taskList);
        setCalendarListView(taskList);
        addToPlaceholder(leftListPlaceholder);
        registerAsAnEventHandler(this);
        setEventHandlerForSelectionChangeEvent();
    }

    //@@author A0140042A
    public void updateLabelCount() {
        labelCount = new HashMap<Label, Integer>();

        for (ReadOnlyTask task : taskList) {
            for (Label label : task.getLabels()) {
                int currentCount = labelCount.get(label) == null ? 0 : labelCount.get(label);
                labelCount.put(label, currentCount + 1);
            }
        }

        setConnections(labelCount);
    }

    private void initIcons() {
        todayIconLabel.setIcon(FontAwesomeIcon.CALENDAR_ALT);
        calendarIconLabel.setIcon(FontAwesomeIcon.CALENDAR);
        labelIconLabel.setIcon(FontAwesomeIcon.HASHTAG);
        labelArrow.setIcon(FontAwesomeIcon.ANGLE_UP);
    }

    //@@author A0162877N
    @SuppressWarnings("deprecation")
    public void setTodayListView(ObservableList<ReadOnlyTask> taskList) {
        todayLabel.setText("Today");
        int count = 0;
        Date endTime = new Date(2222, 1, 1);
        Date startDate = new Date();
        endTime.setHours(23);
        endTime.setMinutes(59);
        endTime.setSeconds(59);
        startDate.setHours(0);
        startDate.setMinutes(0);
        startDate.setSeconds(0);

        // Add all tasks that is not completed and deadline is after today
        for (ReadOnlyTask task : taskList) {
            if (task.getDeadline().isPresent() && task.getStartTime().isPresent()) {
                if ((task.getDeadline().get().getDateTime().before(endTime)
                        && task.getDeadline().get().getDateTime().after(startDate))
                        || task.getDeadline().get().getDateTime().equals(endTime)) {
                    count++;
                }
            } else if (task.getDeadline().isPresent()) {
                if (task.getDeadline().get().getDateTime().before(endTime)
                        || task.getDeadline().get().getDateTime().equals(endTime)) {
                    count++;
                }
            }
        }
        todayCounterLabel.setText(Integer.toString(count));
        setEventHandlerForTodaySelectionChangeEvent();
    }

    //@@author A0140042A
    public void setCalendarListView(ObservableList<ReadOnlyTask> taskList) {
        calendarLabel.setText("Calendar");
    }

    public void setConnections(HashMap<Label, Integer> labelList) {
        labels = getLabelsWithCount(labelList);
        labelCounterLabel.setText(Integer.toString(labelList.size()));
        labelListView.setItems(labels);
        labelListView.setCellFactory(listView -> new LabelListViewCell());
    }

    /**
     * Returns labels with count more than 0, ignoring all empty labels
     */
    private ObservableList<Label> getLabelsWithCount(HashMap<Label, Integer> labelList) {
        labels = FXCollections.observableArrayList();
        for (Entry<Label, Integer> entry : labelList.entrySet()) {
            if (entry.getValue() > 0) {
                labels.add(entry.getKey());
            }
        }
        FXCollections.sort(labels);
        return labels;
    }

    /**
     * Sets the task list to the given task list
     */
    public void setTaskList(ObservableList<ReadOnlyTask> taskList) {
        this.taskList = taskList;
    }

    @FXML
    private void toggleLabelList() {
        labelListView.setVisible(!labelListView.isVisible());
        if (labelListView.isVisible()) {
            labelArrow.setIcon(FontAwesomeIcon.ANGLE_UP);
        } else {
            labelArrow.setIcon(FontAwesomeIcon.ANGLE_DOWN);
        }
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(getRoot());
    }

    //@@author A0162877N
    private void setEventHandlerForSelectionChangeEvent() {
        labelListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                logger.fine("Selection in label left list panel changed to : '" + newValue + "'");
                raise(new LeftPanelSelectionChangedEvent(newValue));
            }
        });
    }

    private void setEventHandlerForTodaySelectionChangeEvent() {
        todayHeader.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logger.fine("Selection in label left list panel changed to : 'Today'");
                raise(new LeftPanelTodaySelectionChangedEvent());
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
        setTodayListView(taskList);
        logger.info(LogsCenter.getEventHandlingLogMessage(tmce,
                "Updating label list count and total number of tasks for today"));
    }

    //@@author A0140042A
    class LabelListViewCell extends ListCell<Label> {

        @Override
        protected void updateItem(Label label, boolean empty) {
            super.updateItem(label, empty);
            if (empty || label == null) {
                setGraphic(null);
                setText(null);
            } else {
                Integer newCount = labelCount.get(label);
                if (newCount == null) {
                    newCount = 1;
                }
                setGraphic(new LabelCard(label, newCount).getRoot());
            }
        }
    }
}
