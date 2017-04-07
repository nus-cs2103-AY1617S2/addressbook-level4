//@@author A0144813J
package seedu.task.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.validate.ValidationException;
import seedu.task.commons.exceptions.IllegalValueException;
import seedu.task.commons.util.IcsUtil;
import seedu.task.model.task.ReadOnlyTask;
import seedu.task.model.task.Task;

/**
 * Stores task manager data in an ICS file
 */
public class IcsFileStorage {
    /**
     * Saves the given list of tasks to the specified file path.
     *
     * @throws IOException         thrown when the ICS file path is invalid.
     * @throws ValidationException thrown when task data is incompatible for ICS file type.
     */
    public static void saveDataToFile(String path, List<ReadOnlyTask> tasks) throws ValidationException, IOException {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//CS2103JAN2017-W09-B1//Burdens 0.3//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);

        for (ReadOnlyTask task : tasks) {
            VEvent event = IcsTaskAdapter.taskToVEvent(task);
            calendar.getComponents().add(event);
        }
        IcsUtil.saveDataToFile(path, calendar);
    }

    /**
     * Returns a list of task objects from file.
     *
     * @throws ParserException       thrown when the ICS file is corrupted.
     * @throws IOException           thrown when the ICS file path is invalid.
     * @throws IllegalValueException thrown when data in ICS file is incompatible to be added to the task manager.
     */
    public static List<Task> loadDataFromSaveFile(String path)
            throws IOException, ParserException, IllegalValueException {
        Calendar calendar = IcsUtil.getDataFromFile(path);
        List<Task> taskList = new ArrayList<Task>();
        for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
            Component component = (Component) i.next();
            Task task = IcsTaskAdapter.componentToTask(component);
            taskList.add(task);
        }
        return taskList;
    }

}
