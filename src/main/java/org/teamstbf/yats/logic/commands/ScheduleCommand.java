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

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule an task or event to the task manager. "
            + "Parameters: task name l/location s/START TIME  e/END TIME  d/ description [t/TAG]...\n"
            + "Example: " + COMMAND_WORD
            + " meeting with boss l/work p/daily s/7:00pm,18/03/2017  e/9:00pm,18/03/2017  "
            + "d/get scolded for being lazy t/kthxbye";

    public static final String MESSAGE_SUCCESS = "New event schedule: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task manager";

    private final Event toSchedule;

    /**
     * Creates an Schedule using raw values.
     * @param string2
     * @param string
     *
     * @throws IllegalValueException if any of the raw values are invalid
     */
    public ScheduleCommand(String name, String location, String period, String startTime,
            String endTime, String deadline, String description, Set<String> tags)
            throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(new Tag(tagName));
        }
        this.toSchedule = new Event(
                new Title(name),
                new Location(location),
                new Schedule(startTime),
                new Schedule(endTime),
                new Schedule(deadline),
                new Description(description),
                new UniqueTagList(tagSet),
                new IsDone()
        );
    }

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
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        List<ReadOnlyEvent> taskLists = (List<ReadOnlyEvent>) model.getFilteredTaskList();
        List<ReadOnlyEvent> filterTaskLists = new ArrayList<ReadOnlyEvent>();
        for (ReadOnlyEvent event : taskLists) {
            if (event.hasStartEndTime()) {
                filterTaskLists.add(event);
            }
        } 
        Collections.sort(filterTaskLists, new ReadOnlyEventComparatorByStartDate());
        long max = filterTaskLists.get(1).getEndTime().getDate().getTime();
        long curr = -1 ;
        long start = -1 ;
        long end = -1 ;
        long timeneeded = 7200000L;
        for (ReadOnlyEvent event : filterTaskLists) {
            System.out.println("start is" + event.getStartTime().getDate().toString());
            System.out.println("end is " + event.getEndTime().getDate().toString());
            curr = event.getStartTime().getDate().getTime();
            if (curr > max) {
                if ((curr - max) > timeneeded){
                    start = max ;
                    end = max + timeneeded;
                }
            }
            max = Math.max(curr, event.getEndTime().getDate().getTime());
        } 
        System.out.println("Chosen time is");
        System.out.println(new Date(start).toString());
        System.out.println(new Date(end).toString());
        try {
            model.addEvent(toSchedule);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
        } catch (UniqueEventList.DuplicateEventException e) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }
    }
}

