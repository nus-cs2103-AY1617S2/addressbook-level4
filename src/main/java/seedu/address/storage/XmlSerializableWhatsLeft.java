package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.UnmodifiableObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyWhatsLeft;
import seedu.address.model.person.Activity;
import seedu.address.model.person.ReadOnlyActivity;
import seedu.address.model.tag.Tag;

/**
 * An Immutable WhatsLeft that is serializable to XML format
 */
@XmlRootElement(name = "whatsleft")
public class XmlSerializableWhatsLeft implements ReadOnlyWhatsLeft {

    @XmlElement
    private List<XmlAdaptedActivity> activities;
    @XmlElement
    private List<XmlAdaptedTag> tags;

    /**
     * Creates an empty XmlSerializableWhatsLeft.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableWhatsLeft() {
        activities = new ArrayList<>();
        tags = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableWhatsLeft(ReadOnlyWhatsLeft src) {
        this();
        activities.addAll(src.getActivityList().stream().map(XmlAdaptedActivity::new).collect(Collectors.toList()));
        tags.addAll(src.getTagList().stream().map(XmlAdaptedTag::new).collect(Collectors.toList()));
    }

    @Override
    public ObservableList<ReadOnlyActivity> getActivityList() {
        final ObservableList<Activity> persons = this.activities.stream().map(p -> {
            try {
                return p.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(persons);
    }

    @Override
    public ObservableList<Tag> getTagList() {
        final ObservableList<Tag> tags = this.tags.stream().map(t -> {
            try {
                return t.toModelType();
            } catch (IllegalValueException e) {
                e.printStackTrace();
                //TODO: better error handling
                return null;
            }
        }).collect(Collectors.toCollection(FXCollections::observableArrayList));
        return new UnmodifiableObservableList<>(tags);
    }

}
