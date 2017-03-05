package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import guitests.GuiRobot;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.model.label.UniqueLabelList;
import seedu.address.model.person.ReadOnlyTask;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonCardHandle extends GuiHandle {
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String LABELS_FIELD_ID = "#labels";

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

    public String getAddress() {
        return getTextFromLabel(ADDRESS_FIELD_ID);
    }

    public String getPhone() {
        return getTextFromLabel(PHONE_FIELD_ID);
    }

    public String getEmail() {
        return getTextFromLabel(EMAIL_FIELD_ID);
    }

    public List<String> getLabels() {
        return getLabels(getLabelsContainer());
    }

    private List<String> getLabels(Region labelsContainer) {
        return labelsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(node -> ((Labeled) node).getText())
                .collect(Collectors.toList());
    }

    private List<String> getLabels(UniqueLabelList labels) {
        return labels
                .asObservableList()
                .stream()
                .map(label -> label.labelName)
                .collect(Collectors.toList());
    }

    private Region getLabelsContainer() {
        return guiRobot.from(node).lookup(LABELS_FIELD_ID).query();
    }

    public boolean isSamePerson(ReadOnlyTask person) {
        return getFullName().equals(person.getTitle().title)
                && getAddress().equals(person.getDeadline().value)
                && getLabels().equals(getLabels(person.getLabels()));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonCardHandle) {
            PersonCardHandle handle = (PersonCardHandle) obj;
            return getFullName().equals(handle.getFullName())
                    && getPhone().equals(handle.getPhone())
                    && getEmail().equals(handle.getEmail())
                    && getAddress().equals(handle.getAddress())
                    && getLabels().equals(handle.getLabels());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return getFullName() + " " + getAddress();
    }
}
