package seedu.doist.storage.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

//@@author A0140887W
/**
 * A class to contain a list of XML Serializable Key Value Element
 * @see XMLSerializableKeyValueElement
 */
public class XMLSerializableKeysValues {

    @XmlElement(name = "elements")
    private List<XMLSerializableKeyValueElement> entries = new ArrayList<>();

    List<XMLSerializableKeyValueElement> entries() {
        return Collections.unmodifiableList(entries);
    }

    void addElement(XMLSerializableKeyValueElement element) {
        entries.add(element);
    }
}
