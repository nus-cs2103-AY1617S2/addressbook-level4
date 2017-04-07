package project.taskcrusher.testutil;

import java.util.ArrayList;
import java.util.List;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.UserInbox;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList;

//@author A0127737X
/**
 * Constructs and returns a sample UserInbox instance that can be used for testing.
 * Also provides sample tasks and events that are not added to the UserInbox by default, which can be added later.
 */
public class TypicalTestUserInbox {

    public TestTaskCard assignment, payment, shopping, phoneCall, application;
    public TestTaskCard notAddedBuyTicket, notAddedYetQuiz;
    public TestEventCard guitarLesson, islandTrip, fixRoof;
    public TestEventCard notAddedYetTownFestival, notAddedYetCheckSamples;

    public TypicalTestUserInbox() {
        try {
            //==================== Sample tasks ======================
            assignment = new TaskBuilder().withName("CS2103 assignment")
                    .withDescription(Description.NO_DESCRIPTION).withDeadline("18-6-2020").withPriority("3")
                    .withTags("school").build();
            payment = new TaskBuilder().withName("pay tuition fee").withDescription("Set up bank account")
                    .withDeadline("tomorrow").withPriority("3")
                    .withTags("owesMoney", "school").build();
            shopping = new TaskBuilder().withName("Buy cheesecake").withPriority("1")
                    .withDeadline("tomorrow").withDescription("wall street").build();
            phoneCall = new TaskBuilder().withName("call Mr Beans").withPriority("1")
                    .withDeadline(Deadline.NO_DEADLINE).withDescription("sometime this year").build();
            application = new TaskBuilder().withName("apply to jobs").withPriority("0")
                    .withDeadline("next Monday").withDescription("McDonald's").build();
            notAddedBuyTicket = new TaskBuilder().withName("Buy Ticket for UEFA").withPriority("3")
                    .withDeadline("next Tuesday").withDescription("In Portugal").build();
            notAddedYetQuiz = new TaskBuilder().withName("Post lecture quiz").withPriority("2")
                    .withDeadline("2018 March 3").withDescription("CS2103").build();

            //==================== Sample events =====================
            guitarLesson = new EventBuilder().withName("Guitar lesson").withPriority("0").withDescription("concert")
                    .withLocation("NUS").withTimeslots(constructTimeslotList(new Timeslot("2017-10-12", "2017-10-15")))
                    .build();
            islandTrip = new EventBuilder().withName("Island trip").withPriority("3").withDescription("Mr XYZ")
                    .withTimeslots(constructTimeslotList(new Timeslot("2017-10-12", "2017-10-15")))
                    .withLocation("Carribean").build();
            fixRoof = new EventBuilder().withName("Fix roof").withPriority("2").withDescription("brand audit")
                    .withLocation("home").withTimeslots(constructTimeslotList(new Timeslot("2017-11-19", "2017-11-20")))
                            .build();
            notAddedYetTownFestival = new EventBuilder().withName("Town festival").withPriority("1").withDescription("")
                    .withLocation("Downtown").
                    withTimeslots(constructTimeslotList(new Timeslot("2018-03-20", "2018-03-31"))).build();
            notAddedYetCheckSamples = new EventBuilder().withName("Check samples").withDescription("for lab")
                    .withLocation("work").withPriority("3")
                    .withTimeslots(constructTimeslotList(new Timeslot("2019-11-11 02:00PM", "2019-11-11 05:00PM"),
                                new Timeslot("2019-11-11 06:00PM", "2019-11-11 07:00PM"))).build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }

    public static void loadUserInboxWithSampleTasks(UserInbox inbox) {
        for (TestTaskCard task : new TypicalTestUserInbox().getTypicalTasks()) {
            try {
                inbox.addTask(new Task(task));
            } catch (UniqueTaskList.DuplicateTaskException e) {
                assert false : "not possible";
            }
        }
    }

    public static void loadUserInboxWithSampleEvents(UserInbox inbox) {
        for (TestEventCard event : new TypicalTestUserInbox().getTypicalEvents()) {
            try {
                inbox.addEvent(new Event(event));
            } catch (UniqueEventList.DuplicateEventException e) {
                assert false : "not possible";
            }
        }
    }

    public TestTaskCard[] getTypicalTasks() {
        return new TestTaskCard[]{assignment, payment, shopping, phoneCall, application};
    }

    public TestEventCard[] getTypicalEvents() {
        return new TestEventCard[]{guitarLesson, fixRoof, islandTrip};
    }

    public UserInbox getTypicalUserInbox() {
        UserInbox inbox = new UserInbox();
        loadUserInboxWithSampleTasks(inbox);
        loadUserInboxWithSampleEvents(inbox);

        return inbox;
    }

    private static List<Timeslot> constructTimeslotList(Timeslot... timeslots) {
        List<Timeslot> timeslotList = new ArrayList<>();
        for (Timeslot timeslot: timeslots) {
            timeslotList.add(timeslot);
        }
        return timeslotList;
    }

}
