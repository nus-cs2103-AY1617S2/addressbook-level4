package org.teamstbf.yats.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import org.teamstbf.yats.commons.core.LogsCenter;
import org.teamstbf.yats.commons.events.ui.EventPanelSelectionChangedEvent;
import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.model.Model;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

//@@author A0138952W
@SuppressWarnings("restriction")
public class MultiViewPanel extends UiPart<Region> {

    protected Model model;

    private final Logger logger = LogsCenter.getLogger(TaskListPanel.class);

    private static final String FXML = "CalendarView.fxml";
    private static final String FXMLPERSONDONE = "PersonListCardDone.fxml";

    private static ObservableList<String[]> timeData = FXCollections.observableArrayList();
    private ObservableList<ReadOnlyEvent> calendarList;

    private final DatePickerSkin calendar;

    @FXML
    private AnchorPane calendarPanel;
    @FXML
    private BorderPane calendarRoot;
    @FXML
    private DatePicker datepicker;
    @FXML
    private ListView<ReadOnlyEvent> taskListView;
    @FXML
    private ListView<String[]> timeTasks;

    private static LocalDate today = LocalDate.now();

    private static final int TASK_DETAILS = 4;
    private static final int TASK_TITLE = 0;
    private static final int TASK_START = 1;
    private static final int TASK_END = 2;
    private static final int TASK_LOCATION = 3;

    /**
     * The AnchorPane where the CalendarView must be inserted
     *
     * @param placeholder
     */
    public MultiViewPanel(AnchorPane placeholder, ObservableList<ReadOnlyEvent> observableList, Model model) {
	super(FXML);
	this.model = model;
	datepicker = new DatePicker(today);
	calendar = new DatePickerSkin(datepicker);
	setConnectionsCalendarView();
	setConnectionsDoneView(observableList);
	addToPlaceholder(placeholder);
    }

    private void setConnectionsCalendarView() {
	Node popupContent = calendar.getPopupContent();
	calendarRoot.setCenter(popupContent);
	createFullDayTime();
    }

    private void setConnectionsDoneView(ObservableList<ReadOnlyEvent> observableList) {
	taskListView.setItems(observableList);
	taskListView.setCellFactory(listView -> new TaskListViewCell());
	setEventHandlerForSelectionChangeEvent();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
	FxViewUtil.applyAnchorBoundaryParameters(calendarPanel, 0.0, 0.0, 0.0, 0.0);
	placeHolderPane.getChildren().add(calendarPanel);
    }

    private void setEventHandlerForSelectionChangeEvent() {
	taskListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
	    if (newValue != null) {
		logger.fine("Selection in task list panel changed to : '" + newValue + "'");
		raise(new EventPanelSelectionChangedEvent(newValue));
	    }
	});
    }

    public void scrollTo(int index) {
	Platform.runLater(() -> {
	    taskListView.scrollTo(index);
	    taskListView.getSelectionModel().clearAndSelect(index);
	});
    }

    // ================== Inner Class for CalendarView ==================

    private class TimeSlotListViewCell extends ListCell<String[]> {

	@Override
	protected void updateItem(String[] taskSlot, boolean empty) {
	    super.updateItem(taskSlot, empty);

	    if (empty || (taskSlot == null)) {
		setGraphic(null);
		setText(null);
	    } else {
		setGraphic(new TimeCard(taskSlot).getRoot());
	    }
	}
    }

    private class TaskListViewCell extends ListCell<ReadOnlyEvent> {

	@Override
	protected void updateItem(ReadOnlyEvent task, boolean empty) {
	    super.updateItem(task, empty);

	    if (empty || task == null) {
		setGraphic(null);
		setText(null);
	    } else {
		if (task.getIsDone().getValue().equals("Yes")) {
		    setGraphic(new TaskCard(task, getIndex() + 1, FXMLPERSONDONE).getRoot());
		}
	    }
	}
    }

    // ================== Inner Methods for Calendar View ==================

    private void createFullDayTime() {
	updateCalendarList();
	timeTasks.setItems(timeData);
	timeTasks.setCellFactory(listView -> new TimeSlotListViewCell());
	getSelectedDate();
    }

    private void updateCalendarList() {
	String[] data = new String[TASK_DETAILS];
	model.updateCalendarFilteredListToShowStartTime();
	calendarList = model.getCalendarFilteredTaskList();
	for (int i = 0; i < calendarList.size(); i++) {
	    ReadOnlyEvent event = calendarList.get(i);
	    data[TASK_TITLE] = event.getTitle().toString();
	    data[TASK_START] = event.getStartTime().toString();
	    data[TASK_END] = event.getEndTime().toString();
	    data[TASK_LOCATION] = event.getLocation().toString();
	    timeData.add(data);
	}
    }

    public LocalDate getSelectedDate() {
	datepicker.setOnAction(event -> {
	    LocalDate date = datepicker.getValue();
	    System.out.println(date);
	});
	return datepicker.getValue();
    }

}
