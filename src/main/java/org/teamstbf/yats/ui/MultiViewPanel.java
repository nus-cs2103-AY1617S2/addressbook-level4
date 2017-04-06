package org.teamstbf.yats.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.commons.util.StringUtil;
import org.teamstbf.yats.model.Model;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import edu.emory.mathcs.backport.java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

//@@author A0138952W
/**
 * The Browser Panel of the App.
 */
@SuppressWarnings("restriction")
public class MultiViewPanel extends UiPart<Region> {

	protected Model model;

	private static final String FXML = "CalendarView.fxml";
	private static ObservableList<String[]> timeData = FXCollections.observableArrayList();
	private static ObservableList<ReadOnlyEvent> taskData = FXCollections.observableArrayList();

	private FilteredList<ReadOnlyEvent> calendarList;
	private FilteredList<ReadOnlyEvent> taskList;
	private final DatePickerSkin calendar;

	private BrowserPanel browserPanel;

	@FXML
	private AnchorPane calendarPanel;
	@FXML
	private AnchorPane browserPlaceholder;
	@FXML
	private TabPane dWMView;
	@FXML
	private Tab doneTaskTab;
	@FXML
	private Tab browserTab;
	@FXML
	private Tab calendarViewTab;
	@FXML
	private BorderPane calendarRoot;
	@FXML
	private ListView<ReadOnlyEvent> taskListView;
	@FXML
	private ListView<String[]> timeTasks;

	private static LocalDate today = LocalDate.now();

	private static final String DONE_TASK_IDENTIFIER = "Yes";
	private static final String FXMLPERSONDONE = "PersonListCardDone.fxml";

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
		calendarList = new FilteredList<ReadOnlyEvent>(model.getFilteredTaskList());
		taskList = new FilteredList<ReadOnlyEvent>(model.getFilteredTaskList());
		calendar = new DatePickerSkin(new DatePicker(LocalDate.now()));
		FxViewUtil.applyAnchorBoundaryParameters(calendarPanel, 0.0, 0.0, 0.0, 0.0);
		initializeCalendarView();
		initializeDoneView();
		initializeBrowser();
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

	private void initializeBrowser() {
		browserPanel = new BrowserPanel(browserPlaceholder);
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

	private void updateCalendarFilteredEventList(Expression expression) {
		calendarList.setPredicate(expression::satisfies);
	}

	private void updateTaskFilteredEventList(Expression expression) {
		taskList.setPredicate(expression::satisfies);
	}

	@SuppressWarnings("unchecked")
	private void updateCalendarList() {
		String[] data = new String[TASK_DETAILS];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String[] keyword = { today.format(formatter) };
		Set<String> keywordSet = new HashSet<>(Arrays.asList(keyword));
		updateFilteredListToShowStartTime(keywordSet);
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
		taskList.setPredicate(null);
		Set<String> doneTaskIdentifier = new HashSet<String>();
		doneTaskIdentifier.add(DONE_TASK_IDENTIFIER);
		updateFilteredListToShowDone(doneTaskIdentifier);
		for (int i = 0; i < taskList.size(); i++) {
			taskData.add(taskList.get(i));
		}
	}

	private void updateFilteredListToShowStartTime(Set<String> keywords) {
		updateCalendarFilteredEventList(new PredicateExpression(new StartTimeQualifier(keywords)));
	}

	public void updateFilteredListToShowDone(Set<String> keywords) {
		updateTaskFilteredEventList(new PredicateExpression(new DoneQualifier(keywords)));
	}

	interface Qualifier {
		boolean run(ReadOnlyEvent event);

		@Override
		String toString();
	}

	interface Expression {
		boolean satisfies(ReadOnlyEvent event);

		@Override
		String toString();
	}

	private class PredicateExpression implements Expression {

		private final Qualifier qualifier;

		PredicateExpression(Qualifier qualifier) {
			this.qualifier = qualifier;
		}

		@Override
		public boolean satisfies(ReadOnlyEvent event) {
			return qualifier.run(event);
		}

		@Override
		public String toString() {
			return qualifier.toString();
		}
	}

	private class StartTimeQualifier implements Qualifier {

		private Set<String> startTimeKeyWords;

		StartTimeQualifier(Set<String> startTimeKeyWords) {
			this.startTimeKeyWords = startTimeKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return startTimeKeyWords.stream()
					.filter(keyword -> StringUtil.containsWordIgnoreCase(event.getStartTime().toString(), keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "startTime=" + String.join(", ", startTimeKeyWords);
		}
	}

	private class DoneQualifier implements Qualifier {

		private Set<String> doneKeyWords;

		DoneQualifier(Set<String> doneKeyWords) {
			this.doneKeyWords = doneKeyWords;
		}

		@Override
		public boolean run(ReadOnlyEvent event) {
			return doneKeyWords.stream()
					.filter(keyword -> StringUtil.containsWordIgnoreCase(event.getIsDone().getValue(), keyword))
					.findAny().isPresent();
		}

		@Override
		public String toString() {
			return "done=" + String.join(", ", doneKeyWords);
		}
	}

	void loadTaskPage(ReadOnlyEvent event) {
		browserPanel.loadPersonPage(event);
	}

	void releaseResources() {
		browserPanel.freeResources();
	}

}
