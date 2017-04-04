package seedu.taskit.model.tag;

import java.util.HashMap;

//@@author A0141011J
public class TagColor {
    public static final String EMPTY_COLOR_VALUE = "";

    private static final HashMap<Integer, String> colorValues = new HashMap<Integer, String>() {{
        put(0, "#9AD0E5");
        put(1, "#C89EE8");
        put(2, "#F48687");
        put(3, "#FFB764");
        put(4, "#FBD75B");
        put(5, "#B1DE7A");
        put(6, "#ABCDEF");
        put(7, "#BCDEFA");
        put(8, "#CDEFAB");
    }};

    public static int numOfColors() {
        return colorValues.size();
    }

    public static String getColorCode(int colorIndex) {
        return colorValues.get(colorIndex);
    }
}
