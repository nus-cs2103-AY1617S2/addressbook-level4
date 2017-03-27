package org.teamstbf.yats.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class TimeCard extends UiPart<Region> {

	private static final String FXML = "TimeCard.fxml";

	@FXML
	private HBox cardPane;
	@FXML
	private Label timeSlot;

	public TimeCard(String time) {
		super(FXML);
		timeSlot.setText(time);
	}
}
