package seedu.doist.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import seedu.doist.model.ReadOnlyAliasListMap;
import seedu.doist.storage.util.XMLHashMapAdapter;

//@@author A0140887W
/**
 * An Immutable AliasListMap that is serializable to XML format
 */
@XmlRootElement(name = "aliaslistMap")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlSerializableAliasListMap implements ReadOnlyAliasListMap {

    @XmlElement(name = "aliasListMap")
    @XmlJavaTypeAdapter(XMLHashMapAdapter.class)
    Map<String, List<String>> aliasListMap;

    /**
     * Creates an empty XmlSerializableAliasListMap.
     * This empty constructor is required for marshalling to XML format.
     */
    public XmlSerializableAliasListMap() {
        aliasListMap = new HashMap<String, List<String>>();
    }

    /**
     * Conversion from model to storage
     */
    public XmlSerializableAliasListMap(ReadOnlyAliasListMap src) {
        this();
        Map<String, ArrayList<String>> map = src.getAliasListMapping();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            List<String> list = entry.getValue();
            aliasListMap.put(entry.getKey(), list);
        }
    }

    /**
     * Conversion from storage to model
     */
    @Override
    public Map<String, ArrayList<String>> getAliasListMapping() {
        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        for (Map.Entry<String, List<String>> entry : aliasListMap.entrySet()) {
            ArrayList<String> list = null;
            if (entry.getValue() != null) {
                list = new ArrayList<String>(entry.getValue());
            } else {
                list = new ArrayList<String>();
            }
            map.put(entry.getKey(), list);
        }
        return map;
    }
}
