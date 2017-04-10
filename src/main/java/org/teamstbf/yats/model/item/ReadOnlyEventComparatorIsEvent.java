package org.teamstbf.yats.model.item;

import java.util.Comparator;

//@@author A0102778B

public class ReadOnlyEventComparatorIsEvent implements Comparator<ReadOnlyEvent> {
    private static final String NOT_EVENT_STRING_REPRESENTATION = "";

    @Override
    public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
        if (o1.getStartTime().toString().equals(NOT_EVENT_STRING_REPRESENTATION)) {
            return 1;
        } else if (o2.getStartTime().toString().equals(NOT_EVENT_STRING_REPRESENTATION)) {
            return -1;
        }
        return 0;
    }

}
