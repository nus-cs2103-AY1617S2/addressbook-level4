//@@author A0127545A
package seedu.toluist.controller.commons;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.toluist.commons.util.StringUtil;
import seedu.toluist.model.Tag;

/**
 * Parse string of tags into a set of tags.
 */
public class TagParser {
    private static final String SEPARATOR_REGEX_TAGS = StringUtil.SINGLE_SPACE;

    public static Set<Tag> parseTags(String tagsString) {
        String[] tagStrings = tagsString == null ? new String[] {} : tagsString.split(SEPARATOR_REGEX_TAGS);
        List<String> tagList = Arrays.asList(tagStrings);
        Set<Tag> tags = tagList
                .stream()
                .filter(tagString -> !tagString.isEmpty())
                .map(tagString -> new Tag(tagString))
                .collect(Collectors.toSet());
        return tags;
    }
}
