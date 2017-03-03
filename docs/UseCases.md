# Use Cases

By : `CS2103JAN2017-W15-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

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
2. Taskbook enters task into system
**Use case ends**

<<<<<<< HEAD
**Extensions**

[Add Deadline Task](#add-deadline-task) occurs after step 1.
=======
#### Extensions

- [Add Deadline Task](#add-deadline-task) occurs after step 1.
>>>>>>> use-cases

### Add Deadline Task
This use case extends [Add Floating task](#add-floating-task). It is inserted at extension point Add Deadline Task.

#### MSS

1. User enters parameters for end date/time
**Use case continues in step 2 of [Add Floating Task](#add-floating-task)**

#### Extensions

<<<<<<< HEAD
**Extensions**

=======
>>>>>>> use-cases
1a. End date/time is in the wrong format
> 1a1. TaskBook shows an error message
  **Use case ends**

- [Add Event](#add-event) occurs after step 1.

### Add Event

This use case extends [Add Deadline Task](#add-deadline-task). It is inserted at extension point Add Event.

#### MSS

1. User enters parameters for start date/time
**Use case continues after step 1 of [Add Deadline Task](#add-deadline-task)**

#### Extensions

<<<<<<< HEAD
**Extensions**

=======
>>>>>>> use-cases
1a. Start date/time is in the wrong format
> 1a1. TaskBook shows an error message
  **Use case ends**

### List Tasks

#### MSS

1. TaskBook shows the list of tasks
**Use case ends**

- [List all Tasks](#list-all-tasks) occurs before step 1
- [Find Tasks](#find-tasks) occurs before step 1

### List all Tasks

This use case extends [List all Tasks](#list-all-tasks). It is inserted at extension point List all tasks.

#### MSS

<<<<<<< HEAD
**Extensions**

2a. The list is empty
> 2a1. TaskBook shows a notice message <br>
  Use case ends
=======
1. User request to list all tasks
2. TaskBook retrieves all tasks
**Use Case continues in step 1 of [List Tasks](#list-tasks)**

#### Extensions

2a. There are no tasks
> 2a1. TaskBook shows a notice message
  **Use case ends**

### Find Tasks

This use case extends [List all Tasks](#list-all-tasks). It is inserted at extension point Find Tasks.

#### MSS

1. User requests to find tasks by keyword
2. TaskBook shows a list of tasks with keywords that match the exact keyword
**Use Case continues in step 1 of [List Tasks](#list-tasks)**
>>>>>>> use-cases

#### Extensions

2a. No tasks match the specified keywords
> 2a1. TaskBook shows a notice message
  **Use case ends**

### Edit Task

#### MSS

1. User retrieves list of tasks [via Use Case: List Tasks](#list-tasks)
2. User enters command to edit task
3. TaskBook edits task according to given parameters
**Use case ends**

#### Extensions

2a. The given index is invalid
> 2a1. TaskBook shows an error message
  **Use case resumes at step 2**
2b. The given parameters are invalid

 > 2b1. TaskBook shows an error message
  **Use case resumes at step 2**

### Mark Task as Completed

1. User retrieves list of tasks [via Use Case: List Tasks](#list-tasks)
2. User enters command to mark task as completed
3. TaskBook marks specified task as completed
**Use case ends**

#### Extensions

2a. The given index is invalid
> 2a1. TaskBook shows an error message
  **Use case resumes at step 2**

### Delete Task

#### MSS

1. User retrieves list of tasks [via Use Case: List Tasks](#list-tasks)
2. User enters command to delete task
3. TaskBook deletes specified task
**Use case ends**

#### Extensions

2a. The given index is invalid
<<<<<<< HEAD

> 2a1. TaskBook shows an error message <br>
  Use case resumes at step 2

### Find task

**MSS**

1. User requests to find tasks by keyword
2. TaskBook shows a list of tasks with keywords that match the exact keyword <br>
Use case ends

**Extensions**

2a. The list is empty
> 2a1. TaskBook shows a notice message <br>
  Use case ends
=======
> 2a1. TaskBook shows an error message
  **Use case resumes at step 2**
>>>>>>> use-cases

### Undo Action

#### MSS

1. User requests to undo the previous action
2. TaskBook undos the last action
3. TaskBook shows the reflected changes
**Use case ends**

#### Extensions

<<<<<<< HEAD
**Extensions**

=======
>>>>>>> use-cases
2a. There exists no valid action that can be undone
> 2a1. TaskBook shows a notice message
  **Use case ends**

### Redo Action

#### MSS

1. User requests to redo the previous undo
2. TaskBook redos the previous undo
3. TaskBook shows the reflected changes
**Use case ends**

#### Extensions

<<<<<<< HEAD
**Extensions**

=======
>>>>>>> use-cases
2a. There exists no valid undo action that can be redone
> 2a1. TaskBook shows a notice message
  **Use case ends**

### Save to File

### Change File storage
