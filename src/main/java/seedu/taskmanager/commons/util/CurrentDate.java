package seedu.taskmanager.commons.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CurrentDate {

	String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
}
