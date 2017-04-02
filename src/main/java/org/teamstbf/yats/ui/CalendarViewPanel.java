package org.teamstbf.yats.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import com.sun.javafx.scene.control.skin.DatePickerSkin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
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
public class CalendarViewPanel extends UiPart<Region> {

	private static final String FXML = "CalendarView.fxml";
	private static ObservableList<String> timeData = FXCollections.observableArrayList();

	@FXML
	private AnchorPane calendarPanel;
	@FXML
	private TabPane dWMView;
	@FXML
	private Tab doneTask;
	@FXML
	private Tab browser;
	@FXML
	private Tab calendarView;
	@FXML
	private BorderPane calendarRoot;
	@FXML
	private ListView<ReadOnlyEvent> taskListView;
	@FXML
	private Button leftArrow;
	@FXML
	private Button rightArrow;
	@FXML
	private Label currentDay;
	@FXML
	private Label currentWeekMonth;
	@FXML
	private Label currentMonth;
	@FXML
	private Label currentYear;
	@FXML
	private ListView<String> timeSlots;

	private static LocalDate today = LocalDate.now();
	private final LocalTime firstTimeSlot = LocalTime.of(0, 0);
	private final Duration timeDifference = Duration.ofMinutes(60);
	private final LocalTime lastTimeSlot = LocalTime.of(23, 00);
	private LocalDateTime startTime = today.atTime(firstTimeSlot);

	/**
	 * The AnchorPane where the CalendarView must be inserted
	 *
	 * @param placeholder
	 */
	public CalendarViewPanel(AnchorPane placeholder) {
		super(FXML);
		FxViewUtil.applyAnchorBoundaryParameters(calendarPanel, 0.0, 0.0, 0.0, 0.0);
		invokeArrowImage();
		initializeCalendarView();
		placeholder.getChildren().add(calendarPanel);
	}

	private void invokeArrowImage() {
		leftArrow.setText("<<");
		rightArrow.setText(">>");
	}

	@SuppressWarnings("restriction")
	private void initializeCalendarView() {

		DatePickerSkin calendar = new DatePickerSkin(new DatePicker(LocalDate.now()));
		Node popupContent = calendar.getPopupContent();
		calendarRoot.setCenter(popupContent);

		for (startTime = today.atTime(firstTimeSlot); !startTime
				.isAfter(today.atTime(lastTimeSlot)); startTime = startTime.plus(timeDifference)) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ha");
			timeData.add(startTime.format(formatter));
		}
		createFullDayTime();
		currentDay.setText(today.format(dateFormatter("d MMM")));
	}

	// ======= Utility methods for the Button Bar ==========

	private DateTimeFormatter dateFormatter(String toFormat) {
		return DateTimeFormatter.ofPattern(toFormat);
	}

	public void nextDay() {
		today = today.plusDays(1);
		currentDay.setText(today.format(dateFormatter("d MMM")));
	}

	public void prevDay() {
		today = today.minusDays(1);
		currentDay.setText(today.format(dateFormatter("d MMM")));
	}

	public void nextWeekMonth() {
		today = today.plusMonths(1);
		currentWeekMonth.setText(today.format(dateFormatter("MMM")));
	}

	public void prevWeekMonth() {
		today = today.minusMonths(1);
		currentWeekMonth.setText(today.format(dateFormatter("MMM")));
	}

	public void nextMonth() {
		today = today.plusMonths(1);
		currentMonth.setText(today.format(dateFormatter("MMM")));
	}

	public void prevMonth() {
		today = today.minusMonths(1);
		currentMonth.setText(today.format(dateFormatter("MMM")));
	}

	public void nextYear() {
		today = today.plusYears(1);
		currentYear.setText(today.format(dateFormatter("uuuu")));
	}

	public void prevYear() {
		today = today.minusYears(1);
		currentYear.setText(today.format(dateFormatter("uuuu")));
	}

	public void resetTodayDay() {
		today = LocalDate.now();
		currentDay.setText(today.format(dateFormatter("d MMM")));
	}

	public void resetTodayWeek() {
		today = LocalDate.now();
		currentWeekMonth.setText(today.format(dateFormatter("MMM")));
	}

	public void resetTodayMonth() {
		today = LocalDate.now();
		currentMonth.setText(today.format(dateFormatter("MMM")));
	}

	public void resetTodayYear() {
		today = LocalDate.now();
		currentYear.setText(today.format(dateFormatter("uuuu")));
	}

	// ========== Inner Class and Methods for calendar view ==========

	private void populateMonth() {
		LocalDate calendarDate = LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth());
	}

	private void createFullDayTime() {
		timeSlots.setItems(timeData);
		timeSlots.setCellFactory(listView -> new TimeSlotListViewCell());
	}

	private class TimeSlotListViewCell extends ListCell<String> {

		@Override
		protected void updateItem(String time, boolean empty) {
			super.updateItem(time, empty);

			if (empty || (time == null && time.isEmpty())) {
				setGraphic(null);
				setText(null);
			} else {
				setGraphic(new TimeCard(time).getRoot());
			}
		}
	}

}
