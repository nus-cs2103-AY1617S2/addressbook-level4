package org.teamstbf.yats.logic.commands;

import static org.teamstbf.yats.logic.parser.ScheduleCommandParser.PARAMETER_HOURS;
import static org.teamstbf.yats.logic.parser.ScheduleCommandParser.PARAMETER_MINUTES;
import static org.teamstbf.yats.logic.parser.ScheduleCommandParser.PARAMETER_TAG;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.teamstbf.yats.commons.core.EventsCenter;
import org.teamstbf.yats.commons.events.ui.JumpToListRequestEvent;
import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.ReadOnlyEventComparatorByStartTime;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

//@@author A0102778B

/**
 * Adds a task to the TaskManager.
 */
public class ScheduleCommand extends Command {

    private static final String EMPTY_HOUR_MINUTE_VALUE = "0";

    private static final String DEFAULT_HOUR_VALUE = "1";

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule an task or event to the task manager. "
            + "Parameters: task name -l LOCATION -d DESCRIPTION -T TAG -T TAG -h HOURS -m MINUTES \n" + "Example: "
            + COMMAND_WORD + " have scheduled phone call [-l meeting room -d clients -T work -T skype] -h 2 -m 30  ";

    public static final String MESSAGE_SUCCESS = "New event scheduled: %1$s";
    public static final String MESSAGE_HOURS_INVALID = "The format of hours is invalid - must be a valid long";
    public static final String MESSAGE_TIME_TOO_LONG = "Schedule can only take a timing of at most 10 hours, and it should not be negative - use add for long events";
    private static final long MAXIMUM_EVENT_LENGTH = 36000000L;
    private static final long MINIMUM_EVENT_LENGTH = 0L;
    private static final int INITIALISE_AS_NEXT_DAY = 1;
    private static final int INITIALISE_AS_ZERO = 0;
    private static final long CONSTANT_FOR_UPPERBOUND_CASE = 122400000L;
    private static final long CONSTANT_FOR_LOWER_BOUND_CASE = 36000000L;
    private static final int MINUTES_PER_HOUR = 60;
    private static final int INITIAL_POSITION_OF_SCHEDULED_OBJECT_IN_LIST = 0;
    private static final int SCHEDULE_ENDING_HOUR = 18;
    private static final int SCHEDULE_STARTING_HOUR = 8;
    private static final int INDEX_OF_POSITION_LONG = 1;
    private static final int INDEX_OF_START_LONG = 0;
    private static final long MILLISECONDS_PER_MINUTE = 60000L;
    private static final long MILLISECONDS_PER_HOUR = 3600000L;
    private static final long LAST_TIME_POSSIBLE = 1577846300000L;
    private static final int INITIAL_START_VALUE = -1;
    private static final int INVALID_VALUE = -1;

    private final Event toSchedule;
    private String hours;
    private String minutes;

    /**
     * Creates an scheduleCommand using a map of parameters. This constructor
     * also initialises the hours and minutes to be used for scheduling (the
     * time required).
     *
     * @param scheduleParam
     * @throws IllegalValueException
     *             if any of the parameters are invalid
     */
    public ScheduleCommand(HashMap<String, Object> parameters) throws IllegalValueException {
        final Set<Tag> tagSet = setUniqueTagList(parameters);
        this.toSchedule = new Event(parameters, new UniqueTagList(tagSet));
        setHoursAndMinutesForScheduling(parameters);
    }

    /*
     * Method to set the unique tag list for the event to be scheduled
     */
    private Set<Tag> setUniqueTagList(HashMap<String, Object> parameters) throws IllegalValueException {
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : (Set<String>) parameters.get(PARAMETER_TAG)) {
            tagSet.add(new Tag(tagName));
        }
        return tagSet;
    }

    /*
     * Method to Set the Parameters for Scheduling - if no timing is given,
     * default schedule is 1 hour
     */
    private void setHoursAndMinutesForScheduling(HashMap<String, Object> parameters) {
        try {
            this.hours = parameters.get(PARAMETER_HOURS).toString();
        } catch (NullPointerException e) {
            this.hours = EMPTY_HOUR_MINUTE_VALUE;
        }
        try {
            this.minutes = parameters.get(PARAMETER_MINUTES).toString();
        } catch (NullPointerException e) {
            this.minutes = EMPTY_HOUR_MINUTE_VALUE;
        }
        if (this.hours.equals(EMPTY_HOUR_MINUTE_VALUE) && this.minutes.equals(EMPTY_HOUR_MINUTE_VALUE)) {
            this.hours = DEFAULT_HOUR_VALUE;
        }
    }

    @Override
    public CommandResult execute() throws CommandException {
        assert model != null;
        try {
            executeSchedulingMethod();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
        } catch (NumberFormatException e) {
            throw new CommandException(MESSAGE_HOURS_INVALID);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_TIME_TOO_LONG);
        }
    }

    /*
     * This is the main method that controls the scheduling input and output.
     * First it saves a copy of the current task manager for the undo function .
     * Then it computes the amount of time needed for scheduling then it calls
     * the method to set the schedule timings for the toSchedule event. Finally
     * it adds this new event to the model, updates the list and moves the GUI
     * to the location of the newly added caller.
     */
    private void executeSchedulingMethod() {
        model.saveImageOfCurrentTaskManager();
        long checkedHours = getTotalScheduleTime();
        int count = setTimingsToSchedule(checkedHours, toSchedule);
        model.addEvent(toSchedule);
        model.updateFilteredListToShowSortedStart();
        EventsCenter.getInstance().post(new JumpToListRequestEvent(count));
    }

    /*
     * This method generates a list of filtered events with start and end times.
     * It then sorts this list by Start Time Finally, it applies the searching
     * algorithm on this sorted list to set the start and end intervals for the
     * new task. It returns the position that the new event should be slotted
     * into in the list.
     */

    private int setTimingsToSchedule(long checkedHours, Event eventToSchedule) {
        List<ReadOnlyEvent> filteredTaskLists = filterOnlyEventsWithStartEndTime();
        Collections.sort(filteredTaskLists, new ReadOnlyEventComparatorByStartTime());
        int count = setStartEndIntervalsForNewTask(checkedHours, filteredTaskLists, eventToSchedule);
        return count;
    }

    /*
     * This method calculates the time needed for the scheduled task based on
     * the number of hours and minutes provided by the user. It returns the
     * total time in milliseconds.
     */
    private long getTotalScheduleTime() {
        long checkedHours = (long) (Double.parseDouble(this.hours) * MILLISECONDS_PER_HOUR);
        checkedHours = checkedHours + (long) (Double.parseDouble(this.minutes) * MILLISECONDS_PER_MINUTE);
        if (checkedHours < MINIMUM_EVENT_LENGTH || checkedHours > MAXIMUM_EVENT_LENGTH) {
            throw new IllegalArgumentException();
        }
        return checkedHours;
    }

    /*
     * This method gets the start and end intervals for the event, constructs
     * new schedule objects, and subsequently modifies the event with the
     * appropriate start and end time. It also returns the position that the new
     * event should be slotted into in the list.
     */
    private int setStartEndIntervalsForNewTask(long checkedHours, List<ReadOnlyEvent> filteredTaskLists,
            Event eventToSchedule) throws IllegalArgumentException {
        ArrayList<Long> myList = getStartInterval(checkedHours, filteredTaskLists);
        long end = getEndInterval(checkedHours, myList.get(INDEX_OF_START_LONG));
        Schedule startTime = new Schedule(new Date(myList.get(INDEX_OF_START_LONG)));
        Schedule endTime = new Schedule(new Date(end));
        eventToSchedule.setStartTime(startTime);
        eventToSchedule.setEndTime(endTime);
        return myList.get(INDEX_OF_POSITION_LONG).intValue();
    }

    /*
     * This method applies a searching algorithm to find the first suitable
     * start timing. This start timing has the following constraints - i) it
     * must not overlap with any other event in the list ii) it must be between
     * 8am and 6pm iii) must not be on a saturday or sunday The algorithm first
     * searches for open intervals in the list of events, then attempts to check
     * if the open interval is appropriate for scheduling the event. To find
     * open intervals , the list is first sorted by start times (this was done
     * in a previous method). Subsequently, we iterate through this sorted list,
     * keeping the max of the end time saved in a variable. If current maximum
     * end time is smaller than the start time of the next variable, it is then
     * provable that this is an open interval. This is because there is no event
     * with an end time after this interval (since we took the max of all
     * preceding end times), and no event with a start time before this interval
     * (since the array is sorted by start time). Thus, this must be an open
     * interval which we can then check whether it is appropriately sized and
     * the correct day for scheduling. In this implementation the step curr >
     * max is not included, as the more stringent check (curr - max) >=
     * checkedHours Since max and checked hours must be positive values, this is
     * an implicit check that the start time is greater than the maximum end
     * time.
     */
    private ArrayList<Long> getStartInterval(long checkedHours, List<ReadOnlyEvent> filteredTaskLists)

            throws IllegalArgumentException {
        long start = INITIAL_START_VALUE;
        int position = INITIAL_POSITION_OF_SCHEDULED_OBJECT_IN_LIST;
        long curr;
        long getStartTime;
        long max = new Date().getTime();
        for (ReadOnlyEvent event : filteredTaskLists) {
            curr = event.getStartTime().getDate().getTime();
            if ((curr - max) >= checkedHours) {
                getStartTime = findStartTimeFromTwoBoundaries(max, checkedHours, curr,
                        (SCHEDULE_STARTING_HOUR * MINUTES_PER_HOUR), (SCHEDULE_ENDING_HOUR * MINUTES_PER_HOUR));
                if (getStartTime != INITIAL_START_VALUE && isCorrectDay(getStartTime)) {
                    start = getStartTime;
                    break;
                }
            }
            max = Math.max(max, event.getEndTime().getDate().getTime());
            position++;
        }
        if (start == INITIAL_START_VALUE) {
            start = findFirstStartTime(max, checkedHours, LAST_TIME_POSSIBLE,
                    (SCHEDULE_STARTING_HOUR * MINUTES_PER_HOUR), (SCHEDULE_ENDING_HOUR * MINUTES_PER_HOUR));
        }
        ArrayList<Long> startAndPositionLongs = generateReturnLong(start, position);
        return startAndPositionLongs;
    }

    /*
     * Once a valid start time has been found, the start time checks guarantee
     * that a slot exists for this schedule, and so the end time can just be
     * derived from the start time and number of scheduled hours.
     */
    private long getEndInterval(long checkedHours, long start) {
        long end = start + checkedHours;
        return end;
    }

    /*
     * Wrapper method to generate an Arraylist of longs to return
     */
    private ArrayList<Long> generateReturnLong(long start, int position) {
        ArrayList<Long> myList = new ArrayList<Long>();
        myList.add(start);
        myList.add((long) position);
        return myList;
    }

    /*
     * Method to check if the scheduled day is a Saturday or a Sunday
     */
    private boolean isCorrectDay(long getStartTime) {
        Calendar checkDay = Calendar.getInstance();
        checkDay.setTimeInMillis(getStartTime);
        if (checkDay.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || checkDay.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Method to check if there is a valid start time between two boundaries.
     * This problem is complex only valid timings between 8am and 6pm are taken,
     * and the boundaries can span across multiple days. To solve this problem,
     * we first break down the possible times of boundaries into 3 cases, i)
     * Case 1 - Less than 10 Hours , ii) Case 2 - More than 34 Hours , iii) Case
     * 3 - In between 10 Hours and 34 Hours. In case i), because of the time
     * period from 6pm to 8am is 10 Hours, any timing less than 10 hours must
     * only have one valid day for scheduling. The theoretical upper bound is 14
     * hours, but in this case we do not what to deal with wrapped timings. and
     * thus we pick a boundary of 10 hours. Thus, we can check just that day
     * alone to see if there is a valid time slot there. Case ii - 34 hours)
     * Next, if the timing is more than 34 hours, there must be a valid 10 hour
     * block where no event exists. This is because 34 hours encompasses at
     * least one complete time block, and because we know this is an open
     * interval, then by pigeonhole principle there must be an empty 10 hour
     * block in a 34 hour range. Finally, the most complex case is between 10
     * Hours and 34 Hours. Here, we must first check if there are one or two
     * valid days to analyse. Then, we find the appropriate boundaries for one
     * or two days, and check if either of these are valid times for scheduling.
     */
    private long findStartTimeFromTwoBoundaries(long start, long checkedHours, long end, int startBound, int endBound) {
        int hoursMin = (int) (checkedHours / MILLISECONDS_PER_MINUTE);
        int startTime = convertToHoursMinutes(start);
        int endTime = convertToHoursMinutes(end);
        Calendar dayOne = Calendar.getInstance();
        dayOne.setTimeInMillis(start);
        return checkThreeCasesForBoundaries(start, end, startBound, endBound, hoursMin, startTime, endTime, dayOne);
    }

    /* Wrapper Method to do if else check for three cases */
    private long checkThreeCasesForBoundaries(long startDateMils, long endDateMils, int startBound, int endBound,
            int hoursMin, int startTime, int endTime, Calendar timeToCheck) {
        long returnMils = INITIAL_START_VALUE;
        if (endDateMils - startDateMils <= CONSTANT_FOR_LOWER_BOUND_CASE) {
            returnMils = checkLessThan10HoursCase(startBound, endBound, hoursMin, startTime, endTime, timeToCheck,
                    returnMils);
        } else if (endDateMils - startDateMils >= CONSTANT_FOR_UPPERBOUND_CASE) {
            returnMils = checkMoreThan34HoursCase(startBound, endBound, hoursMin, startTime, timeToCheck);
        } else {
            returnMils = checkBetween10Hours34HoursCase(startDateMils, endDateMils, startBound, endBound, hoursMin,
                    startTime, endTime, timeToCheck, returnMils);
        }
        return returnMils;
    }

    /*
     * Method to do check for valid schedule slot in less than 10 hours case.
     * First we must check if the end boundary has wrapped over to another
     * timing. Then depending on whether it was wrapped or not, we can check the
     * appropriate boundary conditions by getting the appropriate end time.
     */
    private long checkLessThan10HoursCase(int startBound, int endBound, int hoursMin, int startTime, int endTime,
            Calendar timeToCheck, long returnMils) {
        startTime = Math.max(startTime, startBound);
        endTime = getAppropriateEndTime(endBound, startTime, endTime);
        System.out.println(startTime + "||" + endTime);
        if ((endTime - startTime) >= hoursMin) {
            returnMils = scheduleEventAtEndTime(timeToCheck, startTime);
        }
        return returnMils;
    }

    /*
     * Method to do check for valid schedule slot in more than 34 hours case.
     * First we must check if there is a appropriate boundary on the first day
     * (there must exist 2 days in a 34 hour block. Then if we can't find an
     * appropriate time slot, there must be a time slot open on the second day
     * (guaranteed by pigeonhole principle).
     */
    private long checkMoreThan34HoursCase(int startBound, int endBound, int hoursMin, int startTime,
            Calendar timeToCheck) {
        long returnMils;
        startTime = Math.max(startTime, startBound);
        if ((endBound - startTime) >= hoursMin) {
            returnMils = scheduleEventAtEndTime(timeToCheck, startTime);
        } else {
            returnMils = scheduleEventOnNextDay(timeToCheck);
        }
        return returnMils;
    }

    /*
     * Method to do check for valid schedule slot in the 10 hours to 34 hours
     * case. First we must check if there is a appropriate boundary on the first
     * day. This is complicated by hte possibility that there might be wrapped
     * end and start timings, which all have to be checked. Next we have to
     * check the second day to see if there is a valid timing
     */
    private long checkBetween10Hours34HoursCase(long startDateMils, long endDateMils, int startBound, int endBound,
            int hoursMin, int startTime, int endTime, Calendar timeToCheck, long returnMils) {
        if (startTime < endBound) {
            if (((int) (endDateMils - startDateMils / MILLISECONDS_PER_MINUTE)) > (endBound - startTime)) {
                if ((endBound - startTime) >= hoursMin) {
                    returnMils = scheduleEventAtEndTime(timeToCheck, startTime);
                }
            } else {
                if ((endTime - startTime) >= hoursMin) {
                    returnMils = scheduleEventAtEndTime(timeToCheck, startTime);
                }
            }
        }
        if (endTime > startBound) {
            if ((endTime - startBound) >= hoursMin) {
                returnMils = scheduleEventOnNextDay(timeToCheck);
            }
        }
        return returnMils;
    }

    /*
     * This method checks if the endTime has wrapped around the start time. If
     * it has, take the endbound since the timing has wrapped around. Else, take
     * the endtime.
     */
    private int getAppropriateEndTime(int endBound, int startTime, int endTime) {
        if (endTime >= startTime) {
            endTime = Math.min(endTime, endBound);
        } else {
            endTime = Math.max(endTime, endBound);
        }
        return endTime;
    }

    /*
     * This method takes in the valid start time as well as a calendar object
     * initialised with the start date. It then returns a long containing the
     * appropriate start date and time.
     */
    private long scheduleEventAtEndTime(Calendar dayOne, int startTime) {
        dayOne.set(Calendar.HOUR_OF_DAY, startTime / MINUTES_PER_HOUR);
        dayOne.set(Calendar.MINUTE, startTime % MINUTES_PER_HOUR);
        dayOne.set(Calendar.SECOND, INITIALISE_AS_ZERO);
        dayOne.set(Calendar.MILLISECOND, INITIALISE_AS_ZERO);
        return dayOne.getTimeInMillis();
    }

    /*
     * This method takes in a start date that is known to have no possible
     * scheduling timings. It then returns the next days earliest possible
     * timing.
     */
    private long scheduleEventOnNextDay(Calendar dayOne) {
        dayOne.set(Calendar.HOUR_OF_DAY, SCHEDULE_STARTING_HOUR);
        dayOne.set(Calendar.MINUTE, INITIALISE_AS_ZERO);
        dayOne.set(Calendar.SECOND, INITIALISE_AS_ZERO);
        dayOne.set(Calendar.MILLISECOND, INITIALISE_AS_ZERO);
        dayOne.add(Calendar.DATE, INITIALISE_AS_NEXT_DAY);
        return dayOne.getTimeInMillis();
    }

    /*
     * This method is for the specific case where no start time can be found. It
     * gets the next best time to schedule an event.
     */
    private long findFirstStartTime(long start, long checkedHours, long end, int startBound, int endBound) {
        int startTime = convertToHoursMinutes(start);
        int hoursMin = (int) (checkedHours / MILLISECONDS_PER_MINUTE);
        startTime = Math.max(startTime, startBound);
        int finishedTime = startTime + hoursMin;
        Calendar correctDate = Calendar.getInstance();
        correctDate.setTimeInMillis(start);
        if (finishedTime <= endBound && startTime >= startBound && isCorrectDay(startTime)) {
            return scheduleEventAtEndTime(correctDate, startTime);
        } else {
            while (correctDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || correctDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                correctDate.add(Calendar.DATE, INITIALISE_AS_NEXT_DAY);
            }
            return scheduleEventOnNextDay(correctDate);
        }
    }

    /*
     * This helper method converts a long timing to a minute representation
     */
    private int convertToHoursMinutes(long longTiming) {
        Calendar temp = Calendar.getInstance();
        temp.setTimeInMillis(longTiming);
        int hourMinuteRep = temp.get(Calendar.HOUR_OF_DAY) * MINUTES_PER_HOUR + temp.get(Calendar.MINUTE);
        return hourMinuteRep;
    }

    /*
     * This filters the event list to return only events with a start and end
     * time.
     */
    private List<ReadOnlyEvent> filterOnlyEventsWithStartEndTime() {
        List<ReadOnlyEvent> taskLists = model.getFilteredTaskList();
        List<ReadOnlyEvent> filterTaskLists = new ArrayList<ReadOnlyEvent>();
        for (ReadOnlyEvent event : taskLists) {
            if (event.hasStartAndEndTime()) {
                filterTaskLists.add(event);
            }
        }
        return filterTaskLists;
    }
}
