package org.teamstbf.yats.model;

import java.util.Comparator;

import org.teamstbf.yats.model.item.ReadOnlyEvent;

public class ReadOnlyEventComparatorByStartDate implements Comparator<ReadOnlyEvent> {

	@Override
	public int compare(ReadOnlyEvent o1, ReadOnlyEvent o2) {
		return o1.getStartTime().getDate().compareTo(o2.getStartTime().getDate());
	}
}
