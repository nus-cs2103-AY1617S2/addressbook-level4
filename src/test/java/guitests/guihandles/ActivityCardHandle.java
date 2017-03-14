package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.UniqueTagList;

/**
 * Provides a handle to an activity card in the activity list panel.
 */
public class ActivityCardHandle extends GuiHandle {
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String LOCATION_FIELD_ID = "#locations";
    private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private Node node;

    public ActivityCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    public String getLocation() {
        return getTextFromLabel(LOCATION_FIELD_ID);
    }

    public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
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

    public boolean isSameActivity(ReadOnlyActivity activity) {
        return getDescription().equals(activity.getDescription().description)
                && getPriority().equals(activity.getPriority().value)
                && getEmail().equals(activity.getEmail().value)
                && getLocation().equals(activity.getLocation().value)
                && getTags().equals(getTags(activity.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ActivityCardHandle) {
            ActivityCardHandle handle = (ActivityCardHandle) obj;
            return getDescription().equals(handle.getDescription())
                    && getPriority().equals(handle.getPriority())
                    && getEmail().equals(handle.getEmail())
                    && getLocation().equals(handle.getLocation())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getDescription() + " " + getLocation();
    }
}
