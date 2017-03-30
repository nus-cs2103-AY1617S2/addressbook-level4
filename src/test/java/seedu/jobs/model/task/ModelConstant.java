package seedu.jobs.model.task;


public class ModelConstant {

    public static String getLongString(int length) {
        String result = "";
        for (int i = 0; i < length; i++) {
            result += Integer.toString(i);
        }
        return result;
    }

}
