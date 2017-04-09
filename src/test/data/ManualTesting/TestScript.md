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

`add Submit Assignment e/01/31/2018 23:59`.

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

#### Result

Feedback shows:

> New save location: src/test/data/ManualTesting/Test/newTaskManager.xml

The storage file is saved at the specified path.

## Sort command
To sort the list of tasks, start command with the keyword `save`.

`sort priority`

#### Result

Feedback shows:

> Sorted by priority!

The list of tasks is sorted according to priority, with the 'hi' priority at the top followed by 'mid' and 'low'.

## Sync command
To sync your tasks in Opus into Google calendar, start command with the keyword `sync`.

`sync on` to turn syncing on
`sync off` to turn syncing off

####Result
1. After the first step, Opus will direct you to the Google Tasks authorization page in your browser. Once authorized with your Google account, there will be a pop-up window telling you that sync has been turned on, and you may now close the authorization page and go back to Opus. After the authorization, all the tasks you added will sync to your Google Calendar.
2. After the second step, sync to Google Calendar will be turned off.
