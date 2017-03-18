package seedu.address.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;

import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.validate.ValidationException;

/**
 * Helps with reading from and writing to XML files.
 */
public class IcsUtil {

    /**
     * Returns the ics data in the file as an object of the specified type.
     *
     * @param file           Points to a valid xml file containing data that match the {@code classToConvert}.
     *                       Cannot be null.
     * @param classToConvert The class corresponding to the xml data.
     *                       Cannot be null.
     * @throws FileNotFoundException Thrown if the file is missing.
     * @throws JAXBException         Thrown if the file is empty or does not have the correct format.
     */
    /*
    @SuppressWarnings("unchecked")
    public static <T> T getDataFromFile(File file, Class<T> classToConvert)
            throws FileNotFoundException, JAXBException {

        assert file != null;
        assert classToConvert != null;

        if (!FileUtil.isFileExists(file)) {
            throw new FileNotFoundException("File not found : " + file.getAbsolutePath());
        }

        JAXBContext context = JAXBContext.newInstance(classToConvert);
        Unmarshaller um = context.createUnmarshaller();

        return ((T) um.unmarshal(file));
    }
    */

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
    public static <T> void saveDataToFile(String path, Calendar calendar) throws ValidationException, IOException {

        assert path != null;
        assert path.endsWith(".ics");
        assert calendar != null;

        FileOutputStream fout = new FileOutputStream(path);
        CalendarOutputter outputter = new CalendarOutputter();
        outputter.output(calendar, fout);
    }

}
