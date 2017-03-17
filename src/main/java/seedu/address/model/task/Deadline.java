package seedu.address.model.task;

import java.time.ZonedDateTime;

public class Deadline {
    private ZonedDateTime dateTime;

    public Deadline(ZonedDateTime dateTime) {
        assert dateTime != null;

        // TODO check that it is after today

        this.dateTime = dateTime;
    }

    public ZonedDateTime getValue() {
        return dateTime;
    }
}
