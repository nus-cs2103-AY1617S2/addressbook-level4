## Add command

To add a task, starting command with keyword `add`.

### Add a floating task

`add Buy fruits`

#### Result

Feedback shows:

> New task added: Buy fruits<br/>
> Status: incomplete

Newly added task should be at index #22 without any attributes below it.

### Add a task with deadline

`add Submit CS2103T project e/today 12pm`

#### Result

Feedback shows:

> New task added: Submit CS2103T project<br>
> Status: incomplete
> End Time: (Today's date) 12:00PM

Newly added task should appear in task panel.

### Add a task with note

`add Buy groceries from Sheng Shiong n/Milk`

#### Result

Feedback shows:

> New task added: Buy groceries from Sheng Shiong
> Status: incomplete
> Note: Milk

Newly added task should appear in task panel.

### Add a task with priority

`add Clean up CS2101 documentation p/hi`

#### Result

Feedback shows:

> New task added: Clean up CS2101 documentation
> Priority: hi
> Status: incomplete

Newly added task should appear in task panel with HI priority.

### Add an event

`add Go for NOC camp b/tomorrow 8am e/next monday 4pm`

#### Result

Feedback shows:

> New task added: Go for NOC camp
> Status: incomplete
> Start Time: (Tomorrow's date) 08:00AM
> End Time: (Next Monday's date) 04:00PM

Newly added event should appear in the task panel.

## Sync command

### Sync Opus with Google Tasks

`sync on`

#### Result

1. Your web browser will open prompting to login to Google and give permission to Opus
2. After logging in, the tasks from Opus will be synced to your Google Task list, under `Opus` (Note: you have to open the correct task list, there is a dropdown arrow to open it)
3. The tasks should show in the Google Tasks list

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
