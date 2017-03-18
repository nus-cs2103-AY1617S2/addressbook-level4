package seedu.doist.storage.util;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class HashMapElement {

    @XmlElement(name = "key")
    private String key;

    @XmlElement(name = "value")
    private List<String> value;

    HashMapElement() {
    }

    HashMapElement(String name, List<String> value) {
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
