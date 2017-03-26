//@@author A0164212U
package seedu.task.model.task;

import seedu.task.commons.util.CollectionUtil;

public class RecurringTaskOccurrence {
    private Timing startTime;
    private Timing endTime;
    private boolean complete;

    public RecurringTaskOccurrence(Timing startTime, Timing endTime){
        assert !CollectionUtil.isAnyNull(startTime, endTime);
        this.startTime = startTime;
        this.endTime = endTime;
        this.complete = false;
    }

    public Timing getStartTime() {
        return startTime;
    }

    public void setStartTime(Timing startTime) {
        assert startTime != null;
        this.startTime = startTime;
    }

    public Timing getEndTime() {
        return endTime;
    }

    public void setEndTime(Timing endTime) {
        assert endTime != null;
        this.endTime = endTime;

    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }
}
