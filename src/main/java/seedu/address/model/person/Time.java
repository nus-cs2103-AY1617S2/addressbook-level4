package seedu.address.model.person;

public class Time {
	private int hour;
	private int minute;
	private String hourStr;
	private String minuteStr;
	public final String value;
	public final String TIME_DELIMITER = ":";
	public final int MINUTE_ARRAY_INDEX = 0;
	public final int HOUR_ARRAY_INDEX = 1;
	public Time(String time) {
		time = time.trim();
		value = time;
		int[] timeArray = timeFormatConverter(time);
		setMinute(timeArray[MINUTE_ARRAY_INDEX]);
		setHour(timeArray[HOUR_ARRAY_INDEX]);
	}
	
	public String toString() {
		return hourStr + TIME_DELIMITER + minuteStr;
	}
	public void setHour(int hour) {
		if (0 <= hour && hour <= 23) {
			this.hour = hour;
		} else {
			throw new IllegalArgumentException("Invalid hour");
		}
	}
	public void setMinute(int minute) {
		if (0 <= minute && minute <= 59) {
			this.minute = minute;
		} else {
			throw new IllegalArgumentException("Invalid minute");
		}
	}
	public int[] timeFormatConverter(String time) {
		if (time.length() < 3 || time.length() > 5) {
			throw new IllegalArgumentException("Invalid time format");
		} else if (time.length() == 3) {
			this.minuteStr = time.substring(1,3);
			this.hourStr = time.substring(0,1);
		} else if (time.length() == 4) {
			if (time.substring(1,2).equals(":")) {
				this.minuteStr = time.substring(2,4);
				this.hourStr = "0" + time.substring(0,1);
			} else {
				this.minuteStr = time.substring(2,4);
				this.hourStr = time.substring(0,2);
			}
		} else {
			this.minuteStr = time.substring(3,5);
			this.hourStr = time.substring(0,2);
		}
		int minute = Integer.parseInt(minuteStr);
		int hour = Integer.parseInt(hourStr);
		int[] timeArray = {minute, hour};
		return timeArray;
	}
	public static void main(String[] args) {
		Time t = new Time("0204");
		System.out.println(t);
		t = new Time("305");
		System.out.println(t);
		t = new Time("4:10");
		System.out.println(t);
		t = new Time("12:30");
		System.out.println(t);
		t = new Time("0:01");
		System.out.println(t);
	}
}
