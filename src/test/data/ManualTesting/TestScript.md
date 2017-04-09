# Test Script for Manual Testing

Follow this script to perform the following manual tests for Opus.

## Getting Started

1. Ensure that data is being placed in `src/test/data/ManualTesting/data/opus.xml`
2. Double-click on `Opus.jar`
3. Verify that predefined tasks are loaded

## Add command

To add a task, start command with keyword `add`.

### Add a floating task

`add Buy fruits`

#### Result

Feedback shows:

> New task added: Buy fruits<br/>
> Status: incomplete

Newly added task should be at index #22 without any attributes below it.

## Delete command

To delete a task, start command with keyword `delete`.

### Delete a single task

`delete 1`

## List command
To see the whole list of tasks, enter command `list`.

#### Result

The whole list of tasks is shown to the user.

## Save command
To change the default data save location, start command with the keyword `save`.

>* `save <directory>` - `<directory>` must be a valid path to a file on the local computer. If the folders specified in the new file path does not exist, they will be created. Note that the save file in the old save location remains.

Type `> save src/test/data/ManualTesting/Test/newTaskManager.xml`

> New task added: Buy fruits<br/>
> Status: incomplete

Newly added task should be at index #22 without any attributes below it.

