package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.whatsleft.model.activity.ReadOnlyEvent;
import seedu.whatsleft.model.tag.UniqueTagList;

//@@author A0148038A
/**
 * Provides a handle to an event card in the event list panel.
 */
public class EventCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String DURATION_FIELD_ID = "#duration";
    private static final String LOCATION_FIELD_ID = "#locations";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getDuration() {
        return getTextFromLabel(DURATION_FIELD_ID);
    }

    public String getLocation() {
        return getTextFromLabel(LOCATION_FIELD_ID);
    }

    public List<String> getTags() {
        return getTags(getTagsContainer());
    }

    private List<String> getTags(Region tagsContainer) {
        return tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getTags(UniqueTagList tags) {
        return tags
                .asObservableList()
                .stream()
                .map(tag -> tag.tagName)
                .collect(Collectors.toList());
    }

    private Region getTagsContainer() {
        return guiRobot.from(node).lookup(TAGS_FIELD_ID).query();
    }

    public boolean isSameEvent(ReadOnlyEvent event) {
        return getDescription().equals(event.getDescription().description)
                && getDuration().equals(event.getStartTime().toString()
                        + " " + event.getStartDate().toString()
                        + " ~ " + event.getEndTime().toString()
                        + " " + event.getEndDate().toString())
                && getLocation().equals("@" + event.getLocation().value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getDescription().equals(handle.getDescription())
                    && getDuration().equals(handle.getDuration())
                    && getLocation().equals(handle.getLocation())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription() + " " + getDuration() + " " + getLocation();
    }
}
