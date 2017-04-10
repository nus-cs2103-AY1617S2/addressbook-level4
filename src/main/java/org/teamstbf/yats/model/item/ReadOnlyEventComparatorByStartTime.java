package org.teamstbf.yats.model.item;

import java.util.Comparator;

public class ReadOnlyEventComparatorByStartTime implements Comparator<ReadOnlyEvent> {

	private static final String NO_START_TIME_STRING = "";

    @Override
	public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
		if (o1.getStartTime().toString().equals(NO_START_TIME_STRING) && o1.getStartTime().toString().equals(NO_START_TIME_STRING)) {
			return 0;
		}
		if (o1.getStartTime().toString().equals(NO_START_TIME_STRING)) {
			return -1;
		}
		if (o2.getStartTime().toString().equals(NO_START_TIME_STRING)) {
			return 1;
		}
		return o1.getStartTime().getDate().compareTo(o2.getStartTime().getDate());
	}

}
