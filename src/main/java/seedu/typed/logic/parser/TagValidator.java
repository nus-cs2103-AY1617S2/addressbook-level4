package seedu.typed.logic.parser;

import java.util.Optional;

/**
 * Validates format of parsed tag.
 * @author Peixuan
 *
 */

public class TagValidator extends ArgumentValidator {

    public TagValidator(Optional<String> tag) {
        this.validationRegex = "\\p{Alnum}+";
        this.messageConstraints = "Tag names should be alphanumeric.";
        this.arg = tag;
    }

}


