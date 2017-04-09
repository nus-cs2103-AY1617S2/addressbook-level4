package seedu.whatsleft.storage;

import java.time.LocalTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;

//@@author A0121668A
/**
 * Adapter used for marshaling LocalTime using JAXB
 */
public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    public LocalTime unmarshal(String v) throws Exception {
        return LocalTime.parse(v);
    }

    public String marshal(LocalTime v) throws Exception {
        return v.toString();
    }
}
