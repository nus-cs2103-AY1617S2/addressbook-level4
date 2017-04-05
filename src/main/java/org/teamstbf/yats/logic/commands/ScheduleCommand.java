package org.teamstbf.yats.logic.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.ReadOnlyEventComparatorByStartDate;
import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.IsDone;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

import javafx.collections.ObservableList;

/**
 * Adds a task to the TaskManager.
 */
public class ScheduleCommand extends Command {

    private static final int INITIAL_START_VALUE = -1;

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule an task or event to the task manager. "
            + "Parameters: task name l/location s/START TIME  e/END TIME  d/ description [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " meeting with boss l/work p/daily s/7:00pm,18/03/2017  e/9:00pm,18/03/2017  "
            + "d/get scolded for being lazy t/kthxbye";
    
    public static final String MESSAGE_SUCCESS = "New event scheduled: %1$s";
    public static final String MESSAGE_SUCCESS_WITH_WARNING = "Warning! Event lasts more than 50 years, please check if valid";
    public static final String MESSAGE_HOURS_INVALID = "The format of hours is invalid - must be a valid long";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task manager";
    public static final String MESSAGE_TIME_TOO_LONG = "Hours should not be negative";    
    private static final long MAXIMUM_EVENT_LENGTH = 1577846300000L;
    private static final long MINIMUM_EVENT_LENGTH = 0L;
    private final Event toSchedule;
    private final String hours;


    /**
     * Creates an addCommand using a map of parameters
     * @param addParam
     * @throws IllegalValueException if any of the parameters are invalid
     */
    public ScheduleCommand(HashMap<String, Object> parameters) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : (Set<String>) parameters.get("tag")) {
            tagSet.add(new Tag(tagName));
        }
        this.toSchedule = new Event(parameters, new UniqueTagList(tagSet));
        this.hours = parameters.get("hours").toString();
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            long checkedHours = (long)(Double.parseDouble(this.hours) * 3600000);
            System.out.println(checkedHours);
            if (checkedHours < MINIMUM_EVENT_LENGTH) {
                throw new IllegalArgumentException();
            }
            List<ReadOnlyEvent> filteredTaskLists = filterOnlyEventsWithStartEndTime(); 
            Collections.sort(filteredTaskLists, new ReadOnlyEventComparatorByStartDate());
            setStartEndIntervalsForNewTask(checkedHours, filteredTaskLists);
            model.addEvent(toSchedule);
            if (checkedHours < MAXIMUM_EVENT_LENGTH){
                return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
            } else {
                return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_WARNING, toSchedule));
            }
        } catch (NumberFormatException e){
            throw new CommandException(MESSAGE_HOURS_INVALID);
        } catch (UniqueEventList.DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_TIME_TOO_LONG);
        } 
    }

    private void setStartEndIntervalsForNewTask(long checkedHours, List<ReadOnlyEvent> filteredTaskLists) {
        long start = getStartInterval(checkedHours, filteredTaskLists);
        long end = getEndInterval(checkedHours, start);
        Schedule startTime = new Schedule(new Date(start));
        Schedule endTime = new Schedule(new Date(end));
        this.toSchedule.setStartTime(startTime);
        this.toSchedule.setEndTime(endTime);
    }

    private long getEndInterval(long checkedHours, long start) {
        long end = start + checkedHours;
        return end;
    }

    private long getStartInterval(long checkedHours, List<ReadOnlyEvent> filteredTaskLists) {
        long max = new Date().getTime();
        long curr;
        long start = INITIAL_START_VALUE ;
        for (ReadOnlyEvent event : filteredTaskLists) {
            curr = event.getStartTime().getDate().getTime();
            if (curr > max) {
                if ((curr - max) >= checkedHours){
                    start = max ;
                    break;
                }
            }
            max = Math.max(max, event.getEndTime().getDate().getTime());
        } 
        if (start == INITIAL_START_VALUE) {
            start = max ;
        }
        return start;
    }

    private List<ReadOnlyEvent> filterOnlyEventsWithStartEndTime() {
        List<ReadOnlyEvent> taskLists = (List<ReadOnlyEvent>) model.getFilteredTaskList();
        List<ReadOnlyEvent> filterTaskLists = new ArrayList<ReadOnlyEvent>();
        for (ReadOnlyEvent event : taskLists) {
            if (event.hasStartEndTime()) {
                filterTaskLists.add(event);
            }
        }
        return filterTaskLists;
    }
}

