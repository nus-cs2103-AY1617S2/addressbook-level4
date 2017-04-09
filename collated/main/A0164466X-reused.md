# A0164466X-reused
###### /java/seedu/task/logic/parser/CliSyntax.java
``` java
    /* Prefix definitions */
    public static final Prefix PREFIX_GROUP = new Prefix("g/");
    public static final Prefix PREFIX_END_DATE = new Prefix("d/");
    public static final Prefix PREFIX_START_DATE = new Prefix("s/");

    /* Patterns definitions */
    public static final Pattern KEYWORDS_ARGS_FORMAT =
            Pattern.compile("(?<keywords>\\S+(?:\\s+\\S+)*)"); // one or more keywords separated by whitespace

}
```
