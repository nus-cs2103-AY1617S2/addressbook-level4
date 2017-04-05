package seedu.jobs.model.calendar;

import seedu.jobs.model.task.Task;
import seedu.jobs.model.task.Time;

public class TimeCalendar {
	private Time toConvert;
	
	public TimeCalendar (Time time) {
		toConvert = time;
	}
	
	public String extractYear (Time time) {
		String year = Time.extractDate(time.value).substring(6);
		return year;
	}
	
	public String extractMonth (Time time) {
		String month = Time.extractDate(time.value).substring(3,5);
		return month;
	}
	
	public String extractDate (Time time) {
		String date = Time.extractDate(time.value).substring(0,2);
		return date;
	}
	
	public String extractHour (Time time) {
		String hour = Time.extractTime(time.value).substring(0,2);
		return hour;
	}
	
	public String extractMin (Time time) {
		String min = Time.extractTime(time.value).substring(3);
		return min;
	}
	
	public String getCompleteTime(Time time) {
		String startTime = this.extractYear(time) + "-" + this.extractMonth(time) + "-" + this.extractDate(time)
			+ "T" + this.extractHour(time) + ":" + this.extractMin(time) + ":00+08:00";
		return startTime;
	}
	
	public String toString() {
		return getCompleteTime(toConvert);
	}
}
