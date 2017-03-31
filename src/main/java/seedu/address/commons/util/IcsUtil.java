//@@author A0144813J
package seedu.address.commons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.validate.ValidationException;

/**
 * Helps with reading from and writing to XML files.
 */
public class IcsUtil {

    /**
     * Returns the ics data in the file as an object of the specified type.
     *
     * @param path           Points to a valid ics file containing calendar data.
     *                       Cannot be null.
     * @throws ParserException Thrown if the file path is invalid.
     * @throws IOException     Thrown if the data in calendar file is invalid.
     */

    public static Calendar getDataFromFile(String path) throws IOException, ParserException {

        assert path != null;

        FileInputStream fin = new FileInputStream(path);
        CalendarBuilder builder = new CalendarBuilder();
        Calendar calendar = builder.build(fin);
        fin.close();
        return calendar;
    }

    /**
     * Saves the tasks in the task manager in ics format.
     *
     * @param path Points to a valid path to the intended iCal file.
     *             Cannot be null.
     *             Must end with ".ics".
     * @param calendar Points to the Calendar object that contains all current tasks in the task manager.
     *                 Cannot be null.
     * @throws IOException          Thrown if the file path is invalid.
     * @throws ValidationException  Thrown if the data in calendar is invalid.
     */
    public static void saveDataToFile(String path, Calendar calendar) throws ValidationException, IOException {

        assert path != null;
        assert path.endsWith(".ics");
        assert calendar != null;

        File out = new File(path);
        out.createNewFile();

        FileOutputStream fout = new FileOutputStream(path);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);
        fout.close();

    }

}
