package org.teamstbf.yats.ui;

import org.teamstbf.yats.commons.util.FxViewUtil;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

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
	private TableView<String> dayViewTable;
	@FXML
	private TableColumn<String, String> timeColumn;
	@FXML
	private TableColumn<String, String> eventsColumn;
	@FXML
	private Tab weekView;
	@FXML
	private Tab monthView;
	@FXML
	private Tab yearView;

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
		placeholder.getChildren().add(calendarView);
	}

	private void invokeTabPane() {
		dayView.setText("Day");
		weekView.setText("Week");
		monthView.setText("Month");
		yearView.setText("Year");
	}

	private void initializeDayView() {

	}

}
