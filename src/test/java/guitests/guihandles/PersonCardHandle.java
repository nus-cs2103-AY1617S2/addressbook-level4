package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.task.model.tag.UniqueTagList;
import seedu.task.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String GROUP_FIELD_ID = "#group";
    private static final String STARTDATE_FIELD_ID = "#start";
    private static final String ENDDATE_FIELD_ID = "#end";
    private static final String TAGS_FIELD_ID = "#tagged";

    private Node node;

    public PersonCardHandle(GuiRobot guiRobot, Stage primaryStage, Node node) {
        super(guiRobot, primaryStage, null);
        this.node = node;
    }

    protected String getTextFromLabel(String fieldId) {
        return getTextFromLabel(fieldId, node);
    }

    public String getFullName() {
        return getTextFromLabel(NAME_FIELD_ID);
    }
    //@@author A0164889E
    public String getGroup() {
        return getTextFromLabel(GROUP_FIELD_ID);
    }

    //@@author A0164889E
    public String getStartDate() {
        String startDateField = getTextFromLabel(STARTDATE_FIELD_ID);
        String[] parts = startDateField.split(" +");
        String start = parts[0]; //Starts:
        String date = parts[1]; //01.01
        return date;
     }

    //@@author A0164889E
    public String getEndDate() {
        String endDateField = getTextFromLabel(ENDDATE_FIELD_ID);
        String[] parts = endDateField.split(" +");
        String end = parts[0]; //ends:
        String date = parts[1]; //01.01
        return date;
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

    public boolean isSamePerson(ReadOnlyTask person) {
        return getFullName().equals(person.getName().fullName)
                && getStartDate().equals(person.getStartDate().getInputValue())
                && getEndDate().equals(person.getEndDate().getInputValue())
                && getGroup().equals(person.getGroup().value);
                //&& getTags().equals(getTags(person.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getStartDate().equals(handle.getStartDate())
                    && getEndDate().equals(handle.getEndDate())
                    && getGroup().equals(handle.getGroup());
                    //&& getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getGroup();
    }
}
