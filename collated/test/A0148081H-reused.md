# A0148081H-reused
###### /java/seedu/opus/logic/LogicManagerTest.java
``` java
        /**
         * Generates a Task object with given start time. Other fields will have some dummy values.
         */
        private Task generateTaskWithStartTime(String startTime) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime(startTime),
                    new DateTime("02/02/2017 00:00"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given end time. Other fields will have some dummy values.
         */
        private Task generateTaskWithEndTime(String endTime) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    new Priority("hi"),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime(endTime),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given priority. Other fields will have some dummy values.
         */
        private Task generateTaskWithPriority(String priority) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    new Priority(priority),
                    new Status("incomplete"),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime("01/01/2017 23:59"),
                    new UniqueTagList(new Tag("tag"))
            );
        }

        /**
         * Generates a Task object with given status. Other fields will have some dummy values.
         */
        private Task generateTaskWithStatus(String status) throws Exception {
            return new Task(
                    new Name("Finish assignment"),
                    new Priority("hi"),
                    new Status(status),
                    new Note("House of 1"),
                    new DateTime("01/01/2017 00:00"),
                    new DateTime("01/01/2017 23:59"),
                    new UniqueTagList(new Tag("tag"))
            );
        }
```
