package org.teamstbf.yats.model.item;

import java.util.Comparator;

//@@author A0102778B

public class ReadOnlyEventComparatorByDeadline implements Comparator<ReadOnlyEvent> {

    private static final String NO_DEADLINE_STRING = "";

    @Override
    public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
        if (o1.getDeadline().toString().equals(NO_DEADLINE_STRING) && o1.getDeadline().toString().equals(NO_DEADLINE_STRING)) {
            return 0;
        }
        if (o1.getDeadline().toString().equals(NO_DEADLINE_STRING)) {
            return 1;
        }
        if (o2.getDeadline().toString().equals(NO_DEADLINE_STRING)) {
            return -1;
        }
        return o1.getDeadline().getDate().compareTo(o2.getDeadline().getDate());
    }

}
