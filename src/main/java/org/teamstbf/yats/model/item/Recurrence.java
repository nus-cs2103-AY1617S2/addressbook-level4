package org.teamstbf.yats.model.item;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.teamstbf.yats.commons.exceptions.IllegalValueException;

public class Recurrence {
    
    public static final String RECURRENCE_NONE = "none";
    public static final String RECURRENCE_DAY = "daily";
    public static final String RECURRENCE_WEEK = "weekly";
    public static final String RECURRENCE_MONTH = "monthly";
    public static final String RECURRENCE_YEAR = "yearly";
    public static final int RECURRENCE_INCREMENT = 1;
    public static final String MESSAGE_RECURRENCE_CONSTRAINTS = "Recurrence must be none, daily, weekly, monthly or yearly";
    public static final String RECURRENCE_VALIDATION_REGEX = ".*(none|daily|weekly|monthly|yearly).*";
    public static final String RECURRENCE_DATE_FORMAT = "dd/MM/yyyy";
    
    Date startDate;
    String recurrence;
    List<String> doneList;
    static SimpleDateFormat dateFormat = new SimpleDateFormat(RECURRENCE_DATE_FORMAT);
    int recurrencePeriod;

    public Recurrence(Date date, String recurrence) throws IllegalValueException {
        if (!isValidPeriod(recurrence)) {
            throw new IllegalValueException(MESSAGE_RECURRENCE_CONSTRAINTS);
        }
        
        this.startDate = date;
        this.recurrence = recurrence;
        setPeriod(recurrence);
        this.doneList = new ArrayList<String>();
    }
    
    private static boolean isValidPeriod(String recurrence) {
        return recurrence.matches(RECURRENCE_VALIDATION_REGEX);
    }
    
    private void setPeriod(String recurrence) throws IllegalValueException {
        if (this.recurrence.equals(RECURRENCE_DAY)) {
            this.recurrencePeriod = Calendar.DAY_OF_WEEK;
        } else if (this.recurrence.equals(RECURRENCE_WEEK)) {
            this.recurrencePeriod = Calendar.WEEK_OF_YEAR;
        } else if (this.recurrence.equals(RECURRENCE_MONTH)) {
            this.recurrencePeriod = Calendar.MONTH;
        } else if (this.recurrence.equals(RECURRENCE_YEAR)) {
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
        return this.recurrence;
    }
    
    public List<Date> getOccurenceBetween(Date start, Date end) {
        List<Date> occurenceList = new ArrayList<Date>();
        Calendar startTime = Calendar.getInstance();
        Calendar startBound = Calendar.getInstance();
        Calendar endBound = Calendar.getInstance();
        startTime.setTime(this.startDate);
        startBound.setTime(start);
        endBound.setTime(end);
        
        for(Date date = startTime.getTime();
                startTime.before(endBound);
                startTime.add(recurrencePeriod, RECURRENCE_INCREMENT), date = startTime.getTime()) {
            if (date.before(end) && date.after(start)) {
                occurenceList.add(date);
            }
        }
        
        return occurenceList;
    }
    
    public String getLatestUndoneString() {
        if (doneList.isEmpty()) {
            return dateFormat.format(startDate);
        }
        String lastDateString = (String) doneList.get(doneList.size()-1);
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
        return dateFormat.parse(getLatestUndoneString());
    }
    
    public Calendar getNextOccurence(Calendar occurence) {
        occurence.add(this.recurrencePeriod, RECURRENCE_INCREMENT);
        return occurence;
    }
    
    public void markOccurenceDone() {
        //if no occurence yet, mark the start date as done
        if (doneList.isEmpty()) {
            doneList.add(dateFormat.format(this.startDate));
        } else {
            doneList.add(getLatestUndoneString());
        }
    }
    
    public void markOccurenceUndone() throws NoSuchElementException {
        if (this.doneList.isEmpty()) {
            throw new NoSuchElementException();
        }
        //remove last occurence
        this.doneList.remove(doneList.size()-1);
    }
    
    public boolean hasDoneOccurence() {
        return !doneList.isEmpty();
    }

}
