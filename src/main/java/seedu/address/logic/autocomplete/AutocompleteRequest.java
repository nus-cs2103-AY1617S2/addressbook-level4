package seedu.address.logic.autocomplete;

//@@author A0140042A
/**
 * A request class to request for an auto complete at the cursor position
 */
public class AutocompleteRequest {
    protected String phrase;
    protected int caretPosition; //The caret always represents the character on the left of it

    /**
     * Initializes the request with the phrase and the caretPosition
     */
    public AutocompleteRequest(String phrase, int caretPosition) {
        setPhrase(phrase);
        setCaretPosition(caretPosition);
    }

    /**
     * Returns the phrase
     */
    public String getPhrase() {
        return phrase;
    }

    /**
     * Sets the phrase
     */
    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    /**
     * Sets the caretPosition
     */
    public int getCaretPosition() {
        return caretPosition;
    }

    /**
     * Sets the caretPosition if valid (between 0 to phrase.length - 1)
     */
    public void setCaretPosition(int caretPosition) {
        assert caretPosition >= 0;
        this.caretPosition = caretPosition;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof AutocompleteRequest) {
            AutocompleteRequest otherRequest = (AutocompleteRequest) other;
            return (this.caretPosition == otherRequest.caretPosition) &&
                   (this.phrase.equals(otherRequest.phrase));
        } else {
            return false;
        }
    }
}
