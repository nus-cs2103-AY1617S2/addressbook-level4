package seedu.watodo.logic.parser;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class DateTimeParser {
    public static final String MESSAGE_DATETIME_CONSTRAINTS = "Date and time format must be a date/day, time or both";
    public static final Parser DATE_TIME_PARSER = new Parser();

    public DateTimeParser() {
    }

    public static void parse() {
        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse("the day before next thursday");
    for(DateGroup group:groups) {
      List<Date> dates = group.getDates();
      int line = group.getLine();
      int column = group.getPosition();
      String matchingValue = group.getText();
      String syntaxTree = group.getSyntaxTree().toStringTree();
      boolean isRecurreing = group.isRecurring();
      Date recursUntil = group.getRecursUntil();
    }
    }

}
