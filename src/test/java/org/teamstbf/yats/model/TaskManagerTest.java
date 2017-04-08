package org.teamstbf.yats.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.tag.Tag;
import org.teamstbf.yats.testutil.TypicalTestEvents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskManagerTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private final TaskManager taskManager = new TaskManager();

	@Test
	public void constructor() {
		assertEquals(Collections.emptyList(), taskManager.getTaskList());
		assertEquals(Collections.emptyList(), taskManager.getTagList());
	}

	@Test
	public void resetData_null_throwsAssertionError() {
		thrown.expect(AssertionError.class);
		taskManager.resetData(null);
	}

	@Test
	public void resetData_withValidReadOnlyTaskManager_replacesData() {
		TaskManager newData = new TypicalTestEvents().getTypicalTaskManager();
		taskManager.resetData(newData);
		assertEquals(newData, taskManager);
	}

	@Test
	public void resetData_withDuplicateTags_throwsAssertionError() {
		TaskManager typicalTaskManager = new TypicalTestEvents().getTypicalTaskManager();
		List<ReadOnlyEvent> newEvents = typicalTaskManager.getTaskList();
		List<Tag> newTags = new ArrayList<>(typicalTaskManager.getTagList());
		// Repeat the first tag twice
		newTags.add(newTags.get(0));
		TaskManagerStub newData = new TaskManagerStub(newEvents, newTags);

		thrown.expect(AssertionError.class);
		taskManager.resetData(newData);
	}

	/**
	 * A stub ReadOnlyAddressBook whose persons and tags lists can violate
	 * interface constraints.
	 */
	private static class TaskManagerStub implements ReadOnlyTaskManager {
		private final ObservableList<ReadOnlyEvent> events = FXCollections.observableArrayList();
		private final ObservableList<Tag> tags = FXCollections.observableArrayList();

		TaskManagerStub(Collection<? extends ReadOnlyEvent> events, Collection<? extends Tag> tags) {
			this.events.setAll(events);
			this.tags.setAll(tags);
		}

		@Override
		public ObservableList<ReadOnlyEvent> getTaskList() {
			return events;
		}

		@Override
		public ObservableList<Tag> getTagList() {
			return tags;
		}
	}

}
