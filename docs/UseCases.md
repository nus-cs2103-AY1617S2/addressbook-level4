# Use Cases

By : `CS2103JAN2017-W15-B2`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `Mar 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

- [Use Case Diagram](#use-case-diagram)
- [Use Cases](#use-cases-1)
    - [Add Floating Task](#add-floating-task)
    - [Add Deadline Task](#add-deadline-task)
    - [Add Event](#add-event)
    - [List all Tasks](#list-all-tasks)
    - [Edit Task](#edit-task)
    - [Mark Tasks as Completed](#mark-tasks-as-completed)
    - [Delete Task](#delete-task)
    - [Find Task](#find-task)
    - [Undo Action](#undo-action)
    - [Redo Action](#redo-action)
    - [Save to File](#save-to-file)
    - [Change File storage](#change-file-storage)

## Use Case Diagram

![Use Case Diagram](diagrams/Use_Case_Diagram.png)

## Use Cases

### List all tasks

**MSS**

1. User requests to list tasks
2. TaskBook shows a list of tasks <br>
Use case ends

**Extensions**
2a. The list is empty
> 2a1. TaskBook shows a notice message <br>
  Use case ends

### Edit task

**MSS**

1. User requests to list tasks
2. TaskBook shows a list of tasks
3. User requests to edit a specific task in the list
4. TaskBook edits the task <br>
Use case ends

**Extensions**

2a. The list is empty

> Use case ends

3a. The given index is invalid

> 3a1. TaskBook shows an error message <br>
  Use case resumes at step 2

3b. The given edits are invalid

 > 3b1. TaskBook shows an error message <br>
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
  
### Undo Action

**MSS**

1. User requests to undo the previous action
2. TaskBook undos the last action  
3. TaskBook shows the reflected changes <br>
Use case ends

**Extensions**
2a. There exists no valid action that can be undone
> 2a1. TaskBook shows a notice message <br>
  Use case ends 
  
### Redo Action

**MSS**

1. User requests to redo the previous undo 
2. TaskBook redos the previous undo 
3. TaskBook shows the reflected changes <br>
Use case ends

**Extensions**
2a. There exists no valid undo action that can be redone
> 2a1. TaskBook shows a notice message <br>
  Use case ends