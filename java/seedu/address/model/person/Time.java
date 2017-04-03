package seedu.address.model.person;

public class Time {
	private int hour;
	private int minute;
	public final String value;
	public final String TIME_DELIMITER = ":";
	public final int MINUTE_ARRAY_INDEX = 1;
	public final int HOUR_ARRAY_INDEX = 0;
	public Time(String time) {
		time = time.trim();
		value = time;
		int[] timeArray = timeFormatConverter(time);
		setMinute(timeArray[MINUTE_ARRAY_INDEX]);
		setHour(timeArray[HOUR_ARRAY_INDEX]);
	}
	
	public String toString() {
		return hour + TIME_DELIMITER + minute;
	}
	public void setHour(int hour) {
		this.hour = hour;
		if (0 <= hour && hour <= 23) {
			this.hour = hour;
		} else {
			throw new IllegalArgumentException("Invalid hour");
		}
	}
	public void setMinute(int minute) {
		this.minute = minute;
		if (0 <= minute && minute <= 59) {
			this.minute = minute;
		} else {
			throw new IllegalArgumentException("Invalid minute");
		}
	}
	public static int[] timeFormatConverter(String time) {
		if (time.length() != 4) {
			throw new IllegalArgumentException("Invalid time format");
		}
		int minute = Integer.parseInt(time.substring(0,2));
		int hour = Integer.parseInt(time.substring(2,4));
		int[] timeArray = {minute, hour};
		return timeArray;
	}
	public static void main(String[] args) {
		Time t = new Time("1231");
		System.out.println(t);
	}
}
