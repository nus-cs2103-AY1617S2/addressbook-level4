package org.teamstbf.yats.logic.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;
import org.teamstbf.yats.logic.commands.exceptions.CommandException;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.ReadOnlyEventComparatorByStartDate;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.UniqueEventList;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.model.tag.UniqueTagList;

//@@ author A0102778B

/**
 * Adds a task to the TaskManager.
 */
public class ScheduleCommand extends Command {

	private static final long LAST_TIME_POSSIBLE = 1577846300000L;

	private static final int INITIAL_START_VALUE = -1;

	public static final String COMMAND_WORD = "schedule";

	public static final String MESSAGE_USAGE = COMMAND_WORD + ": Schedule an task or event to the task manager. "
			+ "Parameters: task name [l/location s/START TIME  e/END TIME  d/ description t/TAG] = HOURS\n"
			+ "Example: " + COMMAND_WORD
			+ " meeting with boss l/work p/daily s/7:00pm,18/03/2017  e/9:00pm,18/03/2017  "
			+ "d/get scolded for being lazy t/kthxbye";

	public static final String MESSAGE_SUCCESS = "New event scheduled: %1$s";
	public static final String MESSAGE_SUCCESS_WITH_WARNING = "Warning! Event lasts more than 50 years, please check if valid";
	public static final String MESSAGE_HOURS_INVALID = "The format of hours is invalid - must be a valid long";
	public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the task manager";
	public static final String MESSAGE_TIME_TOO_LONG = "Schedule can only take a timing of at most 10 hours, and it should not be negative - use add for long events";
	private static final long MAXIMUM_EVENT_LENGTH = 36000000L;
	private static final long MINIMUM_EVENT_LENGTH = 0L;
	private final Event toSchedule;
	private String hours;

	/**
	 * Creates an addCommand using a map of parameters
	 *
	 * @param addParam
	 * @throws IllegalValueException
	 *             if any of the parameters are invalid
	 */
	public ScheduleCommand(HashMap<String, Object> parameters) throws IllegalValueException {
		final Set<Tag> tagSet = new HashSet<>();
		for (String tagName : (Set<String>) parameters.get("tag")) {
			tagSet.add(new Tag(tagName));
		}
		this.toSchedule = new Event(parameters, new UniqueTagList(tagSet));
		try {
			this.hours = parameters.get("hours").toString();
		} catch (NullPointerException e) {
			this.hours = "2";
		}
	}

	@Override
	public CommandResult execute() throws CommandException {
		assert model != null;
		try {
			long checkedHours = (long) (Double.parseDouble(this.hours) * 3600000);
			System.out.println(checkedHours);
			if (checkedHours < MINIMUM_EVENT_LENGTH || checkedHours > MAXIMUM_EVENT_LENGTH) {
				throw new IllegalArgumentException();
			}
			List<ReadOnlyEvent> filteredTaskLists = filterOnlyEventsWithStartEndTime();
			Collections.sort(filteredTaskLists, new ReadOnlyEventComparatorByStartDate());
			setStartEndIntervalsForNewTask(checkedHours, filteredTaskLists);
			model.addEvent(toSchedule);
			return new CommandResult(String.format(MESSAGE_SUCCESS, toSchedule));
		} catch (NumberFormatException e) {
			throw new CommandException(MESSAGE_HOURS_INVALID);
		} catch (UniqueEventList.DuplicateEventException e) {
			throw new CommandException(MESSAGE_DUPLICATE_EVENT);
		} catch (IllegalArgumentException e) {
			throw new CommandException(MESSAGE_TIME_TOO_LONG);
		}
	}

	private void setStartEndIntervalsForNewTask(long checkedHours, List<ReadOnlyEvent> filteredTaskLists)
			throws IllegalArgumentException {
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

	private long getStartInterval(long checkedHours, List<ReadOnlyEvent> filteredTaskLists)
			throws IllegalArgumentException {
		long max = new Date().getTime();
		long curr;
		long start = INITIAL_START_VALUE;
		int startBound = 8;
		int endBound = 18;
		long getStartTime;

		for (ReadOnlyEvent event : filteredTaskLists) {
			System.out.println(max);
			System.out.println(event);
			curr = event.getStartTime().getDate().getTime();
			if (curr > max) {
				System.out.println("curr bigger than max");
				if ((curr - max) >= checkedHours) {
					System.out.println("potential slot");
					getStartTime = findStartTime(max, checkedHours, curr, startBound, endBound);
					if (getStartTime != -1) {
						start = getStartTime;
						break;
					}
					System.out.println("didn't fit requirements");
				}
			}
			max = Math.max(max, event.getEndTime().getDate().getTime());
			System.out.println("trying next time");
		}
		if (start == INITIAL_START_VALUE) {
			start = findFirstStartTime(max, checkedHours, LAST_TIME_POSSIBLE, startBound, endBound);
		}
		return start;
	}

	private long findStartTime(long start, long checkedHours, long end, int startBound, int endBound) {
		int hoursMin = (int) (checkedHours / 60000L);
		Calendar dayOne = Calendar.getInstance();
		int startTime = convertToHoursMinutes(start, dayOne);
		Calendar dayTwo = Calendar.getInstance();
		int endTime = convertToHoursMinutes(end, dayTwo);
		startBound = startBound * 60;
		endBound = endBound * 60;
		System.out.println(end - start);
		if (end - start <= 28800000L) {
			startTime = Math.max(startTime, startBound);
			if (endTime >= startTime) {
				endTime = Math.min(endTime, endBound);
			} else {
				endTime = Math.max(endTime, endBound);
			}
			System.out.println("less than 8 hours case");
			System.out.println("startTime is" + startTime);
			System.out.println("endTime is" + endTime);
			System.out.println("hoursTime is" + hoursMin);
			if ((endTime - startTime) >= hoursMin) {
				dayOne.set(Calendar.HOUR_OF_DAY, startTime / 60);
				dayOne.set(Calendar.MINUTE, startTime % 60);
				System.out.println("match found");
				return dayOne.getTimeInMillis();
			}
			System.out.println("match not found");
		} else if (end - start >= 122400000L) {
			startTime = Math.max(startTime, startBound);
			if ((endBound - startTime) >= hoursMin) {
				dayOne.set(Calendar.HOUR_OF_DAY, startTime / 60);
				dayOne.set(Calendar.MINUTE, startTime % 60);
				return dayOne.getTimeInMillis();
			} else {
				dayOne.set(Calendar.HOUR_OF_DAY, 8);
				dayOne.set(Calendar.MINUTE, 0);
				dayOne.add(Calendar.DATE, 1);
				return dayOne.getTimeInMillis();
			}
		} else {
			if (startTime < endBound) {
				if (((int) (end - start / 60000L)) > (endBound - startTime)) {
					if ((endBound - startTime) >= hoursMin) {
						dayOne.set(Calendar.HOUR_OF_DAY, startTime / 60);
						dayOne.set(Calendar.MINUTE, startTime % 60);
						return dayOne.getTimeInMillis();
					}
				} else {
					if ((endTime - startTime) >= hoursMin) {
						dayOne.set(Calendar.HOUR_OF_DAY, startTime / 60);
						dayOne.set(Calendar.MINUTE, startTime % 60);
						return dayOne.getTimeInMillis();
					}
				}
			}
			if (endTime > startBound) {
				if ((endTime - startBound) >= hoursMin) {
					dayOne.set(Calendar.HOUR_OF_DAY, 8);
					dayOne.set(Calendar.MINUTE, 0);
					dayOne.add(Calendar.DATE, 1);
					return dayOne.getTimeInMillis();
				}
			}
		}
		return -1;
	}

	private long findFirstStartTime(long start, long checkedHours, long end, int startBound, int endBound) {
		startBound = startBound * 60;
		endBound = endBound * 60;
		Calendar dayOne = Calendar.getInstance();
		int startTime = convertToHoursMinutes(start, dayOne);
		int hoursMin = (int) (checkedHours / 60000L);
		startTime = Math.max(startTime, startBound);
		int finishedTime = startTime + hoursMin;
		if (finishedTime <= endBound && startTime >= startBound) {
			dayOne.set(Calendar.HOUR_OF_DAY, startTime / 60);
			dayOne.set(Calendar.MINUTE, startTime % 60);
			return dayOne.getTimeInMillis();
		} else {
			dayOne.set(Calendar.HOUR_OF_DAY, 8);
			dayOne.set(Calendar.MINUTE, 0);
			dayOne.add(Calendar.DATE, 1);
			return dayOne.getTimeInMillis();
		}
	}

	private int convertToHoursMinutes(long longTiming, Calendar calendar) {
		calendar.setTimeInMillis(longTiming);
		int hourMinuteRep = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
		return hourMinuteRep;
	}

	private List<ReadOnlyEvent> filterOnlyEventsWithStartEndTime() {
		List<ReadOnlyEvent> taskLists = model.getFilteredTaskList();
		List<ReadOnlyEvent> filterTaskLists = new ArrayList<ReadOnlyEvent>();
		for (ReadOnlyEvent event : taskLists) {
			if (event.hasStartEndTime()) {
				filterTaskLists.add(event);
			}
		}
		return filterTaskLists;
	}
}
