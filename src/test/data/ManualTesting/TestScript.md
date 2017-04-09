# Test Script

By : `T11-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

### Undo

Run `add todo`
You will see a new todo called `todo` added at the bottom of the list

Run `undo`
You will see the new todo disappear

### Redo

Run `redo`
You will see the new todo reappear

### Complete

> * Complete with default time

Run `list` to show all todos
Run `complete 1`
You will see the first todo in the list now says `Completed at {current time}`

Run `undo`
You will see the first todo in the list now says `Not Complete`

Run `redo`
You will see the first todo in the list is once again completed with the time the complete command was run

> * Complete with specified time

Run `list` to show all todos
Run `complete 2 12:11AM 09/04/17`
You will see the second todo in the list now says `Completed at 12:11AM 09/04/17`

Run `undo`
You will see the second todo in the list now says `Not Complete`

Run `redo`
You will see the second todo in the list again says `Completed at 12:11AM 09/04/17`

### Uncomplete

Run `list` to show all todos
Run `uncomplete 1`
You will see the first todo in the list again says `Not Complete`

### Find by Start Time

Run `list` to show all todos
Run `find s/12:15AM 16/04/17`
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

Run `find s/today`
You will see all events with start times before the end of the current day

Run `find s/yesterday`
You will see all events with start times before the end of yesterday

Run `find s/tomorrow`
You will see all events with start times before the end of tomorrow

### Find by End Time

Run `list` to show all todos
Run `find e/12:15AM 16/04/17`
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

Run `find e/today`
You will see all events with end times before the end of the current day

Run `find e/yesterday`
You will see all events with end times before the end of yesterday

Run `find e/tomorrow`
You will see all events with end times before the end of tomorrow

### Find by Complete Time

Run `list` to show all todos
Run `find c/12:15AM 16/04/17`
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

Run `find c/today`
You will see all events with complete times before the end of the current day

Run `find c/yesterday`
You will see all events with complete times before the end of yesterday

Run `find c/tomorrow`
You will see all events with complete times before the end of tomorrow

### Find by Multiple Parameters

Run `list` to show all todos
Run `find populate c/12:15AM 16/04/17 t/school`
You will see the following todo listed:

1. Populate task data

This todo has `populate` in the name (case insensitive), was completed before 12:15AM 16/04/17, and has the tag `school`

### Find all Completed

Run `list` to show all todos
Run `find c/`
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

### Find all Uncompleted

Run `list` to show all todos
Run `find c/not`
You will see 36 todos listed. These are all todos in the list that are not yet completed.
Notice there were 14 todos listed when `find c/` run. 36 + 14 = 50, which is the total number of todos in the sample data for manual testing.

### Add a Floating Todo with Tags

Run `add add test t/tag1 t/tag2`

You will see that there is a new todo `51. add test` with two tags `tag1` and `tag2`.


### Add a deadline Todo

Run `add add deadline test e/11:00AM 11/11/17 t/tag1`

You will see that there is a new todo `52. add deadline test` with `11:00AM 11/11/17` as end time and `tag1` as tag.


### Add a deadline Todo with Default Date and Time

Run `add add deadline with default test e/ t/tag1`

You will see that there is a new todo `53. add deadline test with default` with tomorrow's midnight as end time and `tag1` as tag.


### Add a deadline Todo with Default Date and Time

Run `add add deadline with default date only test e/11/11/17 t/tag1`

You will see that there is a new todo `54. add deadline test with default date only` with `12:00AM 11/11/17` as end time and `tag1` as tag.

### Add a Event Todo

Run `add add event test s/10:00AM 11/11/17 e/11:00AM 11/11/17 t/tag1`

You will see that there is a new todo `55. add event test` with `10:00AM 11/11/17` as start time and `11:00AM 11/11/17` as end time and `tag1` as tag.

### Add a deadline Todo with Default Date and Time

Run `add add event default test s/ e/ t/tag1`

You will see that there is a new todo `56. add event test with default test` with today's midnight as start time and tomorrow's midnight as end time and `tag1` as tag.


### Add a deadline Todo with Default Date and Time

Run `add add event with default date only test s/10/11/17 e/11/11/17 t/tag1`

You will see that there is a new todo `57. add event test with default date only` with `12:00AM 10/11/17` as start time and `12:00AM 11/11/17` as end time and `tag1` as tag.

### Edit a Todo with Name and Tags

Run `list` to show all todos.

Run `edit 1 Walk the dog edited ta/edited`

You will see that the first todo is now changed into a todo with `Walk the dog edited` as  its name and `petcare` and `edited` as its tags.

### Edit for Cleaning the Tags of a Todo

Run `edit 1 t/`

You will see that the first todo has no any tag.

### Edit for Adding Multiple Tags

Run `edit 1 ta/tag1 ta/tag2`

You will see that the first todo's tags are now changed `tag1` and `tag2`.

The three edit commands above can also apply to deadline todo and event todo.

### Edit to Convert a Floating Todo to a Deadline Todo

Run `edit 1 e/11:00AM 11/11/17`

You will see that the first todo has a new end time `11:00AM 11/11/17`.
Now this todo is a deadline todo.

### Edit to Convert a Floating Todo to a Event Todo

Run `edit 2 s/10:00AM 11/11/17 e/12:00PM 11/11/17`

You will see that the second todo has a new start time `10:00AM 11/11/17` and a new end time `12:00PM 11/11/17`.
Now this todo is a event todo.

### Edit to Convert a Deadline Todo to a Event Todo

This is same as converting a floating todo to a event todo.

Run `edit 1 s/10:00AM 11/11/17 e/12:00PM 11/11/17`

You will see that the first todo has a new start time `10:00AM 11/11/17` and a new end time `12:00PM 11/11/17`.
Now this todo is a event todo.

### Edit to Convert a Event Todo to a Deadline Todo

Run `edit 1 e/1:00PM 11/11/17`

You will see that the first todo's new start time is gone and it has a new end time `1:00PM 11/11/17`.
Now this todo is a deadline todo.

### Edit a Todo with Defualt Date and Time

Run `edit 1 e/`

You will see that the first todo's new end time is set to tomorrow's midnight.

Run `edit 1 s/ e/`

You will see that the first todo's new start time is set to today's midnight and new end time is set to tomorrow's midnight.

### Edit a Todo with Defualt Date

Run `edit 1 e/1/1/17`

You will see that the first todo's new end time is set to `12:00AM 01/01/17`.
This todo is now changed into a deadline todo.
If you want to keep the start time, you should type the start time information.

Run `edit 1 s/1/1/17 e/2/1/17`

You will see that the first todo's new start time is set to `12:00AM 01/01/17` and new end time is set to `12:00AM 02/01/17`.
This todo is now changed into a event todo.

All edit command will change a complete todo back to a uncomplete todo.

### Delete a Todo

Run `delete 57`
You will see that the 57th todo is gone.

### Clear the List

Run `clear`
You will see that the Todo list has no any todo now.

Please run `undo` to continue the latter test cases.
