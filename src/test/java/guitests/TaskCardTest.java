//@@author A0142255M

package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import guitests.guihandles.TaskCardHandle;
import javafx.scene.image.Image;
import seedu.tache.MainApp;
import seedu.tache.commons.exceptions.IllegalValueException;
import seedu.tache.model.task.DateTime;
import seedu.tache.model.task.Name;
import seedu.tache.testutil.TestTask;

public class TaskCardTest extends TaskManagerGuiTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void taskCard_id_success() {
        TestTask[] allTasks = td.getTypicalTasks();
        for (int i = 0; i < allTasks.length; i++) {
            TestTask taskToShow = allTasks[i];
            TaskCardHandle card = taskListPanel.navigateToTask(taskToShow.getName().fullName);
            assertEquals(card.getId(), (i + 1) + ". ");
        }
    }

    @Test
    public void taskCard_taggedTask_success() {
        TestTask taskToShow = td.eggsAndBread;
        TaskCardHandle originalCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(originalCard.getTags().toString(), taskToShow.getTags().toString());
    }

    @Test
    public void taskCard_taskWithNoStartDate_failure() {
        // task with no start date and time
        TestTask taskToShow = td.payDavid;
        TaskCardHandle floatingCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        thrown.expect(NoSuchElementException.class);
        floatingCard.getStartDate();
        fail();
    }

    @Test
    public void taskCard_taskWithNoStartTime_failure() {
        // task with no start date and time
        TestTask taskToShow = td.payDavid;
        TaskCardHandle floatingCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        thrown.expect(NoSuchElementException.class);
        floatingCard.getStartTime();
        fail();
    }

    @Test
    public void taskCard_taskWithNoEndDate_failure() {
        // task with no end date and time
        TestTask taskToShow = td.payDavid;
        TaskCardHandle floatingCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        thrown.expect(NoSuchElementException.class);
        floatingCard.getEndDate();
        fail();
    }

    @Test
    public void taskCard_taskWithNoEndTime_failure() {
        // task with no end date and time
        TestTask taskToShow = td.payDavid;
        TaskCardHandle floatingCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        thrown.expect(NoSuchElementException.class);
        floatingCard.getEndTime();
        fail();
    }

    @Test
    public void taskCard_timedTask_success() {
        // task with start date and time only
        TestTask taskToShow = td.eggsAndBread;
        TaskCardHandle addedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(addedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(addedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());

        // task with end date and time only
        taskToShow = td.readBook;
        addedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(addedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(addedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());

        // task with start date and time as well as end date and time
        taskToShow = td.visitFriend;
        addedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(addedCard.getStartDate(), taskToShow.getStartDateTime().get().getDateOnly());
        assertEquals(addedCard.getStartTime(), taskToShow.getStartDateTime().get().getTimeOnly());
        assertEquals(addedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(addedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());
    }

    @Test
    public void taskCard_editedTask_success() throws IllegalValueException {
        TestTask taskToShow = td.visitSarah;
        TaskCardHandle originalCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);

        // edit name
        assertEquals(originalCard.getFullName(), taskToShow.getName().fullName);
        taskToShow.setName(new Name("Visit Sarah at Hospital"));
        commandBox.runCommand("edit 2 change name to Visit Sarah at Hospital");
        TaskCardHandle editedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(editedCard.getFullName(), taskToShow.getName().fullName);

        // edit start date and time
        taskToShow.setStartDateTime(new DateTime("30 may 2pm"));
        commandBox.runCommand("edit 2 change sd to 30 may and change st to 2pm");
        editedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(editedCard.getStartDate(), taskToShow.getStartDateTime().get().getDateOnly());
        assertEquals(editedCard.getStartTime(), taskToShow.getStartDateTime().get().getTimeOnly());

        // edit end date and time
        taskToShow.setEndDateTime(new DateTime("30 may 2.30pm"));
        commandBox.runCommand("edit 2 change ed to 30 may and change et to 2.30pm");
        editedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        assertEquals(editedCard.getEndDate(), taskToShow.getEndDateTime().get().getDateOnly());
        assertEquals(editedCard.getEndTime(), taskToShow.getEndDateTime().get().getTimeOnly());
    }

    @Test
    public void taskCard_completedTask_success() {
        commandBox.runCommand("complete 1");
        commandBox.runCommand("list completed");
        TaskCardHandle completedCard = taskListPanel.navigateToTask(td.payDavid.getName().fullName);
        Image expectedImage = new Image(MainApp.class.getResource("/images/tick.png").toExternalForm());
        Image actualImage = completedCard.getSymbol();
        assertEquals(expectedImage.impl_getUrl(), actualImage.impl_getUrl());
    }

    @Test
    public void taskCard_overdueTask_success() throws IllegalValueException {
        commandBox.runCommand("edit 2 change ed to 1 jan 2017");
        TestTask taskToShow = td.visitSarah;
        taskToShow.setEndDateTime(new DateTime("1 jan 2017"));
        TaskCardHandle overdueCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        Image expectedImage = new Image(MainApp.class.getResource("/images/cross.png").toExternalForm());
        Image actualImage = overdueCard.getSymbol();
        assertEquals(expectedImage.impl_getUrl(), actualImage.impl_getUrl());
    }

    @Test
    public void taskCard_uncompletedTask_failure() {
        TestTask taskToShow = td.eggsAndBread;
        TaskCardHandle uncompletedCard = taskListPanel.navigateToTask(taskToShow.getName().fullName);
        uncompletedCard.getSymbol();
        thrown.expect(AssertionError.class);
        fail();
    }

}
