package seedu.doist.storage.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HashMapElementAdapter extends XmlAdapter<HashMapElements, Map<String, List<String>>> {

    @Override
    public Map<String, List<String>> unmarshal(HashMapElements in) throws Exception {
        HashMap<String, List<String>> hashMap = new HashMap<>();
        for (HashMapElement entry : in.entries()) {
            hashMap.put(entry.key(), entry.value());
        }
        return hashMap;
    }

    @Override
    public HashMapElements marshal(Map<String, List<String>> map) throws Exception {
        HashMapElements props = new HashMapElements();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            props.addEntry(new HashMapElement(entry.getKey(), entry.getValue()));
        }
        return props;
    }

}
