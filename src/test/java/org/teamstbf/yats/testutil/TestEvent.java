package org.teamstbf.yats.testutil;

import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Recurrence;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.UniqueTagList;

/**
 * A mutable person object. For testing only.
 */
public class TestEvent implements ReadOnlyEvent {

    private Title name;
    private Schedule startTime;
    private Schedule endTime;
    private Schedule deadline;
    private Description description;
    private IsDone isDone;
    private Location location;
    private UniqueTagList tags;
    private boolean isRecurring;
    private Recurrence recurrence;
    private String hours;
    private String minutes;

    public TestEvent() {
        tags = new UniqueTagList();
    }

    /**
     * Creates a copy of {@code personToCopy}.
     */
    public TestEvent(TestEvent eventToCopy) {
        this.name = eventToCopy.getTitle();
        this.location = eventToCopy.getLocation();
        this.startTime = eventToCopy.getStartTime();
        this.endTime = eventToCopy.getEndTime();
        this.deadline = eventToCopy.getEndTime();
        this.description = eventToCopy.getDescription();
        this.tags = eventToCopy.getTags();
        this.isDone = eventToCopy.getIsDone();
    }

    public String getAddCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("add " + this.getTitle().fullName + " ");
        sb.append("-s " + this.getStartTime().toString() + " ");
        sb.append("-e " + this.getEndTime().toString() + " ");
        sb.append("-by " + this.getDeadline().toString() + "");
        sb.append("-l " + this.getLocation().value + " ");
        sb.append("-d " + this.getDescription().value + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("-t " + s.tagName + " "));
        return sb.toString();
    }

    public String getScheduleCommand() {
        StringBuilder sb = new StringBuilder();
        sb.append("schedule " + this.getTitle().fullName + " ");
        sb.append("-l " + this.getLocation().value + " ");
        sb.append("-d " + this.getDescription().value + " ");
        sb.append("-h " + this.getHours() + " ");
        sb.append("-m " + this.getMinutes() + " ");
        this.getTags().asObservableList().stream().forEach(s -> sb.append("-t " + s.tagName + " "));
        return sb.toString();
    }

    private String getMinutes() {
        return this.hours;
    }

    private String getHours() {
        return this.minutes;
    }

    @Override
    public Description getDescription() {
        return description;
    }

    @Override
    public Schedule getEndTime() {
        return endTime;
    }

    @Override
    public Location getLocation() {
        return location;
    }

    @Override
    public Schedule getStartTime() {
        return startTime;
    }

    @Override
    public UniqueTagList getTags() {
        return tags;
    }

    @Override
    public Title getTitle() {
        return name;
    }

    public void setDescription(Description description) {
        this.description = description;
    }

    public void setEndTime(Schedule schedule) {
        this.endTime = schedule;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setStartTime(Schedule schedule) {
        this.startTime = schedule;
    }

    public void setTags(UniqueTagList tags) {
        this.tags = tags;
    }

    public void setTitle(Title name) {
        this.name = name;
    }

    public void setIsDone(IsDone isDone) {
        this.isDone = isDone;
    }

    @Override
    public String toString() {
        return getAsText();
    }

    @Override
    public IsDone getIsDone() {
        return isDone;
    }

    @Override
    public void markDone() {
        isDone.markDone();
    }

    @Override
    public Schedule getDeadline() {
        return deadline;
    }

    @Override
    public boolean hasDeadline() {
        if (this.deadline.toString().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasStartAndEndTime() {
        if (this.startTime.toString().equals("") || this.endTime.toString().equals("")) {
            return false;
        }
        return true;
    }

    @Override
    public boolean hasStartOrEndTime() {
        if (!this.startTime.toString().equals("") || !this.endTime.toString().equals("")) {
            return true;
        }
        return false;
    }

    public void setDeadline(Schedule schedule) {
        this.deadline = schedule;
    }

    public void setRecurrence(Recurrence recur) {
        this.recurrence = recur;
    }

    @Override
    public Recurrence getRecurrence() {
        return this.recurrence;
    }

    @Override
    public boolean isRecurring() {
        return this.isRecurring;
    }

    public void setHours(String myHours) {
        this.hours = myHours;
    }

    public void setMinutes(String myMinutes) {
        this.minutes = myMinutes;
    }
}
