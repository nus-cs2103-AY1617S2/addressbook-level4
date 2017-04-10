package org.teamstbf.yats.model.item;

import java.util.Comparator;

public class ReadOnlyEventComparatorByEndDate implements Comparator<ReadOnlyEvent> {

    private static final String NO_END_TIME_STRING = "";

    @Override
    public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
        if (o1.getEndTime().toString().equals(NO_END_TIME_STRING)
                && o1.getEndTime().toString().equals(NO_END_TIME_STRING)) {
            return 0;
        }
        if (o1.getEndTime().toString().equals(NO_END_TIME_STRING)) {
            return 1;
        }
        if (o2.getEndTime().toString().equals(NO_END_TIME_STRING)) {
            return -1;
        }
        return o1.getEndTime().getDate().compareTo(o2.getEndTime().getDate());
    }

}
