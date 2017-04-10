package seedu.jobs.model.task;

//@@author A0130979U
public class ModelConstant {

    public static String getLongString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += Integer.toString(i);
        }
        return result;
    }

}
//@@author A0130979U
