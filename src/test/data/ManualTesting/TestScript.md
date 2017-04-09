# Manual Scipted Testing

## Loading Sample Data

1. Move `Geekeep.jar` in the following directory:
`src/test/data/ManualTesting/Geekeep.jar`
1. Rename and move the sample data in the following directory: `src/test/data/ManualTesting/data/geekeep.xml`
1. Double-click on `Geekeep.jar`

## Help Command

## See the help window

`help`

#### Result

Opens a new window that displays the UserGuide

## Add command

### 1. Add a floating task

`add Buy Apple`

#### Result

Floating task created in the floating tasks panel.

> New task added: Buy Apple<br/>

### 2. Add a task with deadline

`add Example Assignment e/01-03-17 0000`

#### Result

Deadline task created in the deadline tasks panel.

> New task added: Example Assignment Due by: 01-03-17 0000

### 3. Add a task with event time

`add Meeting 1 s/01-04-17 1630 e/01-04-17 1730`

#### Result

Event task created in the event tasks panel.

> New task added: Meeting 1 Starting from: 01-04-17 1630 until 01-04-17 1730

## Delete Command

### Delete a task

`delete 25`

#### Result

Previously added floating task should be deleted

> Deleted task: Buy Apple

## Undo/Redo Command

### 1. Undo a command

`undo`

#### Result

Undoes the delete command. The floating task should reappear.

> Undid command: delete 25

### 2. Redo a command

`redo`

#### Result

Redo the delete command. The floating task should disappear.

> Redid previous undid command: delete 25

### 3. Undo command again

`undo`

#### Result

Undoes the delete command. The floating task should reappear.

> Undid command: delete 25

## Update Command

### 1. Update an event

`update 10 s/01-04-17 1200 e/01-04-17 1800 d/Anywhere t/Work`

#### Result

Updates the task with the new specified start and end datetime, description as well as tag.

> Updated Task: Meeting 1 Starting from: 01-04-17 1200 until 01-04-17 1800 Details: Anywhere Tags: [Work]

### 2. Update a floating task

`update 10 Buy Avocado`

#### Result

Updates the task with the new specified task name

> Updated Task: Buy Avocado

### 3. Update a deadline

`update 42 e/10-03-17 0000`

#### Result

Updates the task with the new specified end datetime.

> Updated Task: Example Assignment Due by: 10-03-17 0000

### 3. Update a event into a deadline

`update 10 s/`

#### Result

Remove starting datetime for the event, changing it into a deadline.

> Updated Task: Meeting 1 Due by: 01-04-17 1800 Details: Anywhere Tags: [Work]

### 4. Update a deadline into a floating task

`update 50 e/`

#### Result

Remove ending datetime for the deadline, changing it into a floating task.

> Updated Task: Meeting 1 Details: Anywhere Tags: [Work]

### 3. Update a floating task into a event

`update 37 s/01-04-17 1200 e/01-04-17`

#### Result

Add new starting and ending datetime for the floating task, changing it into an event.

> Updated Task: Meeting 1 Starting from: 01-04-17 1200 until 01-04-17 Details: Anywhere Tags: [Work]

## Done command

### Marks a task as done

`done 10`

#### Result

Task checkbox should now be checked.

> Marked as done: Meeting 1 Starting from: 01-04-17 1200 until 01-04-17 Details: Anywhere Tags: [Work]

## UnDone command

### Marks a task as undone

`undone 10`

#### Result

Task checkbox should now be unchecked.

> Marked as undone: Meeting 1 Starting from: 01-04-17 1200 until 01-04-17 Details: Anywhere Tags: [Work]

## Listdone command

### Lists all finished tasks

`listdone`

#### Result

Only finished tasks are displayed in all three panels.

> Listed all completed tasks

## Listundone command

### Lists all uncompleted tasks

`listundone`

#### Result

Only uncompleted tasks are displayed in all three panels.

> Listed all uncompleted tasks

## List command

### Marks a task as undone

`list`

#### Result

All tasks are displayed in all three panels.

> Listed all tasks

## Find command

### 1. Find task by keyword

`find assignment`

#### Result

All tasks with assignment in their title are listed.

> 1 tasks listed!
> GeeKeep is showing all the tasks which:
> Contains the keyword(s) [assignment] in title

### 2. Find task by tag

`find t/work`

#### Result

All tasks with the specified tag are listed.

> 3 tasks listed!
> GeeKeep is showing all the tasks which:
> Contains the tags [work]

### 3. Find a task by date and time

`find a/01-04-17 0000 b/10-04-17 0000`

#### Result

All tasks which occurs in the specified time frame are listed. 

> 22 tasks listed!
> GeeKeep is showing all the tasks which:
> Happens after 01-04-17 0000
> Happens before 10-04-17 0000

## Clear command

`clear`

#### Result

Task manager should be empty.

> Task manager has been cleared!

## Store command

`store ../newTaskManager.xml`

Save file should now be located at `src/test/data/newTaskManager.xml`

> GeeKeep file path successfully set to ../newTaskManager.xml

## Command History

Up and down Arrow key

#### Result

The previously entered commands can now cycled through in the order which is most recent.

## Exit Command

`Exit`

#### Result

Quits Geekeep.