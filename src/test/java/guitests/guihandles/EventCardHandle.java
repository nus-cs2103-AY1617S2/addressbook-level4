package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doit.model.item.ReadOnlyEvent;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class EventCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
   // private static final String INDEX_FIELD_ID = "#idx";
   // private static final String TIME_FIELD_ID = "#time";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    //private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String DEADLINE_FIELD_ID = "#";
    private static final String TAGS_FIELD_ID = "#tags";
    //private static final String EMPTY_PREFIX = "";
    //private static final String COMPLETED_PREFIX = "Completed on ";
   // private static final String START_TIME_PREFIX = "from ";
    //private static final String END_TIME_PREFIX = " to ";
    //private static final String DEADLINE_PREFIX = "by ";

    private Node node;

    public EventCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, this.node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }

   /* public String getTaskIndex() {
        return getTextFromLabel(INDEX_FIELD_ID);
    }
*/
    /*public String getTime() {
        return getTextFromLabel(TIME_FIELD_ID);
    }*/

    public String getDescription() {
        return getTextFromLabel(DESCRIPTION_FIELD_ID);
    }

    /*public String getPriority() {
        return getTextFromLabel(PRIORITY_FIELD_ID);
    }*/

    public String getDeadline() {
        return getTextFromLabel(DEADLINE_FIELD_ID);
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
        return this.guiRobot.from(this.node).lookup(TAGS_FIELD_ID).query();
    }



    public boolean isSameEvent(ReadOnlyEvent event) {
        return getFullName().equals(event.getName().fullName)
           // && getPriority().equals(task.getPriority().value)
            && getDeadline().equals(event.getEndTime().value)
            && getDescription().equals(event.getDescription().value)
            && getTags().equals(getTags(event.getTags()));
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof EventCardHandle) {
            EventCardHandle handle = (EventCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                //&& getPriority().equals(handle.getPriority())
                && getDeadline().equals(handle.getDeadline())
                && getDescription().equals(handle.getDescription())
                && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getDescription();
    }
}
