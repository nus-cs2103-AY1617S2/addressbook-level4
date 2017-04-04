//@@author A0131125Y
package seedu.toluist.dispatcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * TokenHistoryList records the list of tokens and values used in the current session
 * Sort like a cookie
 */
public class TokenHistoryList {
    public HashMap<String, HashMap<String, Set<String>>> tokenHistory = new HashMap<>();

    /**
     * Record token map for a particular controller
     * @param controllerClass a controller class
     * @param tokens dictionary of tokens
     */
    public void recordTokens(Class controllerClass, Map<String, String> tokens) {
        if (tokens == null) {
            return;
        }

        String controllerKey = controllerClass.getName();
        if (tokenHistory.get(controllerKey) == null) {
            tokenHistory.put(controllerKey, new HashMap());
        }
        HashMap<String, Set<String>> tokenHistoryForController = tokenHistory.get(controllerKey);
        for (String token : tokens.keySet()) {
            if (tokenHistoryForController.get(token) == null) {
                tokenHistoryForController.put(token, new HashSet());
            }
            tokenHistoryForController.get(token).add(tokens.get(token));
        }
    }

    /**
     * Retrieve past values for a token in the current session for a particular controller
     * @param controllerClass a controller class
     * @param tokenName token value
     * @return
     */
    public Set<String> retrieveTokens(Class controllerClass, String tokenName) {
        String controllerKey = controllerClass.getName();
        if (tokenHistory.get(controllerKey) == null
            || tokenHistory.get(controllerKey).get(tokenName) == null) {
            return new HashSet<>();
        }

        return tokenHistory.get(controllerKey).get(tokenName);
    }
}
