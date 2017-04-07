package seedu.task.model.task;

//@@author A0152855Y

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class NattyParser {

    static Parser parser;

    public NattyParser() {
        parser = new Parser();
    }

    public List<Date> parse(String input) {

        List<DateGroup> groups = parser.parse(input);
        if (groups.isEmpty())
            return null;
        else {
            DateGroup dategroup = groups.get(0);
            List<Date> result = new ArrayList<Date>();
            for (Date date : dategroup.getDates())
                result.add(date);
            return result;
        }
    }

}
