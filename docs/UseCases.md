## Use Cases

(For all use cases below, the **System** is `Opus` and the **Actor** is the `user`, unless specified otherwise)

#### Use case: Display help information

**MSS**

1. User requests to display help information
2. Opus displays help information

Use case ends

#### Use case: Add task

**MSS**

1. User input command to add task
2. Opus adds the task and shows the added task details

Use case ends

**Extensions**

1a. The input format is invalid

> 1a1. Opus shows an error message

Use case ends

#### Use case: Delete task

**MSS**

1. User requests to delete a specific task in the list
2. Opus deletes the task

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

Use case ends

#### Use case: Edit task

**MSS**

1. User requests to edit a specific task in the list
2. Opus edits the task

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

Use case ends

1b. The attribute given is invalid

> 3b1. Opus shows an error message

Use case ends

1c. The value of the attribute is invalid

> 1c1. Opus shows an error message

Use case ends

3d. The value of the attribute is the same as the previous value

>Use case ends


#### Use case: Mark task as complete

**MSS**

1. User requests to mark a specific task in the list as complete
2. Opus marks the task as complete

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

Use case ends

#### Use case: Undo the previous action

**MSS**

1. User requests to undo the previous action
2. Opus shows which action is undone

Use case ends

**Extensions**

1a. The history stack is empty

> Use case ends

#### Use case: Redo the previous undo action

**MSS**

1. User requests redo the previous undo action
2. Opus shows which action is restored

Use case ends

**Extensions**

1a. The restoration history stack is empty

> Use case ends

#### Use case: Find for specific tasks using the attributes

**MSS**

1. User requests to find tasks based on the attributes
2. Opus shows a list of tasks

Use case ends

**Extensions**

1a. The list is empty

> Use case ends

1b. The input bttribute is invalid

> 1b1. Opus shows an error message

Use case ends

#### Use case: Assign a tag to a task

**MSS**

1. User requests to assign a tag to the task
2. Opus shows the tag is assigned to the task

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

Use case ends

1b. The tag input by user is invalid

> 1b1. Opus shows an error message

Use case ends

#### Use case: Add a note to a task

**MSS**

1. User requests to add a note to the task
2. Opus shows the note is added to the task

Use case ends

**Extensions**

1a. The input index is invalid

> 1a1. Opus shows an error message

Use case ends
