package seedu.address.model.task;

import java.time.ZonedDateTime;

public class StartEndDateTime {

    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;

    public StartEndDateTime(ZonedDateTime startDateTime, ZonedDateTime endDateTime) {
        assert startDateTime != null && endDateTime != null;

        // TODO check if endDateTime > startDateTime, this means endDateTime != startDateTime as well
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public ZonedDateTime getStartDateTime() {
        return startDateTime;
    }

    public ZonedDateTime getEndDateTime() {
        return endDateTime;
    }

}
