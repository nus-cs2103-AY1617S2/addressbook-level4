package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import project.taskcrusher.commons.util.UiDisplayUtil;
import project.taskcrusher.model.event.ReadOnlyEvent;
import project.taskcrusher.model.tag.UniqueTagList;

public class EventCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String TAGS_FIELD_ID = "#tags";
    private static final String TIMESLOT_FIELD_ID = "#timeslots";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String LOCATION_FIELD_ID = "#eventLocation";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getEventName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

//    public String getTimeslots() {
//        return getTextFromLabel(TIMESLOT_FIELD_ID);
//    }

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

    //@@author A0127737X
    public boolean isSameEvent(ReadOnlyEvent event) {
        String uiAdjustedPriority = UiDisplayUtil.priorityForUi(event.getPriority());
        String uiAdjustedLocation = UiDisplayUtil.locationForUi(event.getLocation());

        return getEventName().equals(event.getName().name)
                && getPriority().equals(uiAdjustedPriority)
                && getLocation().equals(uiAdjustedLocation)
                && getDescription().equals(event.getDescription().description)
                && getTags().equals(getTags(event.getTags()));
    }

    //@@author
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getEventName().equals(handle.getEventName())
                    && getPriority().equals(handle.getPriority())
                    && getLocation().equals(handle.getLocation())
                    && getDescription().equals(handle.getDescription())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getEventName() + " " + getDescription();
    }
}
