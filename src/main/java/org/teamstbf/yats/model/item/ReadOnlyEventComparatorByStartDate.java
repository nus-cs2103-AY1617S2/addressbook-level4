package org.teamstbf.yats.model.item;

import java.util.Comparator;

//@@ author A0102778B

public class ReadOnlyEventComparatorByStartDate implements Comparator<ReadOnlyEvent> {

	@Override
	public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
		if (o1.getStartTime().toString().equals("") && o1.getStartTime().toString().equals("")) {
			return 0;
		}
		if (o1.getStartTime().toString().equals("")) {
			return 1;
		}
		if (o2.getStartTime().toString().equals("")) {
			return -1;
		}
		return o1.getStartTime().getDate().compareTo(o2.getStartTime().getDate());
	}

}
