package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.doit.model.item.ReadOnlyFloatingTask;
import seedu.doit.model.tag.UniqueTagList;

/**
 * Provides a handle to a task card in the task list panel.
 */
public class FloatingTaskCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
   // private static final String INDEX_FIELD_ID = "#idx";
   // private static final String TIME_FIELD_ID = "#time";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    //private static final String PRIORITY_FIELD_ID = "#priority";
    private static final String TAGS_FIELD_ID = "#tags";
    //private static final String EMPTY_PREFIX = "";
    //private static final String COMPLETED_PREFIX = "Completed on ";
   // private static final String START_TIME_PREFIX = "from ";
    //private static final String END_TIME_PREFIX = " to ";
    //private static final String DEADLINE_PREFIX = "by ";

    private Node node;

    public FloatingTaskCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
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

    //change this after logic change type
    public boolean isSameFloatingTask(ReadOnlyFloatingTask floatingTask) {
        return getFullName().equals(floatingTask.getName().fullName)
           // && getPriority().equals(task.getPriority().value)
           //change this ""  after all three types becomes 1 and use Task Cards for all 3 types
           // && getDeadline().equals(floatingTask.getEndTime().value)
            && getDescription().equals(floatingTask.getDescription().value)
            && getTags().equals(getTags(floatingTask.getTags()));
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FloatingTaskCardHandle) {
            FloatingTaskCardHandle handle = (FloatingTaskCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                //&& getPriority().equals(handle.getPriority())
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
