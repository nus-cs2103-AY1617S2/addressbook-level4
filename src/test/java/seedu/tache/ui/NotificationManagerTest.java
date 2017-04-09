//@@author A0139961U
package seedu.tache.ui;

import static org.junit.Assert.assertNotSame;

import java.awt.SystemTray;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Timer;

import org.junit.Test;

import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.recurstate.RecurState.RecurInterval;
import seedu.tache.model.tag.UniqueTagList;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.model.task.Task;

public class NotificationManagerTest {

    @Test
    public void showSystemTrayNotification_validInput_success() throws IllegalValueException {
        if (SystemTray.isSupported()) {
            NotificationManager testNotification = new NotificationManager();
            Timer initialTimer = testNotification.getNotificationTimer();
            Task event = new Task(new Name("Event"), Optional.of(new DateTime("2 hours later")),
                    Optional.of(new DateTime("2 days later")), new UniqueTagList("TestTag"), true,
                    RecurInterval.NONE, new ArrayList<Date>());
            Task deadline = new Task(new Name("Deadline"), null,
                    Optional.of(new DateTime("2 hours later")), new UniqueTagList("TestTag"), true,
                    RecurInterval.NONE, new ArrayList<Date>());
            testNotification.showSystemTrayNotification(event, 0);
            testNotification.showSystemTrayNotification(deadline, 1);
            assertNotSame(testNotification.getNotificationTimer().toString(), initialTimer.toString());
            testNotification.stop();
            assertNotSame(testNotification.getNotificationTimer().toString(), initialTimer.toString());
        }
    }

}
