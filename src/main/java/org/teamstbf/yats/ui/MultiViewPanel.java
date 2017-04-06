package org.teamstbf.yats.ui;

import java.time.LocalDate;

import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.model.Model;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

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

    private static final String FXML = "CalendarView.fxml";
    private static final String FXMLPERSONDONE = "PersonListCardDone.fxml";

    private static ObservableList<String[]> timeData = FXCollections.observableArrayList();
    private static ObservableList<ReadOnlyEvent> taskData = FXCollections.observableArrayList();

    private ObservableList<ReadOnlyEvent> calendarList;
    private ObservableList<ReadOnlyEvent> taskList;

    private final DatePickerSkin calendar;

    @FXML
    private AnchorPane calendarPanel;
    @FXML
    private BorderPane calendarRoot;
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
    public MultiViewPanel(AnchorPane placeholder, Model model) {
	super(FXML);
	this.model = model;
	calendar = new DatePickerSkin(new DatePicker(LocalDate.now()));
	FxViewUtil.applyAnchorBoundaryParameters(calendarPanel, 0.0, 0.0, 0.0, 0.0);
	initializeCalendarView();
	initializeDoneView();
	placeholder.getChildren().add(calendarPanel);
    }

    private void initializeCalendarView() {
	Node popupContent = calendar.getPopupContent();
	calendarRoot.setCenter(popupContent);
	createFullDayTime();
    }

    private void initializeDoneView() {
	updateTaskList();
	taskListView.setItems(taskData);
	taskListView.setCellFactory(listView -> new TaskListViewCell());
    }

    private void createFullDayTime() {
	updateCalendarList();
	timeTasks.setItems(timeData);
	timeTasks.setCellFactory(listView -> new TimeSlotListViewCell());
    }

    // =============== Inner Class for CalendarView ==================

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

    // ============ Inner Methods for Information Extraction ============

    @SuppressWarnings("unchecked")
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

    private void updateTaskList() {
	model.updateTaskFilteredListToShowDone();
	taskList = model.getTaskFilteredTaskList();
	for (int i = 0; i < taskList.size(); i++) {
	    taskData.add(taskList.get(i));
	}
    }

}
