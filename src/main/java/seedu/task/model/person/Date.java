/**
 * 
 */
package seedu.address.model.person;

/**
 * @author Daniel Mullen
 *
 */
public class Date {
	private int day;
	private int month;
	private int year;
	public final String value;
	private final String DATE_DELIMITER = "-";
	private final int DAY_ARRAY_INDEX = 0;
	private final int MONTH_ARRAY_INDEX = 1;
	private final int YEAR_ARRAY_INDEX = 2;
	
	private static final int DAY_START_INDEX = 0;
	private static final int DAY_END_INDEX = 2;
	private static final int MONTH_START_INDEX = 2;
	private static final int MONTH_END_INDEX = 4;
	private static final int YEAR_START_INDEX = 4;
	private static final int YEAR_END_INDEX = 6;
	
	public Date (String date) {
		date = date.trim();
		value = date;
		int[] dateArray = dateFormatConverter(date);
		setDay(dateArray[DAY_ARRAY_INDEX]);
		setMonth(dateArray[MONTH_ARRAY_INDEX]);
		setYear(dateArray[YEAR_ARRAY_INDEX]);
	}
	
	public void setDay (int day) {
		if (day > 0 && day <= 31) {
			this.day = day;
		} else {
			throw new IllegalArgumentException("Invalid day");
		}
	}
	public void setMonth (int month) {
		if (month > 0 && month <= 12) {
			this.month = month;
		} else {
			throw new IllegalArgumentException("Invalid month");
		}
	}
	public void setYear (int year) {
		if (year > 0) {
			this.year = year;
		} else {
			throw new IllegalArgumentException("Invalid year");
		}
	}
	
	public String toString() {
		return day + DATE_DELIMITER + month + DATE_DELIMITER + year;
	}
	public static int[] dateFormatConverter(String date) {
		if (date.length() != 6) {
			throw new IllegalArgumentException("Invalid date format");
		}
		int day = Integer.parseInt(date.substring(DAY_START_INDEX, DAY_END_INDEX));
		int month = Integer.parseInt(date.substring(MONTH_START_INDEX, MONTH_END_INDEX));
		int year = Integer.parseInt(date.substring(YEAR_START_INDEX, YEAR_END_INDEX));
		int[] returnArray = {day, month, year};
		return returnArray;
	}
}
