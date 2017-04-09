package org.teamstbf.yats.model.item;

import java.util.Comparator;

//@@ author A0102778B

public class ReadOnlyEventComparatorByEndDate implements Comparator<ReadOnlyEvent> {

	@Override
	public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
		if (o1.getEndTime().toString().equals("") && o1.getEndTime().toString().equals("")) {
			return 0;
		}
		if (o1.getEndTime().toString().equals("")) {
			return 1;
		}
		if (o2.getEndTime().toString().equals("")) {
			return -1;
		}
		return o1.getEndTime().getDate().compareTo(o2.getEndTime().getDate());
	}

}
