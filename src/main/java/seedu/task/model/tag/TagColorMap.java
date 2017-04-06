package seedu.task.model.tag;

import java.util.HashMap;
import java.util.Random;
//@@author A0142939W
/**
 * Represents the static TagColorMap in the
 * taskmanager.
 */
public class TagColorMap {
    private static HashMap<String, String> tagColorMap;

    /*
     * Sets sets a random colour for a tag if the tag exists but
     * does not have an assigned colour yet
     */
    public static void setColor(String tag) {
        Random rand = new Random();
        int r = rand.nextInt(205);
        int g = rand.nextInt(205);
        int b = rand.nextInt(205);
        String hex = String.format("#%02x%02x%02x", r, g, b);
        tagColorMap.put(tag, hex);
    }

    /*
     * Returns the color of the tag, if the tag does not
     * have a colour, a random one is assigned
     */
    public static String getColor(String tag) {
        if (tagColorMap == null) {
            tagColorMap = new HashMap<String, String>();
        }

        if (!tagColorMap.containsKey(tag)) {
            setColor(tag);
        }

        return tagColorMap.get(tag);
    }
}
