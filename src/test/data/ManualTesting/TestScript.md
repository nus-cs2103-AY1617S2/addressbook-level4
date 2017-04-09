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

## List command
To see the whole list of tasks, enter command `list`.

#### Result

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
