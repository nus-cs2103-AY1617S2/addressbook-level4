package seedu.opus.model.qualifier;

import java.time.LocalDateTime;
import java.util.Optional;

import seedu.opus.logic.parser.DateTimeParser;
import seedu.opus.model.task.DateTime;
import seedu.opus.model.task.ReadOnlyTask;

//@@author A0126345J
/**
 * Compares and filters the endTime attribute of a task in the task manager
 */
public class EndTimeQualifier implements Qualifier {

    private String endTime;

    public EndTimeQualifier(String endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        Optional<LocalDateTime> inputEndTime = DateTimeParser.parse(this.endTime);
        Optional<DateTime> taskEndTime = task.getEndTime();
        if (!inputEndTime.isPresent() || !taskEndTime.isPresent()) {
            return false;
        }
        return inputEndTime.get().isAfter(taskEndTime.get().dateTime);
    }
}
//@@author

