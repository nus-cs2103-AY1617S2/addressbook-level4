package seedu.task.logic.parser;

import java.util.regex.Pattern;

import seedu.task.logic.parser.ArgumentTokenizer.Prefix;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

  /* Prefix definitions */
  public static final Prefix PREFIX_TAG = new Prefix("t/");
  public static final Prefix PREFIX_DATE = new Prefix("d/");
  public static final Prefix PREFIX_START_TIME = new Prefix("s/");
  public static final Prefix PREFIX_END_TIME = new Prefix("e/");
  public static final Prefix PREFIX_DESCRIPTION = new Prefix("m/");

  /* Patterns definitions */
  public static final Pattern KEYWORDS_ARGS_FORMAT = Pattern
      .compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one
  // or
  // more
  // keywords
  // separated
  // by
  // whitespace
  public static final Pattern DELETE_ARGS_FORMAT = Pattern.compile("(?<index>\\d+(?:\\s+\\d+)*)"); // one
  // or
  // more
  // number
  // separated
  // by
  // whitespace
  public static final Pattern DONE_ARGS_FORMAT = Pattern.compile("(?<index>\\d+(?:\\s+\\d+)*)"); // one
  // or
  // more
  // number
  // separated
  // by
  // whitespace
  public static final Pattern PATH_ARGS_FORMAT = Pattern.compile("(?<keyword>\\S+(?:\\s+\\S+)*)");
}
