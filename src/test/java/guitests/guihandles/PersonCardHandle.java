package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String TITLE_FIELD_ID = "#title";
    private static final String CONTENT_FIELD_ID = "#content";
    private static final String DATE_TIME_FIELD_ID = "#dateTime";
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
        return getTextFromLabel(TITLE_FIELD_ID);
    }

    public String getContent() {
        return getTextFromLabel(CONTENT_FIELD_ID);
    }

    public String getDateTime() {
        return getTextFromLabel(DATE_TIME_FIELD_ID);
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
        return getTitle().equals(person.getTitle().fullTitle)
                && getTags().equals(getTags(person.getTags()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getTitle().equals(handle.getTitle())
                    && getTags().equals(handle.getTags());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getTitle() + " " + getContent();
    }
}
