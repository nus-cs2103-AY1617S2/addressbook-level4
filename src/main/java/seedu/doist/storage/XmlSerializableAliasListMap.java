package seedu.doist.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.doist.model.ReadOnlyAliasListMap;

/**
 * An Immutable AliasListMap that is serializable to XML format
 */
@XmlRootElement(name = "aliaslistMap")
public class XmlSerializableAliasListMap implements ReadOnlyAliasListMap {

    @XmlElementWrapper(name = "aliasListMapping")
    Map<String, ListWrapper> stringToStringArrMap;

    /**
     * Wrapper for internal ArrayList of aliasList
     */
    class ListWrapper {

        @XmlElementWrapper(name = "aliasList")
        private List<String> list;

        public void setList(List<String> list) {
            this.list = list;
        }

        public List<String> getList() {
            return list;
        }
    }

    /**
     * Creates an empty XmlSerializableAliasListMap.
     * This empty constructor is required for marshalling to XML format.
     */
    public XmlSerializableAliasListMap() {
        stringToStringArrMap = new HashMap<String, ListWrapper>();
    }

    /**
     * Conversion from model to storage
     */
    public XmlSerializableAliasListMap(ReadOnlyAliasListMap src) {
        this();
        Map<String, ArrayList<String>> map = src.getAliasListMapping();
        for (Map.Entry<String, ArrayList<String>> entry : map.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            ListWrapper wrapper = new ListWrapper();
            wrapper.setList(entry.getValue());
            stringToStringArrMap.put(entry.getKey(), wrapper);
        }
    }

    /**
     * Conversion from storage to model
     */
    @Override
    public Map<String, ArrayList<String>> getAliasListMapping() {
        Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

        for (Map.Entry<String, ListWrapper> entry : stringToStringArrMap.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            ArrayList<String> arrayList = new ArrayList<String>(entry.getValue().getList());
            map.put(entry.getKey(), arrayList);
        }
        return map;
    }
}
