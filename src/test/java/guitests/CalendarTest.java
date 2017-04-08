package guitests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.testfx.api.FxToolkit;

import jfxtras.internal.scene.control.skin.agenda.AgendaWeekSkin;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import seedu.whatsleft.testutil.TestEvent;
import seedu.whatsleft.testutil.TestTask;
import seedu.whatsleft.testutil.TestUtil;

//@@author A0124377A

/**
 * Use cases need to be covered:
 *  1. Response to Refresh commmand
 *  2. Response to Next command
 *  3. Feedback to select command
 */
public class CalendarTest extends WhatsLeftGuiTest {

    public String refreshCommand = "refresh";
    public String nextCommand = "next 2";
    public static final int TESTWEEKSAHEAD = 2;

    @Test
    public void initWeekView() throws Exception {
        Agenda agenda = calendar.getAgenda();
        assertCalendarViewMatch(agenda, new AgendaWeekSkin(new Agenda()));
    }

    @Test
    public void refreshCommandTest() {
        TestEvent[] currentEventList = te.getTypicalEvents();
        TestEvent eventToAdd = te.workshop;
        currentEventList = TestUtil.addEventsToList(currentEventList, eventToAdd);
        commandBox.runCommand(eventToAdd.getAddCommand());
        commandBox.runCommand(refreshCommand);
        assertTrue(assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime()
                , LocalDateTime.now()));
        assertFalse(assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime()
                , LocalDateTime.of(eventToAdd.getStartDate().getValue(), eventToAdd.getStartTime().getValue())));
    }

    @Test
    public void nextCommandTest() {
        commandBox.runCommand(nextCommand);
        assertFalse(assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime()
                , LocalDateTime.now()));
        assertTrue(assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime()
                , LocalDateTime.now().plusWeeks(2)));
    }

    @Test
    public void nextAndRefreshDisplayedDateTimeShouldShowCorrectTime() throws Exception {
        LocalDateTime time1 = LocalDateTime.now();
        LocalDateTime time2 = LocalDateTime.now().plusWeeks(TESTWEEKSAHEAD);
        Agenda target1 = calendar.getAgendaOfDateTime(time1);
        Agenda target2 = calendar.getAgendaOfDateTime(time2);

        //restore to main app
        FxToolkit.setupApplication(testApp.getClass(), getDataFileLocation());

        assertTrue(calendarDisplayedDateTimeMatch("refresh", target1));
        assertTrue(calendarDisplayedDateTimeMatch("next 2", target2));

        assertFalse(calendarDisplayedDateTimeMatch("refresh", target2));
        assertFalse(calendarDisplayedDateTimeMatch("next 2", target1));
    }

    @Test
    public void selectActivityTest() {

        //select a event
        commandBox.runCommand("select ev 1");
        assertCalendarSelectedCorrectEvent(te.presentation);

        //select a task
        commandBox.runCommand("select ts 1");
        assertCalendarSelectedCorrectTask(tt.printing);

    }

    /****************************** Helper Methods ***************************/

    private boolean calendarDisplayedDateTimeMatch(String command, Agenda expectedAgenda) {
        commandBox.runCommand(command);

        return assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime(),
                expectedAgenda.getDisplayedLocalDateTime());
    }

    /**
     * Compare two LocalDateTime with 2 minutes allowed for test buffer.
     * @param testTime
     * @param expectedTime
     * @return
     */
    private boolean assertCalendarDisplayedDateTimeMatch(LocalDateTime testTime, LocalDateTime expectedTime) {
        return testTime.isAfter(expectedTime.minusMinutes(1)) && testTime.isBefore(expectedTime.plusMinutes(1));
    }


    private boolean assertCalendarViewMatch(Agenda agenda, AgendaWeekSkin skin) {
        return (agenda.getSkin().getClass().getName().equals(skin.getClass().getName()));
    }

    private void assertCalendarSelectedCorrectEvent(TestEvent event) {
        assertTrue(calendarHighlightedEvent(calendar.getAgenda().selectedAppointments().get(0), event));
        assertTrue(assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime(),
                LocalDateTime.of(event.getStartDate().getValue(), event.getStartTime().getValue())));
    }

    private void assertCalendarSelectedCorrectTask(TestTask task) {
        assertTrue(calendarHighlightedEvent(calendar.getAgenda().selectedAppointments().get(0), task));
        assertTrue(assertCalendarDisplayedDateTimeMatch(calendar.getAgenda().getDisplayedLocalDateTime(),
                LocalDateTime.of(task.getByDate().getValue(), task.getByTime().getValue())));
    }

    private boolean calendarHighlightedEvent(Appointment appointment, TestEvent event) {
        return calendar.isSameEvent(appointment, event);
    }

    private boolean calendarHighlightedEvent(Appointment appointment, TestTask task) {
        return calendar.isSameTask(appointment, task);
    }

}
