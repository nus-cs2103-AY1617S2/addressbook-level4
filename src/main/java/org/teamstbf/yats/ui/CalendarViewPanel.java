package org.teamstbf.yats.ui;

import org.teamstbf.yats.commons.util.FxViewUtil;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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

	// ====== Inner classes to define calendar view ================

	private class InnerTime {

		private final StringProperty time;

		/**
		 * Default constructor
		 */
		public InnerTime() {
			this(null);
		}

		/**
		 * Constructor with some initial data
		 *
		 * @param time
		 */
		public InnerTime(String time) {
			this.time = new SimpleStringProperty(time);
		}

		public String getTime() {
			return time.get();
		}

		public StringProperty timeProperty() {
			return time;
		}
	}
}
