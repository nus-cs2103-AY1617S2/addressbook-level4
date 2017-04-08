package onlythree.imanager.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import onlythree.imanager.commons.util.FxViewUtil;

//@@author A0135998H
public class Clock extends UiPart<Region> {
    private static final String FXML = "Clock.fxml";
    private static final int CLOCK_RENDERING_DELAY = 500; // msec

    private DateTimeFormatter formatter;

    @FXML
    private Label clockLabel;

    public Clock(AnchorPane clockPlaceholder) {
        super(FXML);
        formatter = DateTimeFormatter.ofPattern("d MMM EEEE, h:mm a");
        addToPlaceholder(clockPlaceholder);
        clockLabel.setText(formattedDate());
        Timeline timer = new Timeline(new KeyFrame(Duration.millis(CLOCK_RENDERING_DELAY), event -> {
            clockLabel.setText(formattedDate());
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }

    private void addToPlaceholder(AnchorPane placeHolderPane) {
        SplitPane.setResizableWithParent(placeHolderPane, false);
        FxViewUtil.applyAnchorBoundaryParameters(getRoot(), 0.0, 0.0, 0.0, 0.0);
        FxViewUtil.applyAnchorBoundaryParameters(clockLabel, 0.0, 0.0, 0.0, 0.0);
        placeHolderPane.getChildren().add(clockLabel);
    }

    public String formattedDate() {
        return formatter.format((LocalDateTime.now()));
    }

}

