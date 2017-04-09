# Test Script for Manual Testing

Follow this script to perform the following manual tests for Opus.

## Getting Started

1. Ensure that data is being placed in `src/test/data/ManualTesting/data/opus.xml`
2. Double-click on `Opus.jar`
3. Verify that predefined tasks are loaded

## Add command

To add a task, start command with keyword `add`.

### 1. Add a floating task

`add Buy fruits`

#### Result

Feedback shows:

> New task added: Buy fruits<br/>
> Status: incomplete

### 2. Add a task with deadline

`add Submit Assignment e/01/31/2018 23:59`

#### Result

Feedback shows:

> New task added: Submit Assignment<br/>
> Status: incomplete<br/>
> End Time: 31 Jan 18 11:59PM

### 3. Add a task with event time

`add School Camp b/02/01/2018 10:00 e/02/10/2018 19:00`

#### Result

Feedback shows:

> New task added: School Camp<br/>
> Status: incomplete<br/>
> Start Time: 01 Feb 18 10:00AM<br/>
> End Time: 10 Feb 18 07:00PM

## Find command

To find tasks, start the command with the keyword `find`.

> Find command accepts keywords and attributes. For keywords, the program searches through name, note and tags content and return tasks that contain either of the keywords specified. 

### 1. Find a task with a keyword

`find swimming`

#### Result

Feedback shows:

> 1 tasks listed!

The task with the name `Go swimming` is listed.

### 2. Find a task an attribute

`find p/hi`

#### Result

Feedback shows:

> 13 tasks listed!

The tasks with high priorities are listed.

### 3. Find a task with keywords and attributes

`find study consultation e/04/20/2017`

#### Result

Feedback shows:

> 3 tasks listed!

The tasks that contain either of the keywords `study` or `consultation` and end before 20th April, 2017 are listed.

## Schedule command

To schedule an existing task, start the command with the keyword `schedule`.

### 1. Schedule an event

`schedule 18 thursday 6pm to thursday 9pm`

#### Result

Feedback shows:

> Edited Task: Wash my clothes
> Status: incomplete
> Start Time: 13 Apr 17 06:00PM
> End Time: 13 Apr 17 09:00PM

The floating task at index 18 is scheduled for Thursday of the current week from 6pm to 9pm.

### 2. Schedule a deadline

`schedule 19 thursday 9pm`

#### Result

Feedback shows:

> Edited Task: Feed the dog
> Status: incomplete
> End Time: 13 Apr 17 09:00PM

The deadline of the floating task at index 19 is scheduled for Thursday 9pm of the current week.

## Help command

To see the help section of Opus, enter the command `help`.

#### Result

The help window that contains the user guide appears.

## Mark command

To toggle the status of the task to either `complete` or `incomplete`, start the command with the keyword `mark`.

### 1. Mark a task as complete

`mark 1`

#### Result

Feedback shows:

> Edited Task: Study for CS2010 Written Quiz
> Priority: hi
> Status: complete
> Note: Die liao
> Start Time: 01 Apr 17 10:00AM
> End Time: 02 Apr 17 11:59PM
> Tags: [6%]

The status of the incomplete task at the index 1 is changed to `complete`. 

### 2. Mark a task as incomplete

`mark 54`

#### Result

Feedback shows:

> Edited Task: Buy groceries for Mum
> Priority: low
> Status: incomplete

The status of the completed task at the index 54 is changed to `incomplete`. 

## Select command

To select a task, start the command with the keyword `select`.

### Select a task

`select 13`

#### Result

Feedback shows:

> Selected Task: 13

The task at index 13 is set to focus.

## Clear command

To clear the whole list of tasks, enter command `clear`.

#### Result

> Task manager has been cleared!

The whole list of tasks is shown to the user.

## Delete command

To delete a task, start command with keyword `delete`.

### Delete a single task

`delete 1`

#### Result

Feedback shows:

> Deleted Task: Buy dinner<br/>
> Priority: low<br/>
> Status: incomplete<br/>
> Start Time: 28 Apr 17 09:21PM<br/>

Task with index 1 is deleted.

## Edit command

To edit a specific task, start command with the keyword `edit`.

`edit 1 p/hi b/04/28/2017 22:00 e/04/28/2017 22:30`

#### Result

Feedback shows:

> Edited Task: Buy dinner<br/>
> Priority: hi<br/>
> Status: incomplete<br/>
> Start Time: 28 Apr 17 10:00PM<br/>
> End Time: 28 Apr 17 10:30PM

## List command

To see the whole list of tasks, enter command `list`.

#### Result

> Listed all tasks

The whole list of tasks is shown to the user.

## Save command
To change the default data save location, start command with the keyword `save`.

>* `save <directory>` - `<directory>` must be a valid path to a file on the local computer. If the folders specified in the new file path does not exist, they will be created. Note that the save file in the old save location remains.

`save src/test/data/ManualTesting/Test/newTaskManager.xml`

> New task added: Buy fruits<br/>
> Status: incomplete

Newly added task should be at index #22 without any attributes below it.

The storage file is saved at the specified path.

## Sort command
To sort the list of tasks, start command with the keyword `save`.

`sort priority`

#### Result

Feedback shows:

> Sorted all tasks by priority

The list of tasks is sorted according to priority, with the 'hi' priority at the top followed by 'mid' and 'low'.

## Sync command
To sync your tasks in Opus into Google calendar, start command with the keyword `sync`.

`sync on` to turn syncing on
`sync off` to turn syncing off

#### Result
1. After the first step, Opus will direct you to the Google Tasks authorization page in your browser. Once authorized with your Google account, there will be a pop-up window telling you that sync has been turned on, and you may now close the authorization page and go back to Opus. After the authorization, all the tasks you added will sync to your Google Calendar.
2. After the second step, sync to Google Calendar will be turned off.

## Undo/Redo command

### Undo the previous command

`add Dummy task`

`undo`

#### Result

Dummy task should be removed from the task list.

### Redo the previous undo

`redo`

#### Result

Dummy task should be back in the task list.

### Undo again

`undo`

#### Result

Dummy task should be gone again.

## Autocompletion

`a` + Tab

#### Result

`add` command should autocomplete in the command box.


## Command history

`addd blah`

#### Result

The command box should flash due to incorrect error. Press the up arrow key. The same command should appear again.

