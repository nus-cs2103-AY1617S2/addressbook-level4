package savvytodo.commons.util;

import java.util.Arrays;
//@@author A0140016B
/**
 * @author A0140016B
 * Utility methods related to Enum
 */
public class EnumUtil {
    /**
     * for seed properties
     * @param e
     * @return String []
     */
    public static String[] getNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
}
//@@author A0140016B
