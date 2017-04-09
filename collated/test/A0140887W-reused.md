# A0140887W-reused
###### \java\guitests\guihandles\TaskCardHandle.java
``` java
    private String prettyDate (Date date) {
        StringBuilder prettydate = new StringBuilder();
        prettydate.append(prettyMonth (date.getMonth() + 1));
        prettydate.append(" " + date.getDate() + ", ");
        prettydate.append((date.getYear() + 1900) + " at ");
        prettydate.append(prettyTime(date.getHours(), date.getMinutes()));
        return prettydate.toString();
    }

    private String prettyMonth (int month) {
        switch (month) {
        case 1 : return "January";
        case 2 : return "February";
        case 3 : return "March";
        case 4 : return "April";
        case 5 : return "May";
        case 6 : return "June";
        case 7 : return "July";
        case 8 : return "August";
        case 9 : return "September";
        case 10 : return "October";
        case 11 : return "November";
        case 12 : return "December";
        default : return null;
        }
    }

    private String prettyTime (int hours, int minutes) {
        String suffix = (hours <= 12) ? "am" : "pm";
        String hour = (hours <= 12) ? Integer.toString(hours) : Integer.toString(hours - 12);
        String minute = (minutes < 10) ? "0" + Integer.toString(minutes) : Integer.toString(minutes);
        return hour + ":" + minute + suffix;
    }

```
