//@@author A0131125Y
package seedu.toluist.dispatcher;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import seedu.toluist.controller.Controller;

/**
 * TokenHistoryList records the list of tokens and values used in the current session
 * Sort like a cookie
 */
public class TokenHistoryList {
    public HashMap<String, HashMap<String, Set<String>>> tokenHistory = new HashMap<>();

    public void recordTokens(Controller controller, Map<String, String> tokens) {
        if (tokens == null) {
            return;
        }

        String controllerKey = controller.getClass().getName();
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

    public Set<String> retrieveTokens(Controller controller, String tokenName) {
        String controllerKey = controller.getClass().getName();
        if (tokenHistory.get(controllerKey) == null
            || tokenHistory.get(controllerKey).get(tokenName) == null) {
            return new HashSet<>();
        }

        return tokenHistory.get(controllerKey).get(tokenName);
    }
}
