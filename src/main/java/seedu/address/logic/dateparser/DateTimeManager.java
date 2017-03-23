package seedu.address.logic.dateparser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.Token;
import org.antlr.runtime.TokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;
import org.antlr.runtime.tree.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.joestelmach.natty.ANTLRNoCaseInputStream;
import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.NattyTokenSource;
import com.joestelmach.natty.ParseListener;
import com.joestelmach.natty.ParseLocation;
import com.joestelmach.natty.generated.DateLexer;
import com.joestelmach.natty.generated.DateParser;
import com.joestelmach.natty.generated.DateWalker;
import com.joestelmach.natty.generated.TreeRewrite;

import seedu.address.logic.parser.Parser;

//@@author A0162877N
/**
* Parses input arguments to Natty
*/
public class DateTimeManager implements DateTimeParser {
    private TimeZone defaultTimeZone;
    private static final Logger logger = LoggerFactory.getLogger(Parser.class);

    /**
     * Tokens that should be removed from the end any list of tokens before
     * parsing. These are valid tokens, but could never add any meaningful
     * parsing information when located at the end of a token stream.
     */
    private static final Set<Integer> IGNORED_TRAILING_TOKENS = new HashSet<Integer>(
            Arrays.asList(new Integer[] { DateLexer.DOT, DateLexer.COLON, DateLexer.COMMA, DateLexer.DASH,
                DateLexer.SLASH, DateLexer.DOT, DateLexer.PLUS, DateLexer.SINGLE_QUOTE }));

    /**
     * Creates a new parser with default time zone set as GMT+8
     */
    public DateTimeManager() {
        defaultTimeZone = TimeZone.getTimeZone("GMT+8");
    }

    /**
     * Parses the given input value for one or more groups of date alternatives
     *
     * @param value
     * @return List of Date group
     */
    public List<DateGroup> parse(String value) {
        return parse(value, new Date());
    }

    /**
     * Parses the given input value for one or more groups of date alternatives
     * with relative dates resolved according to referenceDate
     *
     * @param value
     * @param referenceDate
     * @return
     */
    public List<DateGroup> parse(String value, Date referenceDate) {
        // lex the input value to obtain our global token stream
        ANTLRInputStream input = null;

        try {
            input = new ANTLRNoCaseInputStream(new ByteArrayInputStream(value.getBytes()));

        } catch (IOException e) {
            logger.error("could not lex input", e);
        }
        DateLexer lexer = new DateLexer(input);

        // collect all sub-token streams that may include date information
        List<TokenStream> streams = collectTokenStreams(new CommonTokenStream(lexer));

        // and parse each of them
        List<DateGroup> groups = new ArrayList<DateGroup>();
        TokenStream lastStream = null;
        for (TokenStream stream : streams) {
            lastStream = stream;
            List<Token> tokens = ((NattyTokenSource) stream.getTokenSource()).getTokens();
            DateGroup group = singleParse(stream, value, referenceDate);
            while ((group == null || group.getDates().size() == 0) && tokens.size() > 0) {
                if (group == null || group.getDates().size() == 0) {
                    List<Token> endRemovedTokens = new ArrayList<Token>(tokens);
                    while ((group == null || group.getDates().isEmpty()) && !endRemovedTokens.isEmpty()) {
                        endRemovedTokens = endRemovedTokens.subList(0, endRemovedTokens.size() - 1);
                        TokenStream newStream = new CommonTokenStream(new NattyTokenSource(endRemovedTokens));
                        group = singleParse(newStream, value, referenceDate);
                        lastStream = newStream;
                    }

                    while ((group == null || group.getDates().isEmpty()) && tokens.size() >= 1) {
                        tokens = tokens.subList(1, tokens.size());
                        Iterator<Token> iter = tokens.iterator();
                        while (iter.hasNext()) {
                            Token token = iter.next();
                            if (!DateParser.FOLLOW_empty_in_parse186.member(token.getType())) {
                                iter.remove();
                            } else {
                                break;
                            }
                        }
                        TokenStream newStream = new CommonTokenStream(new NattyTokenSource(tokens));
                        group = singleParse(newStream, value, referenceDate);
                        lastStream = newStream;
                    }
                }
            }

            // If a group with at least one date was found, we'll most likely
            // want to add it to our list,
            // but not if multiple streams were found and the group contains
            // only numeric time information.
            // For example: A full text string of '1' should parse to 1 o'clock,
            // but 'I need 1 hard drive'
            // should result in no groups found.
            if (group != null && !group.getDates().isEmpty()
                    && (streams.size() == 1 || !group.isDateInferred() || !isAllNumeric(lastStream))) {

                // Additionally, we'll only accept this group if the associated
                // text does not have an
                // alphabetic character to the immediate left or right, which
                // would indicate a portion
                // of a word was tokenized. For example, 'nightingale' will
                // result in a 'NIGHT' token,
                // but there's clearly no datetime information there.
                group.setFullText(value);
                String prefix = group.getPrefix(1);
                String suffix = group.getSuffix(1);
                if ((prefix.isEmpty() || !Character.isLetter(prefix.charAt(0)))
                        && (suffix.isEmpty() || !Character.isLetter(suffix.charAt(0)))) {

                    groups.add(group);
                }
            }
        }
        return groups;
    }

    /**
     * Determines if a token stream contains only numeric tokens
     *
     * @param stream
     * @return true if all tokens in the given stream can be parsed as an
     *         integer
     */
    private boolean isAllNumeric(TokenStream stream) {
        List<Token> tokens = ((NattyTokenSource) stream.getTokenSource()).getTokens();
        for (Token token : tokens) {
            try {
                Integer.parseInt(token.getText());
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return true;
    }

    /**
     * Parses the token stream for a SINGLE date time alternative. This method
     * assumes that the entire token stream represents date and or time
     * information (no extraneous tokens)
     *
     * @param stream
     * @return
     */
    private DateGroup singleParse(TokenStream stream, String fullText, Date referenceDate) {
        DateGroup group = null;
        List<Token> tokens = ((NattyTokenSource) stream.getTokenSource()).getTokens();
        if (tokens.isEmpty()) {
            return group;
        }

        StringBuilder tokenString = new StringBuilder();
        for (Token token : tokens) {
            tokenString.append(DateParser.tokenNames[token.getType()]);
            tokenString.append(" ");
        }

        try {
            // parse
            ParseListener listener = new ParseListener();
            DateParser parser = new DateParser(stream, listener);
            DateParser.parse_return parseReturn = parser.parse();

            Tree tree = (Tree) parseReturn.getTree();

            // continue if a meaningful syntax tree has been built
            if (tree.getChildCount() > 0) {
                logger.info("PARSE: " + tokenString.toString());

                // rewrite the tree
                CommonTreeNodeStream nodes = new CommonTreeNodeStream(tree);
                TreeRewrite s = new TreeRewrite(nodes);
                tree = (CommonTree) s.downup(tree);
                nodes = new CommonTreeNodeStream(tree);
                nodes.setTokenStream(stream);
                DateWalker walker = new DateWalker(nodes);
                walker.setReferenceDate(referenceDate);
                walker.getState().setDefaultTimeZone(defaultTimeZone);
                walker.parse();
                logger.info("AST: " + tree.toStringTree());

                // run through the results and append the parse information
                group = walker.getState().getDateGroup();
                ParseLocation location = listener.getDateGroupLocation();
                group.setLine(location.getLine());
                group.setText(location.getText());
                group.setPosition(location.getStart());
                group.setSyntaxTree(tree);
                group.setParseLocations(listener.getLocations());
                group.setFullText(fullText);

                String prefix = group.getPrefix(1);
                String suffix = group.getSuffix(1);
                // ignore this result if the group's matching text has an immediate alphabetic
                // prefix or suffix
                if ((!prefix.isEmpty() && Character.isLetter(prefix.charAt(0)))
                        || (!suffix.isEmpty() && Character.isLetter(suffix.charAt(0)))) {

                    group = null;
                }

            }

        } catch (RecognitionException e) {
            logger.debug("Could not parse input", e);
        }

        return group;
    }

    /**
     * Scans the given token global token stream for a list of sub-token streams
     * representing those portions of the global stream that may contain date
     * time information
     *
     * @param stream
     * @return list of token stream
     */
    private List<TokenStream> collectTokenStreams(TokenStream stream) {

        // walk through the token stream and build a collection
        // of sub token streams that represent possible date locations
        List<Token> currentGroup = null;
        List<List<Token>> groups = new ArrayList<List<Token>>();
        Token currentToken;
        int currentTokenType;
        StringBuilder tokenString = new StringBuilder();
        while ((currentToken = stream.getTokenSource().nextToken()).getType() != DateLexer.EOF) {
            currentTokenType = currentToken.getType();
            tokenString.append(DateParser.tokenNames[currentTokenType]).append(" ");

            if (currentGroup == null) {
                // skip over white space and known tokens that cannot be the start of a date
                if (currentTokenType != DateLexer.WHITE_SPACE
                        && DateParser.FOLLOW_empty_in_parse186.member(currentTokenType)) {

                    currentGroup = new ArrayList<Token>();
                    currentGroup.add(currentToken);
                }
            } else { // collect and preserve white space
                if (currentTokenType == DateLexer.WHITE_SPACE) {
                    currentGroup.add(currentToken);
                } else {
                    // if this is an unknown token, close out the current group
                    if (currentTokenType == DateLexer.UNKNOWN) {
                        addGroup(currentGroup, groups);
                        currentGroup = null;
                    } else {
                        // token is known and add it to the current group
                        currentGroup.add(currentToken);
                    }
                }
            }
        }

        if (currentGroup != null) {
            addGroup(currentGroup, groups);
        }

        // add information to logger
        logger.info("STREAM: " + tokenString.toString());
        List<TokenStream> streams = new ArrayList<TokenStream>();
        for (List<Token> group : groups) {
            if (!group.isEmpty()) {
                StringBuilder builder = new StringBuilder();
                builder.append("GROUP: ");
                for (Token token : group) {
                    builder.append(DateParser.tokenNames[token.getType()]).append(" ");
                }
                logger.info(builder.toString());

                streams.add(new CommonTokenStream(new NattyTokenSource(group)));
            }
        }

        return streams;
    }

    /**
     * Cleans up the given group and adds it to the list of groups if still
     * valid
     *
     * @param group
     * @param groups
     */
    private void addGroup(List<Token> group, List<List<Token>> groups) {

        if (group.isEmpty()) {
            return;
        }

        // remove trailing tokens that should be ignored
        while (!group.isEmpty() && IGNORED_TRAILING_TOKENS.contains(group.get(group.size() - 1).getType())) {
            group.remove(group.size() - 1);
        }

        // if the group still has some tokens left, add it to our list of groups
        if (!group.isEmpty()) {
            groups.add(group);
        }
    }
}
