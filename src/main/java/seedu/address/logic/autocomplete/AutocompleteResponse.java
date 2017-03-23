package seedu.address.logic.autocomplete;

import java.util.List;

//@@author A0140042A
/**
 * A response class that extends request class to contain suggestions and the replaced phrase
 */
public class AutocompleteResponse extends AutocompleteRequest {
    private List<String> suggestions;

    /**
     * Initializes a response with phrase, caretPosition and a list of suggestions
     */
    public AutocompleteResponse(String phrase, int caretPosition, List<String> suggestions) {
        super(phrase, caretPosition);
        setSuggestions(suggestions);
    }

    /**
     * Initializes a response from the {@link AutocompleteRequest}
     */
    public AutocompleteResponse(AutocompleteRequest request, List<String> suggestions) {
        this(request.getPhrase(), request.getCaretPosition(), suggestions);
    }

    /**
     * Returns the suggestions
     */
    public List<String> getSuggestions() {
        return suggestions;
    }

    /**
     * Sets the suggestions list
     */
    public void setSuggestions(List<String> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AutocompleteResponse) {
            AutocompleteResponse otherResponse = (AutocompleteResponse) other;
            boolean sameCaret = this.caretPosition == otherResponse.caretPosition;
            boolean samePhrase = this.phrase.equals(otherResponse.phrase);
            boolean sameSuggestions = (suggestions == otherResponse.suggestions);
            if (this.suggestions != null && otherResponse.suggestions != null) {
                sameSuggestions = suggestions.containsAll(otherResponse.getSuggestions());
            }
            return sameCaret && samePhrase && sameSuggestions;
        } else {
            return false;
        }
    }
}
