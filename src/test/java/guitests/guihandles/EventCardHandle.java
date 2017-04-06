package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.tag.UniqueTagList;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class EventCardHandle extends GuiHandle {
	private static final String NAME_FIELD_ID = "#name";
	private static final String STARTTIME_FIELD_ID = "#startTime";
	private static final String ENDTIME_FIELD_ID = "#endTime";
	private static final String DESCRIPTION_FIELD_ID = "#description";
	private static final String LOCATION_FIELD_ID = "#loc";
	private static final String TAGS_FIELD_ID = "#tags";

	private Node node;

	public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
		super(guiRobot, primaryStage, null);
		this.node = node;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof EventCardHandle) {
			EventCardHandle handle = (EventCardHandle) obj;
			return getTitle().equals(handle.getTitle()) && getStartTime().equals(handle.getStartTime())
					&& getEndTime().equals(handle.getEndTime()) && getDescription().equals(handle.getDescription())
					&& getLocation().equals(handle.getLocation()) && getEndTime().equals(handle.getEndTime())
					&& getTags().equals(handle.getTags());
		}
		return super.equals(obj);
	}

	public String getDescription() {
		return getTextFromLabel(DESCRIPTION_FIELD_ID);
	}

	public String getEndTime() {
		return getTextFromLabel(ENDTIME_FIELD_ID);
	}

	public String getLocation() {
		return getTextFromLabel(LOCATION_FIELD_ID);
	}

	public String getStartTime() {
		return getTextFromLabel(STARTTIME_FIELD_ID);
	}

	public List<String> getTags() {
		return getTags(getTagsContainer());
	}

	private List<String> getTags(Region tagsContainer) {
		return tagsContainer.getChildrenUnmodifiable().stream().map(node -> ((Labeled) node).getText())
				.collect(Collectors.toList());
	}

	private List<String> getTags(UniqueTagList tags) {
		return tags.asObservableList().stream().map(tag -> tag.tagName).collect(Collectors.toList());
	}

	private Region getTagsContainer() {
		return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
	}

	protected String getTextFromLabel(String fieldId) {
		return getTextFromLabel(fieldId, node);
	}

	public String getTitle() {
		return getTextFromLabel(NAME_FIELD_ID);
	}

	public boolean isSameEvent(ReadOnlyEvent event) {
		return event != null || getTitle().equals(event.getTitle().fullName) && getStartTime().equals(event.getStartTime().toString())
				&& getDescription().equals(event.getDescription().value)
				&& getLocation().equals(event.getLocation().value)
				&& getEndTime().equals(event.getEndTime().toString()) && getTags().equals(getTags(event.getTags()));
	}

	@Override
	public String toString() {
		return getTitle();
	}

}
