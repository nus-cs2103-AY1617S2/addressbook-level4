package seedu.tache.ui;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.beans.property.ReadOnlyObjectProperty;
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
import seedu.tache.MainApp;
import seedu.tache.commons.core.LogsCenter;
import seedu.tache.commons.events.model.TaskManagerChangedEvent;
import seedu.tache.commons.events.ui.CalendarNextRequestEvent;
import seedu.tache.commons.events.ui.CalendarPreviousRequestEvent;
import seedu.tache.commons.events.ui.TaskListTypeChangedEvent;
import seedu.tache.commons.events.ui.TaskPanelSelectionChangedEvent;
import seedu.tache.commons.util.FxViewUtil;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.ReadOnlyTask;
import seedu.tache.model.task.Task;

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

    //@@author A0142255M
    private void loadCalendar(ObservableList<ReadOnlyTask> taskList) {
        String calendarURL = MainApp.class.getResource("/html/calendar.html").toExternalForm();
        WebEngine engine = calendar.getEngine();
        engine.load(calendarURL);
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            addAllEvents(taskList);
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    addAllEvents(taskList);
                }
            });
        }
    }

    private String getLoadTaskExecuteScript(ReadOnlyTask task) {
        String title = task.getName().toString();
        String start = "0";
        String end = "0"; // invalid format of datetime
        Optional<DateTime> startDateTime = task.getStartDateTime();
        Optional<DateTime> endDateTime = task.getEndDateTime();
        if (task.getStartDateTime().isPresent()) {
            start = startDateTime.get().getDateTimeForFullCalendar();
        }
        if (task.getEndDateTime().isPresent()) {
            end = endDateTime.get().getDateTimeForFullCalendar();
        }
        String status = "uncompleted";
        if (task.getActiveStatus() == false) {
            status = "completed";
        } else if (task.getEndDateTime().isPresent()) {
            if (task.getEndDateTime().get().hasPassed()) {
                status = "overdue";
            }
        } else if (task.getStartDateTime().isPresent()) {
            if (task.getStartDateTime().get().hasPassed()) {
                status = "overdue";
            }
        }
        return "add_event('" + title + "', '" + start + "', '" + end + "', '" + status + "')";
    }

    @Subscribe
    public void handleTaskManagerChangedEvent(TaskManagerChangedEvent event) {
        ObservableList<ReadOnlyTask> taskList = event.data.getTaskList();
        refreshCalendar(taskList);
    }

    @Subscribe
    public void handleTaskPanelSelectionChangedEvent(TaskPanelSelectionChangedEvent event) {
        Optional<DateTime> startDateTime = event.getNewSelection().getStartDateTime();
        Optional<DateTime> endDateTime = event.getNewSelection().getEndDateTime();
        if (startDateTime.isPresent()) {
            changeReferenceDate(startDateTime.get().getDateTimeForFullCalendar());
        } else if (endDateTime.isPresent()) {
            changeReferenceDate(endDateTime.get().getDateTimeForFullCalendar());
        }
    }

    @Subscribe
    public void handleTaskListTypeChangedEvent(TaskListTypeChangedEvent event) {
        String taskListType = event.getTaskListType();
        if (taskListType.equals("Tasks Due Today") || taskListType.equals("Tasks Due This Week")) {
            Date today = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String referenceDate = sdf.format(today);
            changeReferenceDate(referenceDate);
            if (taskListType.equals("Tasks Due Today")) {
                changeView("day");
            } else {
                changeView("week");
            }
        } else {
            changeView("month");
        }
    }

    @Subscribe
    public void handleCalendarPreviousRequestEvent(CalendarPreviousRequestEvent event) {
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            engine.executeScript("prev()");
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    engine.executeScript("prev()");
                }
            });
        }
    }

    @Subscribe
    public void handleCalendarNextRequestEvent(CalendarNextRequestEvent event) {
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            engine.executeScript("next()");
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    engine.executeScript("next()");
                }
            });
        }
    }

    private void changeView(String view) {
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            engine.executeScript("change_view('" + view + "')");
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    engine.executeScript("change_view('" + view + "')");
                }
            });
        }
    }

    private void changeReferenceDate(String referenceDate) {
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            engine.executeScript("change_reference_date('" + referenceDate + "')");
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    engine.executeScript("change_reference_date('" + referenceDate + "')");
                }
            });
        }
    }

    private String getRemoveAllTaskScript() {
        return "remove_all()";
    }

    private void refreshCalendar(ObservableList<ReadOnlyTask> taskList) {
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            removeAllEvents();
            addAllEvents(taskList);
        } else {
            engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                @Override
                public void changed(ObservableValue<? extends Worker.State> observable,
                        Worker.State oldValue, Worker.State newValue) {
                    if (newValue != Worker.State.SUCCEEDED) {
                        return;
                    }
                    removeAllEvents();
                    addAllEvents(taskList);
                }
            });
        }
    }

    private void removeAllEvents() {
        WebEngine engine = calendar.getEngine();
        engine.executeScript(getRemoveAllTaskScript());
    }

    /**
     * Inputs all timed events in task list to calendar.
     * For deadline tasks (only have end but no start date/time), convert end date/time to start date/time.
     */
    public void addAllEvents(ObservableList<ReadOnlyTask> taskList) {
        for (ReadOnlyTask task : taskList) {
            if (task.getTimedStatus()) {
                if (!task.getStartDateTime().isPresent()) {
                    Task newTask = new Task(task);
                    newTask.setStartDateTime(newTask.getEndDateTime());
                    newTask.setEndDateTime(Optional.empty());
                    task = newTask;
                }
                if (task.isMasterRecurring()) {
                    continue;
                }
                addCurrentEvent(task);
            }
        }
    }

    private void addCurrentEvent(ReadOnlyTask task) {
        WebEngine engine = calendar.getEngine();
        ReadOnlyObjectProperty<Worker.State> webViewState = engine.getLoadWorker().stateProperty();
        if (webViewState.get() == Worker.State.SUCCEEDED) {
            engine.executeScript(getLoadTaskExecuteScript(task));
        } else {
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
    }
    //@@author

    /**
     * Frees resources allocated to the calendar.
     */
    public void freeResources() {
        calendar = null;
    }

}
