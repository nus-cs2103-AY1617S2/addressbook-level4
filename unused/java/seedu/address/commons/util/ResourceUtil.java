package seedu.address.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import seedu.address.MainApp;

//@@author A0163848R
/**
 * A container for resource-specific utility functions
 */
public class ResourceUtil {
    public static List<String> getResourceFiles(String path) throws IOException {
        List<String> files = new ArrayList<>();

        InputStream in = MainApp.class.getResourceAsStream(path);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        String file;
        while ((file = br.readLine()) != null) {
            files.add(file);
        }

        return files;
    }
}

