package seedu.doist.storage.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

//@@author A0140887W
/**
 * A class to serve as an XML adapter to convert between Java HashMap to
 * XML Serializable Key Values
 * @see XMLSerializableKeysValues
 */
public class XMLHashMapAdapter extends XmlAdapter<XMLSerializableKeysValues,
                                                            Map<String, List<String>>> {

    /**
     * Converts from XML to Map (HashMap)
     */
    @Override
    public Map<String, List<String>> unmarshal(XMLSerializableKeysValues in) throws Exception {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (XMLSerializableKeyValueElement entry : in.entries()) {
            hashMap.put(entry.key(), entry.value());
        }
        return hashMap;
    }

    /**
     * Converts from Map (HashMap) to XML
     */
    @Override
    public XMLSerializableKeysValues marshal(Map<String, List<String>> map) throws Exception {
        XMLSerializableKeysValues props = new XMLSerializableKeysValues();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            props.addElement(new XMLSerializableKeyValueElement(entry.getKey(), entry.getValue()));
        }
        return props;
    }

}
