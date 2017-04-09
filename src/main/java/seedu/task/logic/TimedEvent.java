package seedu.task.logic;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import javafx.collections.ObservableList;
import seedu.task.commons.util.NotificationUtil;
import seedu.task.model.task.Deadline;
import seedu.task.model.task.ReadOnlyTask;

//@@author A0141928B
public class TimedEvent extends TimerTask {
    private String message;
    public Timer timer;
    private int period;
    public ObservableList<ReadOnlyTask> tasks;

    public TimedEvent(ObservableList<ReadOnlyTask> tasks, int interval) {
        this.tasks = tasks;
        this.period = interval;
    }

    public void updateMessage() {
        this.message = "";

        Date today = new Date();
        String date1 = new SimpleDateFormat("dd-MMM-yy").format(today);
        String date2 = new SimpleDateFormat("dd-MMM-yyyy").format(today);

        Deadline deadline;

        Iterator<ReadOnlyTask> i = tasks.iterator();
        while (i.hasNext()) {
            ReadOnlyTask current = i.next();
            deadline = current.getDate();
            if (deadline.value.equals(date1) || deadline.value.equals(date2)) {
                this.message = current.getTaskName().toString();
                break;
            }
        }
    }

    public void start() {
        timer = new Timer(true);
        timer.scheduleAtFixedRate(this, 0, period);
    }

    @Override
    public void run() {
        updateMessage();
        if (!message.isEmpty()) {
            NotificationUtil notification = new NotificationUtil();
            notification.displayNotification(this.message + " is due today!");
        }
    }
}
