package seedu.tache.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;

/**
 * The Calendar Panel of the App.
 */
public class CalendarPanel extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(CalendarPanel.class);
    private static final String FXML = "CalendarPanel.fxml";

    @FXML
    private WebView calendar;

    /**
     * @param placeholder The AnchorPane where the CalendarPanel must be inserted
     */
    public CalendarPanel(AnchorPane placeholder, ObservableList<ReadOnlyTask> taskList) {
        super(FXML);
        placeholder.setOnKeyPressed(Event::consume); // To prevent triggering events for typing inside the
                                                     // loaded Web page.
        FxViewUtil.applyAnchorBoundaryParameters(calendar, 0.0, 0.0, 0.0, 0.0);
        placeholder.getChildren().add(calendar);
        registerAsAnEventHandler(this);
        loadCalendar(taskList);
    }

    //@@author A0139925U
    private void loadCalendar(ObservableList<ReadOnlyTask> taskList) {
        try {
            String calendarTemplate = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")
                    + "/src/main/resources/html/calendar.html")));
            WebEngine engine = calendar.getEngine();
            engine.loadContent(calendarTemplate);
            for (ReadOnlyTask task : taskList) {
                if (!task.getStartDateTime().isPresent()) {
                    continue;
                }
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                        @Override
                        public void changed(ObservableValue<? extends Worker.State> observable,
                                                Worker.State oldValue, Worker.State newValue) {
                            if (newValue != Worker.State.SUCCEEDED) {
                                return;
                            }
                            engine.executeScript(getLoadTaskExecuteScript(task));
                        }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getLoadTaskExecuteScript(ReadOnlyTask task) {
        String title = task.getName().toString();
        String start = task.getStartDateTime().get().getDateTimeForFullCalendar();
        String end = "0"; // invalid format of datetime
        Optional<DateTime> endDateTime = task.getEndDateTime();
        if (task.getEndDateTime().isPresent()) {
            end = endDateTime.get().getDateTimeForFullCalendar();
        }
        String isActive = String.valueOf(task.getActiveStatus());
        return "add_event('" + title + "', '" + start + "', '" + end + "', '" + isActive + "')";

    }
    //@@author A0142255M
    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        ObservableList<ReadOnlyTask> taskList = event.data.getTaskList();
        WebEngine engine = calendar.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                calendar.getEngine().executeScript("'remove_all'");
            }
        });
        loadCalendar(taskList);
    }
    //@@author

    /**
     * Frees resources allocated to the calendar.
     */
    public void freeResources() {
        calendar = null;
    }

}
