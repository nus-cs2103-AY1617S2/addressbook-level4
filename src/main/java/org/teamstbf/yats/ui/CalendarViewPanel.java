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
public class CalendarViewPanel extends UiPart<Region> {

	protected Model model;

	private static final String FXML = "CalendarView.fxml";
	private static ObservableList<String[]> timeData = FXCollections.observableArrayList();

	private final FilteredList<ReadOnlyEvent> internalList;

	@FXML
	private AnchorPane calendarPanel;
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
	public CalendarViewPanel(AnchorPane placeholder, Model model) {
		super(FXML);
		this.model = model;
		internalList = new FilteredList<ReadOnlyEvent>(model.getFilteredTaskList());
		FxViewUtil.applyAnchorBoundaryParameters(calendarPanel, 0.0, 0.0, 0.0, 0.0);
		initializeCalendarView();
		placeholder.getChildren().add(calendarPanel);
	}

	private void initializeCalendarView() {

		DatePickerSkin calendar = new DatePickerSkin(new DatePicker(LocalDate.now()));
		Node popupContent = calendar.getPopupContent();
		calendarRoot.setCenter(popupContent);
		createFullDayTime();
	}

	// ========== Inner Class and Methods for calendar view ==========

	private void createFullDayTime() {
		// Fetch task list for today: using the list command to extract out the
		// tasks
		// Populate timeData observable list with the tasks with correct labels
		String[] data = new String[TASK_DETAILS];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String[] keyword = { today.plusDays(1).format(formatter) };
		Set<String> keywordSet = new HashSet<>(Arrays.asList(keyword));
		updateFilteredListToShowStartTime(keywordSet);
		for (int i = 0; i < internalList.size(); i++) {
			ReadOnlyEvent event = internalList.get(i);
			data[TASK_TITLE] = event.getTitle().toString();
			data[TASK_START] = event.getStartTime().toString();
			data[TASK_END] = event.getEndTime().toString();
			// data[TASK_LOCATION] = event.getLocation().toString();
			timeData.add(data);
		}
		timeTasks.setItems(timeData);
		timeTasks.setCellFactory(listView -> new TimeSlotListViewCell());
	}

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

	// ============ Inner Class for Information Extraction ============

	private void updateFilteredEventList(Expression expression) {
		internalList.setPredicate(expression::satisfies);
	}

	private void updateFilteredListToShowStartTime(Set<String> keywords) {
		updateFilteredEventList(new PredicateExpression(new StartTimeQualifier(keywords)));
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

}
