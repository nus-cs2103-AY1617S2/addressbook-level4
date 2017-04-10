# Test Script

This is the script for Manual Scripted Testing and a short review of the results.

###Test Case 01
-------- | :--------
Test Case Summary | Add task without any details
Test Data | `add TEST1`
Expected Result | Floating task added
Actual Result | Floating task added
Status | **PASSED**

###Test Case 02
-------- | :--------
Test Case Summary | Add task with only end/ 
Test Data | `add TEST2 end/01/04/2017 12:00`
Expected Result | Task with deadline added
Actual Result | Task with deadline added
Status | **PASSED**

###Test Case 03
-------- | :--------
Test Case Summary | Add task with invalid time
Test Data | `add TEST3 start/010/04/2017 10:00`
Expected Result | Prompt: _Time should always follow the dd/mm/yyyy hh:mm format_
Actual Result | Prompt: _Time should always follow the dd/mm/yyyy hh:mm format_
Status | **PASSED**
Remarks | Time parameter follows a rigid requirements

###Test Case 04
-------- | :--------
Test Case Summary | Add task with desc/
Test Data | `add TEST4 start/02/04/2017 10:00 end/02/04/2017 12:00 desc/adding with start and end`
Expected Result | Task with description added
Actual Result | Task with description added
Status | **PASSED**

###Test Case 05
-------- | :--------
Test Case Summary | Add task with multiple tags
Test Data | `add TEST5 start/04/04/2017 12:00 end/04/04/2017 16:00 desc/adding with multiple tags tag/start tag/end tag/desc`
Expected Result | Task with multiple tags added
Actual Result | Task with multiple tags added
Status | **PASSED**
Remarks | Allow single and multiple tagging

###Test Case 06
-------- | :--------
Test Case Summary | Add task with multiple tags
Test Data | `add TEST6 start/04/04/2017 12:00 end/04/04/2017 16:00 desc/adding with multiple tags tag/start tag/end, desc`
Expected Result | Prompt: _Tags names should be alphanumeric_
Actual Result | Prompt: _Tags names should be alphanumeric_
Status | **PASSED**
Remarks | Multiple tagging follows format **tag/tag1 tag/tag2**

###Test Case 07
-------- | :--------
Test Case Summary | Add task with time before current local time
Test Data | `add TEST7 start/02/02/2017 16:00 end/03/02/2017 18:00 desc/this is for passed time period tag/add`
Expected Result | Task successfully added
Actual Result | Task successfully added
Status | **PASSED**
Remarks | No restriction related to current local time

###Test Case 08
-------- | :--------
Test Case Summary | Add task with flexible input 
Test Data | `add TEST8 end/02/02/2017 16:00 desc/ Flexible sequence start/01/02/2017 16:00`
Expected Result | Task successfully added
Actual Result | Task successfully added
Status | **PASSED**
Remarks | Input recognition follows keyword instead of sequence

###Test Case 09
-------- | :--------
Test Case Summary | Add task with end time preceding start time 
Test Data | `add TEST9 start/02/04/2017 10:00 end/02/04/2017 08:00`
Expected Result | Prompt: _Start time must precede end time_
Actual Result | Prompt: _Start time must precede end time_
Status | **PASSED**

###Test Case 10
-------- | :--------
Test Case Summary | Add exact task duplicate 
Test Data | `add TEST10 start/05/04/2017 14:00 end/05/04/2017 15:00`
`add TEST10 start/05/04/2017 14:00 end/05/04/2017 15:00`
Expected Result | Prompt: _This task already exists in JOBS_
Actual Result | Prompt: _This task already exists in JOBS_
Status | **PASSED**

###Test Case 11
-------- | :--------
Test Case Summary | Add task only differing in description
Test Data | `add TEST11 start/06/04/2017 13:00 end/06/04/2017 14:00`
`add TEST11 start/06/04/2017 13:00 end/06/04/2017 14:00 desc/duplicating test`
Expected Result | Prompt: _This task already exists in JOBS_
Actual Result | Prompt: _This task already exists in JOBS_
Status | **PASSED**
Remarks | Duplicate strictly follows name, startTime and endTime comparison

###Test Case 12
-------- | :--------
Test Case Summary | Add task only differing in tags
Test Data | `add TEST11 start/06/04/2017 13:00 end/06/04/2017 14:00 tag/duplicate`
Expected Result | Prompt: _This task already exists in JOBS_
Actual Result | Prompt: _This task already exists in JOBS_
Status | **PASSED**
Remarks | Duplicate strictly follows name, startTime and endTime comparison

###Test Case 13
-------- | :--------
Test Case Summary | Add task only differing in recur
Test Data | `add TEST11 start/06/04/2017 13:00 end/06/04/2017 14:00 recur/2`
Expected Result | Prompt: _This task already exists in JOBS_
Actual Result | Prompt: _This task already exists in JOBS_
Status | **PASSED**
Remarks | Duplicate strictly follows name, startTime and endTime comparison

###Test Case 14
-------- | :--------
Test Case Summary | Add task with exactly the same name and start time
Test Data | `add TEST11 start/06/04/2017 13:00`
Expected Result | Task successfully added
Actual Result | Task successfully added
Status | **PASSED**

###Test Case 15
-------- | :--------
Test Case Summary | Add task with exactly the same name and start time
Test Data | `add TEST11 end/06/04/2017 14:00`
Expected Result | Task successfully added
Actual Result | Task successfully added
Status | **PASSED**

###Test Case 16
-------- | :--------
Test Case Summary | Delete task from list
Test Data | `delete 1`
Expected Result | Task with index 1 is deleted
Actual Result | Task with index 1 is deleted
Status | **PASSED**

###Test Case 17
-------- | :--------
Test Case Summary | Delete task with out of bound index
Test Data | `delete 40`
Expected Result | Prompt: _The task index provided is invalid_
Actual Result | Prompt: _The task index provided is invalid_
Status | **PASSED**

###Test Case 16
-------- | :--------
Test Case Summary | Delete task with name reference
Test Data | `delete TEST11`
Expected Result | Prompt: _Invalid command format_
Actual Result | Prompt: _Invalid command format_
Status | **PASSED**
Remarks | Delete function only use index for task identification

###Test Case 17
-------- | :--------
Test Case Summary | Edit a task
Test Data | `edit 1 start/07/04/2017 09:00`
Expected Result | Edit start time without affecting other details
Actual Result | Edit start time without affecting other details
Status | **PASSED**

###Test Case 18
-------- | :--------
Test Case Summary | Removing start time
Test Data | `edit 1 start/`
Expected Result | Remove start time, become task with deadline
Actual Result | Remove start time, become task with deadline
Status | **PASSED**
Remarks | To remove details, use / followed by empty details

###Test Case 19
-------- | :--------
Test Case Summary | Removing end time
Test Data | `edit 1 end/`
Expected Result | End time removed
Actual Result | End time removed
Status | **PASSED**
Remarks | To remove details, use / followed by empty details

###Test Case 20
-------- | :--------
Test Case Summary | Remove tags
Test Data | `edit 1 tag/`
Expected Result | All tags are removed
Actual Result | All tags are removed
Status | **PASSED**
Remarks | All tags will be removed 

###Test Case 21
-------- | :--------
Test Case Summary | Edit the recursion period of task
Test Data | `edit 1 recur/2`
Expected Result | Recursion period will be edited
Actual Result | Prompt: _At least one field to edit must be provided_
Status | **FAILED**
Remarks | Unable to change recursion detail

###Test Case 22
-------- | :--------
Test Case Summary | Remove recursion function
Test Data | `edit 1 recur/`
Expected Result | Remove the recurring function of task
Actual Result | Prompt: _At least one field to edit must be provided_
Status | **PASSED**
Remarks | Unable to change recursion detail

###Test Case 23
-------- | :--------
Test Case Summary | Edit tags attached to the task
Test Data | `edit 1 tag/edit tag/multiple`
Expected Result | Override all previous tags with new tags input
Actual Result | Override all previous tags with new tags input
Status | **PASSED**

###Test Case 24
-------- | :--------
Test Case Summary | Adding tag with multiple words
Test Data | `edit 1 tag/single word`
Expected Result | Prompt: _Tags names should be alphanumeric_
Actual Result | Prompt: _Tags names should be alphanumeric_
Status | **PASSED**
Remarks | Tag only accept one word input

###Test Case 25
-------- | :--------
Test Case Summary | Edit using details other than index
Test Data | `edit NAME`
Expected Result | Prompt: _Invalid command format_
Actual Result | Prompt: _Invalid command format_
Status | **PASSED**

###Test Case 26
-------- | :--------
Test Case Summary | To marks a task as complete
Test Data | `complete 1`
Expected Result | Status of task will change to complete
Actual Result | Status of task will change to complete
Status | **PASSED**
Remarks |

###Test Case 27
-------- | :--------
Test Case Summary | Reverse completed task to in-progress
Test Data | `complete 2`
`in-progress 2`
Expected Result | Prompt: _Unknown command_
Actual Result | Prompt: _Unknown command_
Status | **PASSED**
Remarks | Unable to reverse status unless using undo command

###Test Case 28
-------- | :--------
Test Case Summary | List down all tasks
Test Data | `list`
Expected Result | All tasks will be listed
Actual Result | All tasks will be listed
Status | **PASSED**

###Test Case 29
-------- | :--------
Test Case Summary | List down all completed tasks
Test Data | `list complete`
Expected Result | All completed tasks listed
Actual Result | All completed tasks listed
Status | **PASSED**

###Test Case 30
-------- | :--------
Test Case Summary | Edit from completed tasks list
Test Data | `list complete`
`edit 1 TEST12_complete tag/complete`
Expected Result | Task successfully edited
Actual Result | Task successfully edited
Status | **PASSED**

###Test Case 31
-------- | :--------
Test Case Summary | Edit from in-progress tasks list
Test Data | `list in-progress`
`edit 1 tag/list`
Expected Result | Task successfully edited
Actual Result | Task successfully edited
Status | **PASSED**

###Test Case 32
-------- | :--------
Test Case Summary | Complete task from in-progress tasks list
Test Data | `list in-progress`
`complete 1`
Expected Result | Task marked as completed
Actual Result | Task marked as completed
Status | **PASSED**

###Test Case 33
-------- | :--------
Test Case Summary | Listing using parameter aside from status
Test Data | `list TEST`
Expected Result | Display 0 task
Actual Result | Display 0 task
Status | **PASSED**
Remarks | List only compares with status

###Test Case 34
-------- | :--------
Test Case Summary | List all tasks with substring "1" in name or description
Test Data | `find 1`
Expected Result | List all task with "1" in their, description or tag 
Actual Result | List all task with "1" in their, description or tag 
Status | **PASSED**
Remarks | Find function accommodates substring of name, description or tag 

###Test Case 35
-------- | :--------
Test Case Summary | List task based on time
Test Data | `find 2017`
Expected Result | Display 0 task
Actual Result | Display 0 task
Status | **PASSED**

###Test Case 36
-------- | :--------
Test Case Summary | List all tasks with substring "1" in tag and edit task
Test Data | `find add`
`edit 1 tag/retag tag/find`
Expected Result | Task with index 1 will have tag overridden
Actual Result | Task with index 1 will have tag overridden
Status | **PASSED**

###Test Case 37
-------- | :--------
Test Case Summary | Find all tasks with status complete
Test Data | `find complete`
Expected Result | Only display tasks with "complete" in name or description
Actual Result | Only display tasks with "complete" in name or description
Status | **PASSED**
Remarks | Find does not support comparison with status

###Test Case 38
-------- | :--------
Test Case Summary | Find all tasks with status in-progress
Test Data | `find in-progress`
Expected Result | Only display tasks with "in-progress" in name or description
Actual Result | Only display tasks with "in-progress" in name or description
Status | **PASSED**
Remarks | Find does not support comparison with status

###Test Case 39
-------- | :--------
Test Case Summary | Clear the whole taskbook
Test Data | `clear`
Expected Result | Removes all tasks on taskbook
Actual Result | Removes all tasks on taskbook
Status | **PASSED**

###Test Case 40
-------- | :--------
Test Case Summary | Complete a recurring task
Test Data | `add TEST14 start/07/04/2017 10:00 end/07/04/2017 12:00 recur/3`
`complete 1`
Expected Result | Both dates will increase by 3 days
Actual Result | Both dates will increase by 3 days
Status | **PASSED**
Remarks | Complete command will continue to increase dates by specified period

###Test Case 41
-------- | :--------
Test Case Summary | Undo function to cancel an action
Test Data | `delete 1`
`undo`
Expected Result | Retrieve the previously deleted task
Actual Result | Retrieve the previously deleted task
Status | **PASSED**

###Test Case 42
-------- | :--------
Test Case Summary | Redo function to cancel an undo action
Test Data | `delete 1`
`undo`
`redo`
Expected Result | Task with index 1 is deleted
Actual Result | Task with index 1 is deleted
Status | **PASSED**
Remarks | Undo/redo supports up to 10 consecutive times

###Test Case 43
-------- | :--------
Test Case Summary | Redo function exceeding the number of undo call
Test Data | `add TEST14 start/07/04/2017 10:00 end/07/04/2017 12:00`
`undo`
`redo`
`redo`
Expected Result | Prompt: _No more commands to redo_
Actual Result | Prompt: _No more commands to redo_
Status | **PASSED**

###Test Case 44
-------- | :--------
Test Case Summary | Undo a previously completed task
Test Data | `add TEST14 start/07/04/2017 10:00 end/07/04/2017 12:00`
`complete`
`undo`
Expected Result | Status of task turns in-progress
Actual Result | Status of task turns in-progress
Status | **PASSED**
Remarks | The only method to reverse the status of a previously completed task

###Test Case 48
-------- | :--------
Test Case Summary | Set function with email/ and pwd/
Test Data | `set email/cs2103cks@gmail.com pwd/abcdefgh123456`
Expected Result | Account saved
Actual Result | Account saved
Status | **PASSED**

###Test Case 49
-------- | :--------
Test Case Summary | Set function with only email/
Test Data | `set email/cs2103cks@gmail.com`
Expected Result | Invalid command format
Actual Result | Invalid command format
Status | **PASSED**
Remarks | Input requires both email and password at the same time

###Test Case 50
-------- | :--------
Test Case Summary | Set function with only pwd/
Test Data | `set pwd/abcdefgh123456`
Expected Result | Invalid command format
Actual Result | Invalid command format
Status | **PASSED**
Remarks | Input requires both email and password at the same time

###Test Case 51
-------- | :--------
Test Case Summary | Load Google Calendar feature
Test Data | `display`
Expected Result | Browser panel loading Google Calendar
Actual Result | Browser panel loading Google Calendar
Status | **PASSED**
Remarks | Logging in with different account will prompt authorization

###Test Case 52
-------- | :--------
Test Case Summary | Test for generating help browser
Test Data | `help`
Expected Result | Redirect to help page
Actual Result | Redirect to help page
Status | **PASSED**
