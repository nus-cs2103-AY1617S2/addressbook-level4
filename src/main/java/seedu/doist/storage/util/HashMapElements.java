package seedu.doist.storage.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class HashMapElements {

    @XmlElement(name = "elements")
    private List<HashMapElement> entries = new ArrayList<>();

    List<HashMapElement> entries() {
        return Collections.unmodifiableList(entries);
    }

    void addEntry(HashMapElement entry) {
        entries.add(entry);
    }
}
