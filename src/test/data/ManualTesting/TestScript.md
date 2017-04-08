Test Script

Undo

Run "add todo"
You will see a new todo called "todo" added at the bottom of the list

Run "undo"
You will see the new todo disappear

Redo

Run "redo"
You will see the new todo reappear

Complete

Complete with default time

Run "list" to show all todos
Run "complete 1"
You will see the first todo in the list now says "Completed at {current time}"

Run "undo"
You will see the first todo in the list now says "Not Complete"

Run "redo"
You will see the first todo in the list is once again completed with the time the complete command was run

Complete with specified time

Run "list" to show all todos
Run "complete 2 12:11AM 09/04/17"
You will see the second todo in the list now says "Completed at 12:11AM 09/04/17"

Run "undo"
You will see the second todo in the list now says "Not Complete"

Run "redo"
You will see the second todo in the list again says "Completed at 12:11AM 09/04/17"

Uncomplete

Run "list" to show all todos
Run "uncomplete 1"
You will see the first todo in the list again says "Not Complete"

Find by Start Time

Run "list" to show all todos
Run "find s/12:15AM 16/04/17"
You will see the following events listed:

1. Eat breakfast
2. Eat midnight snack
3. Paint house
4. Mow front yard
5. Mow back yard
6. Wash back yard
7. Dispose of body
8. Do CS2102 Project
9. Attend CS2103 tutorial
10. Populate task data

All of these events have start dates on or before 12:15AM 16/04/17.
No floating tasks or deadlines are listed.
You may also see additional events if you have added any that start before 12:15AM 16/04/17

Run "find s/today"
You will see all events with start times before the end of the current day

Run "find s/yesterday"
You will see all events with start times before the end of yesterday

Run "find s/tomorrow"
You will see all events with start times before the end of tomorrow

Find by End Time

Run "list" to show all todos
Run "find e/12:15AM 16/04/17"
You will see the following todos listed:

1. Walk the cat
2. Eat breakfast
3. Eat midnight snack
4. Paint house
5. Mow front yard
6. Mow back yard
7. Wash back yard
8. Dig up front yard
9. Dig up back yard
10. Dispose of body
11. Do CS2102 Project
12. Attend CS2103 tutorial
13. Populate task data

All of these todos have end dates on or before 12:15AM 16/04/17.
No floating tasks are listed.
You may also see additional todos if you have added any that end before 12:15AM 16/04/17

Run "find e/today"
You will see all events with end times before the end of the current day

Run "find e/yesterday"
You will see all events with end times before the end of yesterday

Run "find e/tomorrow"
You will see all events with end times before the end of tomorrow

Find by Complete Time

Run "list" to show all todos
Run "find c/12:15AM 16/04/17"
You will see the following todos listed:

1. Update Documentation
2. Eat dinner
3. Walk the cat
4. Eat breakfast
5. Eat midnight snack
6. Paint house
7. Mow front yard
8. Mow back yard
9. Wash back yard
10. Dig up front yard
11. Dig up back yard
12. Put rocks in front yard
13. Dispose of body
14. Populate task data

All of these todos have complete times on or before 12:15AM 16/04/17.
You may also see additional todos if you have added any that were completed before 12:15AM 16/04/17

Run "find c/today"
You will see all events with complete times before the end of the current day

Run "find c/yesterday"
You will see all events with complete times before the end of yesterday

Run "find c/tomorrow"
You will see all events with complete times before the end of tomorrow

Find by Multiple Parameters

Run "list" to show all todos
Run "find populate c/12:15AM 16/04/17 t/school"
You will see the following todo listed:

1. Populate task data

This todo has "populate" in the name (case insensitive), was completed before 12:15AM 16/04/17, and has the tag "school"

Find all Completed

Run "list" to show all todos
Run "find c/"
You will see the following todos listed:

1. Update Documentation
2. Eat dinner
3. Walk the cat
4. Eat breakfast
5. Eat midnight snack
6. Paint house
7. Mow front yard
8. Mow back yard
9. Wash back yard
10. Dig up front yard
11. Dig up back yard
12. Put rocks in front yard
13. Dispose of body
14. Populate task data

Find all Uncompleted

Run "list" to show all todos
Run "find c/not"
You will see 36 todos listed. These are all todos in the list that are not yet completed. Notice there were 14 todos listed when "find c/" run. 36 + 14 = 50, which is the total number of todos in the sample data for manual testing.