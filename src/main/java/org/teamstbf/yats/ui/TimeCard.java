package org.teamstbf.yats.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class TimeCard extends UiPart<Region> {

	private static final String FXML = "TimeCard.fxml";

	private static final int TASK_TITLE = 0;
	private static final int TASK_TIME_START = 1;
	private static final int TASK_TIME_END = 2;
	private static final int TASK_LOCATION = 3;

	@FXML
	private HBox cardPane;
	@FXML
	private Label taskStartTime;
	@FXML
	private Label taskEndTime;
	@FXML
	private Label taskTitle;
	@FXML
	private Label taskLocation;

	public TimeCard(String[] taskSlot) {
		super(FXML);
		taskStartTime.setText(taskSlot[TASK_TIME_START]);
		taskEndTime.setText(taskSlot[TASK_TIME_END]);
		taskTitle.setText(taskSlot[TASK_TITLE]);
		taskLocation.setText(taskSlot[TASK_LOCATION]);
	}
}
