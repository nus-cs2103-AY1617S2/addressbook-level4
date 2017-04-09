# A0164889E-unused
###### /TypicalTestPersons.java
``` java
    public TypicalTestPersons() {
        try {
            alice = new PersonBuilder()
                    .withName("Alice Paul")
                    .withGroup("group1")
                    .withStartDate("01.01")
                    .withEndDate("12.12")
                    .withTags("complete")
                    .build();
            benson = new PersonBuilder()
                    .withName("Benson Meier")
                    .withGroup("311")
                    .withEndDate("12.11")
                    .withTags("incomplete")
                    .build();
            carl = new PersonBuilder()
                    .withName("Carl Kurz")
                    .withGroup("wall street")
                    .withEndDate("10.01")
                    .withTags("complete")
                    .build();
            daniel = new PersonBuilder()
                    .withName("Daniel Meier")
                    .withGroup("street")
                    .withEndDate("11.11")
                    .withTags("incomplete")
                    .build();
            elle = new PersonBuilder()
                    .withName("Elle Meyer")
                    .withGroup("michegan")
                    .withEndDate("05.05")
                    .withTags("incomplete")
                    .build();
            fiona = new PersonBuilder()
                    .withName("Fiona Kunz")
                    .withGroup("little tokyo")
                    .withEndDate("07.07")
                    .withTags("incomplete")
                    .build();
            george = new PersonBuilder()
                    .withName("George Best")
                    .withEndDate("04.12")
                    .withGroup("street")
                    .withTags(Tag.inc)
                    .build();

            // Manually added
            hoon = new PersonBuilder()
                    .withName("Hoon Meier")
                    .withStartDate("01.01")
                    .withEndDate("05.05")
                    .withGroup("little india")
                    .withTags("incomplete")
                    .build();
            ida = new PersonBuilder()
                    .withName("Ida Mueller")
                    .withStartDate("03.03")
                    .withEndDate("08.08")
                    .withGroup("chicago")
                    .withTags("incomplete")
                    .build();
        } catch (IllegalValueException e) {
            e.printStackTrace();
            assert false : "not possible";
        }
    }
```
