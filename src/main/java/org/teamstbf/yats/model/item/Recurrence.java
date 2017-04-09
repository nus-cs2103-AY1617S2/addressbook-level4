package org.teamstbf.yats.model.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

import edu.emory.mathcs.backport.java.util.Arrays;

//@@author A0116219L
public class Recurrence {

	public static final String RECURRENCE_NONE = "none";
	public static final String RECURRENCE_DAY = "daily";
	public static final String RECURRENCE_WEEK = "weekly";
	public static final String RECURRENCE_MONTH = "monthly";
	public static final String RECURRENCE_YEAR = "yearly";
	public static final int RECURRENCE_INCREMENT = 1;
	public static final String MESSAGE_RECURRENCE_DATE_CONSTRAINTS = "Recurrence date must be in dd/MM/yyyy format.";
	public static final String MESSAGE_RECURRENCE_CONSTRAINTS = "Recurrence must be none, daily, weekly, monthly or yearly";
	public static final String RECURRENCE_VALIDATION_REGEX = ".*(none|daily|weekly|monthly|yearly).*";
	public static final String RECURRENCE_DATE_FORMAT = "dd/MM/yyyy";
	public static final String DELIMITER_DONE_LIST = ",";
	public static final String STRING_EMPTY = "";

	Date startDate;
	String periodicity;
	List<String> doneList;
	static SimpleDateFormat dateFormat = new SimpleDateFormat(RECURRENCE_DATE_FORMAT);
	int recurrencePeriod;

	/*
	 * Creates an empty Recurrence object
	 */
	public Recurrence() {
		this.startDate = new Date(Long.MIN_VALUE);
		this.periodicity = RECURRENCE_NONE;
		this.doneList = new ArrayList<String>();
	}

	public Recurrence(Date date, String recurrence) throws IllegalValueException {
		if (!isValidPeriod(recurrence)) {
			throw new IllegalValueException(MESSAGE_RECURRENCE_CONSTRAINTS);
		}

		this.startDate = date;
		this.periodicity = recurrence;
		setPeriod(recurrence);
		this.doneList = new ArrayList<String>();
	}

	public Recurrence(String startDate, String periodicity, String doneList) throws IllegalValueException {
		try {
			this.startDate = dateFormat.parse(startDate);
		} catch (ParseException pe) {
			throw new IllegalValueException(MESSAGE_RECURRENCE_DATE_CONSTRAINTS);
		}
		if (!isValidPeriod(periodicity)) {
			throw new IllegalValueException(MESSAGE_RECURRENCE_CONSTRAINTS);
		}
		this.periodicity = periodicity;
		setPeriod(periodicity);
		this.doneList = getDoneListFromString(doneList);
	}

	private List<String> getDoneListFromString(String doneListString) {
		// return an empty List if string is empty
		if (doneListString.equals(STRING_EMPTY)) {
			return new ArrayList<String>();
		}
		return new ArrayList<String>(Arrays.asList(doneListString.split(DELIMITER_DONE_LIST)));
	}

	public static boolean isValidPeriod(String recurrence) {
		return recurrence.matches(RECURRENCE_VALIDATION_REGEX);
	}

	private void setPeriod(String recurrence) throws IllegalValueException {
		if (this.periodicity.equals(RECURRENCE_DAY)) {
			this.recurrencePeriod = Calendar.DAY_OF_WEEK;
		} else if (this.periodicity.equals(RECURRENCE_WEEK)) {
			this.recurrencePeriod = Calendar.WEEK_OF_YEAR;
		} else if (this.periodicity.equals(RECURRENCE_MONTH)) {
			this.recurrencePeriod = Calendar.MONTH;
		} else if (this.periodicity.equals(RECURRENCE_YEAR)) {
			this.recurrencePeriod = Calendar.YEAR;
		} else {
			throw new IllegalValueException(MESSAGE_RECURRENCE_CONSTRAINTS);
		}
	}

	public boolean hasOccurenceOn(Date day) {
		throw new UnsupportedOperationException();
	}

	public String getStartTimeString() {
		return this.dateFormat.format(startDate);
	}

	public String getPeriodicity() {
		return this.periodicity;
	}

	public String getDoneListString() {
		if (this.doneList.isEmpty()) {
			return STRING_EMPTY;
		}
		StringBuilder sb = new StringBuilder();
		for (String doneDate : this.doneList) {
			sb.append(doneDate);
			sb.append(DELIMITER_DONE_LIST);
		}
		return sb.toString();
	}

	public List<Date> getOccurenceBetween(Date start, Date end) {
		List<Date> occurenceList = new ArrayList<Date>();
		Calendar startTime = Calendar.getInstance();
		Calendar startBound = Calendar.getInstance();
		Calendar endBound = Calendar.getInstance();
		startTime.setTime(this.startDate);
		startBound.setTime(start);
		endBound.setTime(end);

		for (Date date = startTime.getTime(); startTime.before(endBound); startTime.add(recurrencePeriod,
				RECURRENCE_INCREMENT), date = startTime.getTime()) {
			if (date.before(end) && date.after(start)) {
				occurenceList.add(date);
			}
		}

		return occurenceList;
	}

	public String getLatestUndoneDateString() {
		if (doneList.isEmpty()) {
			return dateFormat.format(startDate);
		}
		String lastDateString = doneList.get(doneList.size() - 1);
		Calendar lastCalendar = Calendar.getInstance();
		try {
			lastCalendar.setTime(dateFormat.parse(lastDateString));
		} catch (ParseException pe) {
			// TODO: handle parse exception
			;
		}
		return dateFormat.format(getNextOccurence(lastCalendar).getTime());
	}

	public Date getLatestUndoneDate() throws ParseException {
		return dateFormat.parse(getLatestUndoneDateString());
	}

	public Calendar getNextOccurence(Calendar occurence) {
		occurence.add(this.recurrencePeriod, RECURRENCE_INCREMENT);
		return occurence;
	}

	public void markOccurenceDone() {
		// if no occurence yet, mark the start date as done
		if (doneList.isEmpty()) {
			doneList.add(dateFormat.format(this.startDate));
		} else {
			doneList.add(getLatestUndoneDateString());
		}
	}

	public void markOccurenceUndone() throws NoSuchElementException {
		if (this.doneList.isEmpty()) {
			throw new NoSuchElementException();
		}
		// remove last occurence
		this.doneList.remove(doneList.size() - 1);
	}

	public boolean hasDoneOccurence() {
		return !doneList.isEmpty();
	}

}
