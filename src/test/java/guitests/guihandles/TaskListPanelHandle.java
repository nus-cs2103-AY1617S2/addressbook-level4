package guitests.guihandles;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.teamstbf.yats.TestApp;
import org.teamstbf.yats.model.item.Event;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.testutil.TestUtil;

import guitests.GuiRobot;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

/**
 * Provides a handle for the panel containing the person list.
 */
public class TaskListPanelHandle extends GuiHandle {

	public static final int NOT_FOUND = -1;
	public static final String CARD_PANE_ID = "#cardPane";

	private static final String PERSON_LIST_VIEW_ID = "#personListView";

	public TaskListPanelHandle(GuiRobot guiRobot, Stage primaryStage) {
		super(guiRobot, primaryStage, TestApp.APP_TITLE);
	}

	public List<ReadOnlyEvent> getSelectedEvent() {
		ListView<ReadOnlyEvent> eventList = getListView();
		return eventList.getSelectionModel().getSelectedItems();
	}

	public ListView<ReadOnlyEvent> getListView() {
		return getNode(PERSON_LIST_VIEW_ID);
	}

	/**
	 * Returns true if the list is showing the person details correctly and in
	 * correct order.
	 *
	 * @param expectedList
	 *            A list of person in the correct order.
	 */
	public boolean isListMatching(ReadOnlyEvent... events) {
		return this.isListMatching(0, events);
	}

	/**
	 * Returns true if the list is showing the person details correctly and in
	 * correct order.
	 *
	 * @param startPosition
	 *            The starting position of the sub list.
	 * @param events
	 *            A list of person in the correct order.
	 */
	public boolean isListMatching(int startPosition, ReadOnlyEvent... events) throws IllegalArgumentException {
		if (events.length + startPosition != getListView().getItems().size()) {
			throw new IllegalArgumentException(
					"List size mismatched\n" + "Expected " + (getListView().getItems().size() - 1) + " persons");
		}
		assertTrue(this.containsInOrder(startPosition, events));
		for (int i = 0; i < events.length; i++) {
			final int scrollTo = i + startPosition;
			guiRobot.interact(() -> getListView().scrollTo(scrollTo));
			guiRobot.sleep(200);
			if (!TestUtil.compareCardAndEvent(getEventCardHandle(startPosition + i), events[i])) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Clicks on the ListView.
	 */
	public void clickOnListView() {
		Point2D point = TestUtil.getScreenMidPoint(getListView());
		guiRobot.clickOn(point.getX(), point.getY());
	}

	/**
	 * Returns true if the {@code persons} appear as the sub list (in that
	 * order) at position {@code startPosition}.
	 */
	public boolean containsInOrder(int startPosition, ReadOnlyEvent... events) {
		List<ReadOnlyEvent> eventsInList = getListView().getItems();

		// Return false if the list in panel is too short to contain the given
		// list
		if (startPosition + events.length > eventsInList.size()) {
			return false;
		}

		// Return false if any of the persons doesn't match
		for (int i = 0; i < events.length; i++) {
			if (!eventsInList.get(startPosition + i).getTitle().fullName.equals(events[i].getTitle().fullName)) {
				return false;
			}
		}

		return true;
	}

	public EventCardHandle navigateToEvent(String name) {
		guiRobot.sleep(500); // Allow a bit of time for the list to be updated
		final Optional<ReadOnlyEvent> person = getListView().getItems().stream()
				.filter(p -> p.getTitle().fullName.equals(name)).findAny();
		if (!person.isPresent()) {
			throw new IllegalStateException("Name not found: " + name);
		}

		return navigateToEvent(person.get());
	}

	/**
	 * Navigates the listview to display and select the person.
	 */
	public EventCardHandle navigateToEvent(ReadOnlyEvent event) {
		int index = getEventIndex(event);

		guiRobot.interact(() -> {
			getListView().scrollTo(index);
			guiRobot.sleep(150);
			getListView().getSelectionModel().select(index);
		});
		guiRobot.sleep(100);
		return getEventCardHandle(event);
	}

	/**
	 * Returns the position of the person given, {@code NOT_FOUND} if not found
	 * in the list.
	 */
	public int getEventIndex(ReadOnlyEvent targetEvent) {
		List<ReadOnlyEvent> eventsInList = getListView().getItems();
		for (int i = 0; i < eventsInList.size(); i++) {
			if (eventsInList.get(i).getTitle().equals(targetEvent.getTitle())) {
				return i;
			}
		}
		return NOT_FOUND;
	}

	/**
	 * Gets a person from the list by index
	 */
	public ReadOnlyEvent getEvent(int index) {
		return getListView().getItems().get(index);
	}

	public EventCardHandle getEventCardHandle(int index) {
		return getEventCardHandle(new Event(getListView().getItems().get(index)));
	}

	public EventCardHandle getEventCardHandle(ReadOnlyEvent event) {
		Set<Node> nodes = getAllCardNodes();
		Optional<Node> eventCardNode = nodes.stream()
				.filter(n -> new EventCardHandle(guiRobot, primaryStage, n).isSameEvent(event)).findFirst();
		if (eventCardNode.isPresent()) {
			return new EventCardHandle(guiRobot, primaryStage, eventCardNode.get());
		} else {
			return null;
		}
	}

	protected Set<Node> getAllCardNodes() {
		return guiRobot.lookup(CARD_PANE_ID).queryAll();
	}

	public int getNumberOfEvent() {
		return getListView().getItems().size();
	}
}
