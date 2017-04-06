package project.taskcrusher.model.util;

import java.util.ArrayList;
import java.util.List;

import project.taskcrusher.commons.exceptions.IllegalValueException;
import project.taskcrusher.model.ReadOnlyUserInbox;
import project.taskcrusher.model.UserInbox;
import project.taskcrusher.model.event.Event;
import project.taskcrusher.model.event.Location;
import project.taskcrusher.model.event.Timeslot;
import project.taskcrusher.model.event.UniqueEventList.DuplicateEventException;
import project.taskcrusher.model.shared.Description;
import project.taskcrusher.model.shared.Name;
import project.taskcrusher.model.shared.Priority;
import project.taskcrusher.model.tag.UniqueTagList;
import project.taskcrusher.model.task.Deadline;
import project.taskcrusher.model.task.Task;
import project.taskcrusher.model.task.UniqueTaskList.DuplicateTaskException;

public class SampleDataUtil {
    public static Task[] getSampleTasks() {
        try {
            return new Task[] {
                new Task(new Name("CS2103 tutorial"), new Deadline("2017-11-11"), new Priority("3"),
                        new Description("presentation"), new UniqueTagList("school")),
                new Task(new Name("CS2106 assignment"), new Deadline("2018-03-20"), new Priority("2"),
                        new Description("submit assignment"), new UniqueTagList("school")),

                new Task(new Name("security assignment"), new Deadline("2017-12-11"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("Marketing project"), new Deadline("2017-11-19"), new Priority("3"),
                        new Description("brand audit"), new UniqueTagList("school")),
                new Task(new Name("Meeting"), new Deadline("2019-11-11"), new Priority("2"),
                        new Description("nuclear deal"), new UniqueTagList("office")),
                new Task(new Name("Invite for dinner"), new Deadline("2017-10-12"), new Priority("1"),
                        new Description("Mr XYZ"), new UniqueTagList("Home")),
                new Task(new Name("Auditing"), new Deadline("2017-11-13"), new Priority("2"),
                        new Description("Accounts"), new UniqueTagList("office")),
                new Task(new Name("Tennis match"), new Deadline("2017-09-11"), new Priority("1"),
                        new Description("With John"), new UniqueTagList("Home")),
                new Task(new Name("Movie"), new Deadline("2017-11-09"), new Priority("1"),
                        new Description("iron man"), new UniqueTagList("home")),
                new Task(new Name("Buy a phone"), new Deadline("2017-12-11"), new Priority("1"),
                        new Description("gift for friend"), new UniqueTagList("home")),
                new Task(new Name("Cricket match"), new Deadline("2017-11-13"), new Priority("1"),
                        new Description("india wc final"), new UniqueTagList("home")),
                new Task(new Name("maths project"), new Deadline("2017-04-11"), new Priority("1"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("Shopping"), new Deadline("2017-11-13"), new Priority("1"),
                        new Description("ikea"), new UniqueTagList("home")),
                new Task(new Name("Repair watch"), new Deadline("2017-08-17"), new Priority("3"),
                        new Description("swatch"), new UniqueTagList("home")),
                new Task(new Name("Physics hw"), new Deadline("2017-11-14"), new Priority("3"),
                        new Description("Kinematics"), new UniqueTagList("school")),
                new Task(new Name("Chemistry lab"), new Deadline("2017-12-16"), new Priority("3"),
                        new Description("salt analysis"), new UniqueTagList("school")),
                new Task(new Name("Club meeting"), new Deadline("2017-11-13"), new Priority("3"),
                        new Description("organising committee"), new UniqueTagList("school")),
                new Task(new Name("Pick a friend"), new Deadline("2017-07-15"), new Priority("1"),
                        new Description("Mr Smith"), new UniqueTagList("home")),
                new Task(new Name("Physics lab"), new Deadline("2017-06-11"), new Priority("3"),
                        new Description("Sonometer"), new UniqueTagList("school")),
                new Task(new Name("Book tickets"), new Deadline("2017-11-12"), new Priority("3"),
                        new Description("delhisingapore"), new UniqueTagList("home")),
                new Task(new Name("Fix a meeting"), new Deadline("2017-11-19"), new Priority("3"),
                        new Description("russian invasion"), new UniqueTagList("office")),
                new Task(new Name("Computer lab"), new Deadline("2017-08-11"), new Priority("3"),
                        new Description("C OOP"), new UniqueTagList("school")),
                new Task(new Name("C assignment"), new Deadline("2017-05-11"), new Priority("1"),
                        new Description("Directory with double pointers"), new UniqueTagList("school")),
                new Task(new Name("Dinner"), new Deadline("2019-07-11"), new Priority("1"),
                        new Description("Hilton hotel"), new UniqueTagList("home")),
                new Task(new Name("Marriage invite"), new Deadline("2017-09-11"), new Priority("3"),
                        new Description("Church"), new UniqueTagList("home")),
                new Task(new Name("Lunch"), new Deadline("2017-09-10"), new Priority("3"), new Description("MCD"),
                        new UniqueTagList("school")),
                new Task(new Name("Assignment"), new Deadline("2017-11-09"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("comp 2011"), new Deadline("2017-11-01"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("paper checking"), new Deadline("2017-10-09"), new Priority("3"),
                        new Description("comp2711"), new UniqueTagList("school")),
                new Task(new Name("trip "), new Deadline("2017-08-09"), new Priority("2"), new Description("India"),
                        new UniqueTagList("home")),
                new Task(new Name("Get a gun"), new Deadline("2017-11-08"), new Priority("3"),
                        new Description("shoot all enemies"), new UniqueTagList("school")),
                new Task(new Name("comp3711 assignment"), new Deadline("2017-10-07"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("comp2012 assignment"), new Deadline("2017-09-10"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("OP assignment"), new Deadline("2017-07-08"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("cs3111"), new Deadline("2017-10-07"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")),
                new Task(new Name("cs2001"), new Deadline("2017-11-12"), new Priority("3"),
                        new Description("submit assignment"), new UniqueTagList("school")), };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static Event[] getSampleEvents() {
        try {
            return new Event[] {
                new Event(new Name("Guitar lesson"),
                        constructTimeslotList(new Timeslot("2017-11-11", "2017-11-12")), new Priority("1"),
                        new Location("NUS"), new Description("presentation"), new UniqueTagList("school")),
                new Event(new Name("Town festival"),
                        constructTimeslotList(new Timeslot("2018-03-20", "2018-03-31")), new Location("Iloilo"),
                        new Description("submit assignment"), new UniqueTagList("school")),

                new Event(new Name("Party planning"),
                        constructTimeslotList(new Timeslot("2017-12-11 03:00PM", "2017-12-11 05:00PM")),
                        new Priority("2"), new Location("Secret base"), new Description("submit assignment"),
                        new UniqueTagList("school")),
                new Event(new Name("Fix roof"), constructTimeslotList(new Timeslot("2017-11-19", "2017-11-20")),
                        new Location("home"), new Description("brand audit"), new UniqueTagList("school")),
                new Event(new Name("Check samples"),
                        constructTimeslotListTentative(new Timeslot("2019-11-11 02:00PM", "2019-11-11 05:00PM"),
                                new Timeslot("2019-11-11 06:00PM", "2019-11-11 07:00PM")),
                        new Location("Dexters laboratory"), new Description("nuclear deal"),
                        new UniqueTagList("office")),
                new Event(new Name("Island trip"), constructTimeslotList(new Timeslot("2017-10-12", "2017-10-15")),
                        new Priority("3"), new Location("Middle of nowhere"), new Description("Mr XYZ"),
                        new UniqueTagList("Home")) };
        } catch (IllegalValueException e) {
            throw new AssertionError("sample data cannot be invalid", e);
        }
    }

    public static ReadOnlyUserInbox getSampleUserInbox() {
        try {
            UserInbox sampleuserInbox = new UserInbox();
            for (Task sampleTask : getSampleTasks()) {
                sampleuserInbox.addTask(sampleTask);
            }
            for (Event sampleEvent : getSampleEvents()) {
                sampleuserInbox.addEvent(sampleEvent);
            }

            return sampleuserInbox;
        } catch (DuplicateTaskException e) {
            throw new AssertionError("sample data cannot contain duplicate tasks", e);
        } catch (DuplicateEventException e2) {
            throw new AssertionError("sample data cannot contain duplicate events", e2);
        }
    }

    public static List<Timeslot> constructTimeslotList(Timeslot t) {
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(t);
        return timeslots;
    }

    public static List<Timeslot> constructTimeslotListTentative(Timeslot t, Timeslot t2) {
        List<Timeslot> timeslots = new ArrayList<>();
        timeslots.add(t);
        timeslots.add(t2);
        return timeslots;
    }
}
