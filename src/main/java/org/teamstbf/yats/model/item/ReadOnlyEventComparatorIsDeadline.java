package org.teamstbf.yats.model.item;

import java.util.Comparator;

public class ReadOnlyEventComparatorIsDeadline implements Comparator<ReadOnlyEvent> {
	public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
		if (o1.getDeadline().toString().equals("")) {
			return 1;
		} else if (o2.getDeadline().toString().equals("")) {
			return -1;
		}
		return 0;
	}
}
