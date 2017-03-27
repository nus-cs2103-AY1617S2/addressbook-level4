package org.teamstbf.yats.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class TimeCard extends UiPart<Region> {

	@FXML
	private Label timeSlot;

	public TimeCard(String time, String FXML) {
		super(FXML);
		timeSlot.setText(time);
	}
}
