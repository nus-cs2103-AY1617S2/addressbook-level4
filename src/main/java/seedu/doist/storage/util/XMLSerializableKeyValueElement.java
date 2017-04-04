package seedu.doist.storage.util;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

//@@author A0140887W
/**
 * An Immutable class with key and value that is serializable to XML format
 */
public class XMLSerializableKeyValueElement {

    @XmlElement(name = "key")
    private String key;

    @XmlElement(name = "value")
    private List<String> value;

    XMLSerializableKeyValueElement() {
    }

    XMLSerializableKeyValueElement(String name, List<String> value) {
        this.key = name;
        this.value = value;
    }

    public List<String> value() {
        return value;
    }

    public String key() {
        return key;
    }
}
