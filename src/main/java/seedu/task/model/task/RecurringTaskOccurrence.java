//@@author A0164212U
package seedu.task.model.task;

import seedu.task.commons.util.CollectionUtil;

public class RecurringTaskOccurrence {
    private Timing startTiming;
    private Timing endTiming;
    private boolean complete;

    public RecurringTaskOccurrence(Timing startTime, Timing endTime){
        assert !CollectionUtil.isAnyNull(startTime, endTime);
        this.startTiming = startTime;
        this.endTiming = endTime;
        this.complete = false;
    }

    public Timing getStartTiming() {
        return startTiming;
    }

    public void setStartTiming(Timing startTime) {
        assert startTime != null;
        this.startTiming = startTime;
    }

    public Timing getEndTiming() {
        return endTiming;
    }

    public void setEndTiming(Timing endTime) {
        assert endTime != null;
        this.endTiming = endTime;

    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isComplete() {
        return complete;
    }
}
