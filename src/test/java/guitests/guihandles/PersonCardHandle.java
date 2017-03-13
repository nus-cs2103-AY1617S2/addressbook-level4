package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import org.teamstbf.yats.model.item.Description;
import org.teamstbf.yats.model.item.Location;
import org.teamstbf.yats.model.item.Periodic;
import org.teamstbf.yats.model.item.ReadOnlyEvent;
import org.teamstbf.yats.model.item.ReadOnlyItem;
import org.teamstbf.yats.model.item.Schedule;
import org.teamstbf.yats.model.item.Title;
import org.teamstbf.yats.model.tag.UniqueTagList;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String PERIOD_FIELD_ID = "#period";
    private static final String STARTTIME_FIELD_ID = "#startTime";
    private static final String ENDTIME_FIELD_ID = "#endTime";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String LOCATION_FIELD_ID = "#location";
    private static final String TAGS_FIELD_ID = "#tags";
    
    
    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getTitle() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

    public String getPeriod() {
        return getTextFromLabel(PERIOD_FIELD_ID);
    }

    public String getStartTime() {
        return getTextFromLabel(STARTTIME_FIELD_ID);
    }

    public String getEndTime() {
        return getTextFromLabel(ENDTIME_FIELD_ID);
    }
    
    public String getLocation() {
        return getTextFromLabel(LOCATION_FIELD_ID);
    }
    
    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
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

    public boolean isSamePerson(ReadOnlyEvent person) {
        return getTitle().equals(person.getTitle().fullName)
                && getStartTime().equals(person.getStartTime().value)
                && getPeriod().equals(person.getPeriod().value)
                && getEndTime().equals(person.getEndTime().value)
                && getDescription().equals(person.getDescription().value)
                && getLocation().equals(person.getLocation().value)
                && getEndTime().equals(person.getEndTime().value)
                && getTags().equals(getTags(person.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getTitle().equals(handle.getTitle())
                    && getPeriod().equals(handle.getPeriod())
                    && getStartTime().equals(handle.getStartTime())
                    && getEndTime().equals(handle.getEndTime())
                    && getDescription().equals(handle.getDescription())
                    && getLocation().equals(handle.getLocation())
                    && getEndTime().equals(handle.getEndTime())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }



    @Override
    public String toString() {
        return getTitle();
    }
    
}
