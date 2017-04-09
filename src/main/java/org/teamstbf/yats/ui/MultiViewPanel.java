package org.teamstbf.yats.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
	private static final String FXMLPERSON = "PersonListCard.fxml";

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
	@FXML
	private Button prevDate;
	@FXML
	private Button nextDate;
	@FXML
	private Label date;

	private static LocalDate today;
	private static DateTimeFormatter formatter;

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
	public MultiViewPanel(AnchorPane placeholder, ObservableList<ReadOnlyEvent> observableTaskList, Model model) {
		super(FXML);
		this.model = model;
		datepicker = new DatePicker(today);
		calendar = new DatePickerSkin(datepicker);
		today = LocalDate.now();
		formatter = DateTimeFormatter.ofPattern("d MMMM");
		setConnectionsCalendarView();
		setConnectionsDoneView(observableTaskList);
		addToPlaceholder(placeholder);
	}

	private void setConnectionsCalendarView() {
		Node popupContent = calendar.getPopupContent();
		calendarRoot.setCenter(popupContent);
		updateCurrentDay(today);
		updateCalendarList(today);
		timeTasks.setItems(timeData);
		timeTasks.setCellFactory(listView -> new TimeSlotListViewCell());
		setEventHandlerForSelectionChangeEvent();
	}

	private void setConnectionsDoneView(ObservableList<ReadOnlyEvent> observableTaskList) {
		taskListView.setItems(observableTaskList);
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
					setGraphic(new TaskCard(task, getIndex() + 1, FXMLPERSON).getRoot());
				}
			}
		}
	}

	// ================== Inner Methods for Calendar View ==================

	private void updateCalendarList(LocalDate day) {
		String[] data = new String[TASK_DETAILS];
		model.updateCalendarFilteredListToShowStartTime(day);
		calendarList = model.getCalendarFilteredTaskList();
		if (calendarList.size() == 0) {
			timeData.clear();
		} else {
			timeData.clear();
			for (int i = 0; i < calendarList.size(); i++) {
				ReadOnlyEvent event = calendarList.get(i);
				data[TASK_TITLE] = event.getTitle().toString();
				data[TASK_START] = event.getStartTime().toString();
				data[TASK_END] = event.getEndTime().toString();
				data[TASK_LOCATION] = event.getLocation().toString();
				timeData.add(data);
				String[] data1 = timeData.get(i);
				int j = 0;
				while (j != 4) {
					System.out.println(data1[j]);
					j++;
				}
			}
		}
	}

	public void prevDay() {
		MultiViewPanel.today = today.minusDays(1);
		datepicker.setValue(today);
		updateCalendarList(today);
		updateCurrentDay(today);
	}

	public void nextDay() {
		MultiViewPanel.today = today.plusDays(1);
		datepicker.setValue(today);
		updateCalendarList(today);
		updateCurrentDay(today);
	}

	public void resetDay() {
		MultiViewPanel.today = LocalDate.now();
		datepicker.setValue(today);
		updateCalendarList(today);
		updateCurrentDay(today);
	}

	public void updateCurrentDay(LocalDate day) {
		MultiViewPanel.today = day;
		date.setText(today.format(formatter));
	}

}
