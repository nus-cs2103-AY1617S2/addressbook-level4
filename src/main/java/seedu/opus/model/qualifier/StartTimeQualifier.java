package seedu.opus.model.qualifier;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.opus.logic.parser.DateTimeParser;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.ReadOnlyTask;

//@@author A0126345J
/**
 * Compares and filters the startTime attribute of a task in the task manager
 */
public class StartTimeQualifier implements Qualifier  {

    private String startTime;

    public StartTimeQualifier(String startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        Optional<LocalDateTime> inputStartTime = DateTimeParser.parse(this.startTime);
        Optional<DateTime> taskStartTime = task.getStartTime();
        if (!inputStartTime.isPresent() || !taskStartTime.isPresent()) {
            return false;
        }
        return inputStartTime.get().isAfter(taskStartTime.get().dateTime);
    }
}
//@@author

