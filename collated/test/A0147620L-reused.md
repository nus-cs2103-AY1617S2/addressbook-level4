# A0147620L-reused
###### \java\guitests\ListCommandTest.java
``` java
    public Date stringToDate(DateFormat format, String dateString) throws IllegalValueException {
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException pe) {
            throw new IllegalValueException(dateString + " is not a valid date");
        }
    }
}
```
