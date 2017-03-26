package org.teamstbf.yats.ui;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.teamstbf.yats.commons.util.FxViewUtil;
import org.teamstbf.yats.model.item.ReadOnlyEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

//@@author A0138952W
/**
 * The Browser Panel of the App.
 */
public class CalendarViewPanel extends UiPart<Region> {

	private static final String FXML = "CalendarView.fxml";

	@FXML
	private AnchorPane calendarView;
	@FXML
	private TabPane dWMView;
	@FXML
	private Tab dayView;
	@FXML
	private TableView<String> timeList;
	@FXML
	private TableColumn<LocalDateTime, String> timeListColumn;
	@FXML
	private ListView<ReadOnlyEvent> taskListView;
	@FXML
	private Button leftArrow;
	@FXML
	private Button rightArrow;
	@FXML
	private Label currentDay;
	@FXML
	private Label currentMonth;
	@FXML
	private Label currentMonth2;
	@FXML
	private Label currentYear;
	@FXML
	private Tab weekView;
	@FXML
	private Tab monthView;
	@FXML
	private Tab yearView;

	private final LocalTime firstTimeSlot = LocalTime.of(0, 0);
	private final Duration timeDifference = Duration.ofMinutes(60);
	private final LocalTime lastTimeSlot = LocalTime.of(23, 00);

	private LocalDate today = LocalDate.now();
	private LocalDateTime startTime = today.atTime(firstTimeSlot);

	/**
	 * The AnchorPane where the CalendarView must be inserted
	 *
	 * @param placeholder
	 */
	public CalendarViewPanel(AnchorPane placeholder) {
		super(FXML);
		FxViewUtil.applyAnchorBoundaryParameters(calendarView, 0.0, 0.0, 0.0, 0.0);
		invokeTabPane();
		initializeDayView();
		initializeWeekView();
		initializeMonthView();
		initializeYearView();
		placeholder.getChildren().add(calendarView);
	}

	// Initialize headers for the tabs
	private void invokeTabPane() {
		dayView.setText("Day");
		weekView.setText("Week");
		monthView.setText("Month");
		yearView.setText("Year");
	}

	private void invokeArrowImage() {
		Image leftArrowImage = new Image(getClass().getResourceAsStream("arrow_left_48px.png"));
		leftArrow.setGraphic(new ImageView(leftArrowImage));
		Image rightArrowImage = new Image(getClass().getResourceAsStream("arrow_right_48px.png"));
		rightArrow.setGraphic(new ImageView(rightArrowImage));
	}

	private void initializeDayView() {

		ObservableList<String> timeData = FXCollections.observableArrayList();

		for (startTime = today.atTime(firstTimeSlot); !startTime
				.isAfter(today.atTime(lastTimeSlot)); startTime = startTime.plus(timeDifference)) {
			timeData.add(startTime.toString());
		}
		currentDay.setText(today.format(dateFormatter("d MMM")));
	}

	private void initializeWeekView() {
		currentMonth.setText(today.format(dateFormatter("MMM")));
	}

	private void initializeMonthView() {
		currentMonth2.setText(today.format(dateFormatter("MMM")));
	}

	private void initializeYearView() {
		currentYear.setText(today.format(dateFormatter("uuuu")));
	}

	private DateTimeFormatter dateFormatter(String toFormat) {
		return DateTimeFormatter.ofPattern(toFormat);
	}

	// ====== Inner classes to define calendar view ================

}
