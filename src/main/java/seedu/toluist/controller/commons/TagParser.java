//@@author Melvin
package seedu.toluist.controller.commons;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import edu.emory.mathcs.backport.java.util.Arrays;
import seedu.toluist.model.Tag;

/**
 * Parse string of tags into a set of tags.
 */
public class TagParser {
    private static final String TAGS_SEPARATOR_REGEX = " ";

    public static Set<Tag> parseTags(String tagsString) {
        String[] tagStrings = tagsString == null ? new String[] {} : tagsString.split(TAGS_SEPARATOR_REGEX);
        List<String> tagList = Arrays.asList(tagStrings);
        Set<Tag> tags = (Set<Tag>) tagList
                .stream()
                .filter(tagString -> !tagString.isEmpty())
                .map(tagString -> new Tag((String) tagString))
                .collect(Collectors.toSet());
        return tags;
    }
}
