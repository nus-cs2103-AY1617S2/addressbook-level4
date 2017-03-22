# Appendix B: Use Cases

- [Use Case Diagram](#use-case-diagram)
- [Use Cases](#use-cases-1)
    - [Add Floating Task](#add-floating-task)
    - [Add Deadline Task](#add-deadline-task)
    - [Add Event](#add-event)
    - [List Tasks](#list-tasks)
    - [List all Tasks](#list-all-tasks)
    - [Find Tasks](#find-tasks)
    - [Edit Task](#edit-task)
    - [Mark Task as Completed](#mark-task-as-completed)
    - [Delete Task](#delete-task)
    - [Undo Action](#undo-action)
    - [Redo Action](#redo-action)
    - [Save to File](#save-to-file)
    - [Change File storage](#change-file-storage)

## Use Case Diagram

![Use Case Diagram](diagrams/Use_Case_Diagram.PNG)

## Use Cases

### Add Floating Task

#### MSS

1. User enters command with name and description of task
2. Taskbook enters task into system <br>
**Use case ends**

#### Extensions

- [Add Deadline Task](#add-deadline-task) occurs after step 1.

### Add Deadline Task
This use case extends [Add Floating task](#add-floating-task). It is inserted at extension point Add Deadline Task.

#### MSS

1. User enters parameters for end date/time <br>
**Use case continues in step 2 of [Add Floating Task](#add-floating-task)**

#### Extensions

1a. End date/time is in the wrong format
> 1a1. TaskBook shows an error message <br>
  **Use case ends**

- [Add Event](#add-event) occurs after step 1.

### Add Event

This use case extends [Add Deadline Task](#add-deadline-task). It is inserted at extension point Add Event.

#### MSS

1. User enters parameters for start date/time <br>
**Use case continues after step 1 of [Add Deadline Task](#add-deadline-task)**

#### Extensions

1a. Start date/time is in the wrong format
> 1a1. TaskBook shows an error message <br>
  **Use case ends**

### List Tasks

#### MSS

1. TaskBook shows the list of tasks <br>
**Use case ends**

- [List all Tasks](#list-all-tasks) occurs before step 1
- [Find Tasks](#find-tasks) occurs before step 1

### List all Tasks

This use case extends [List all Tasks](#list-all-tasks). It is inserted at extension point List all tasks.

#### MSS

1. User request to list all tasks
2. TaskBook retrieves all tasks <br>
**Use Case continues in step 1 of [List Tasks](#list-tasks)**

#### Extensions

2a. There are no tasks
> 2a1. TaskBook shows a notice message <br>
  **Use case ends**

### Find Tasks

This use case extends [List all Tasks](#list-all-tasks). It is inserted at extension point Find Tasks.

#### MSS

1. User requests to find tasks by keyword
2. TaskBook shows a list of tasks with keywords that match the exact keyword <br>
**Use Case continues in step 1 of [List Tasks](#list-tasks)**

#### Extensions

2a. No tasks match the specified keywords
> 2a1. TaskBook shows a notice message <br>
  **Use case ends**

### Edit Task

#### MSS

1. User retrieves list of tasks [via Use Case: List Tasks](#list-tasks)
2. User enters command to edit task
3. TaskBook edits task according to given parameters <br>
**Use case ends**

#### Extensions

2a. The given index is invalid
> 2a1. TaskBook shows an error message <br>
  **Use case resumes at step 2**

2b. The given parameters are invalid

 > 2b1. TaskBook shows an error message <br>
  **Use case resumes at step 2**

### Mark Task as Completed

1. User retrieves list of tasks [via Use Case: List Tasks](#list-tasks)
2. User enters command to mark task as completed
3. TaskBook marks specified task as completed <br>
**Use case ends**

#### Extensions

2a. The given index is invalid
> 2a1. TaskBook shows an error message <br>
  **Use case resumes at step 2**

### Delete Task

#### MSS

1. User retrieves list of tasks [via Use Case: List Tasks](#list-tasks)
2. User enters command to delete task
3. TaskBook deletes specified task <br>
**Use case ends**

#### Extensions

2a. The given index is invalid
> 2a1. TaskBook shows an error message <br>
  **Use case resumes at step 2**

### Undo Action

#### MSS

1. User requests to undo the previous action
2. TaskBook undos the last action
3. TaskBook shows the reflected changes <br>
**Use case ends**

#### Extensions

2a. There exists no valid action that can be undone
> 2a1. TaskBook shows a notice message <br>
  **Use case ends**

### Redo Action

#### MSS

1. User requests to redo the previous undo
2. TaskBook redos the previous undo
3. TaskBook shows the reflected changes <br>
**Use case ends**

#### Extensions

2a. There exists no valid undo action that can be redone
> 2a1. TaskBook shows a notice message <br>
  **Use case ends**

### Save to File

1. User enters command to save progress
2. Task manager saves progress in the directory<br>
**Use case ends**

### Change File storage

#### MSS

1. User enters command to retrieve file saving directory
2. User switches directory
2. All data is then relocated to the specified location<br>
**Use case ends**

#### Extensions

1a. At first startup, an options message will display available directories for the files to be saved to
> 1a1. Save to desktop by default in the options<br>
  **Use case ends**
