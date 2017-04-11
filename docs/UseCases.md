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

> 1a1. Opus shows an error message<br>
> 1a2. Use case ends

#### Use case: Delete task

**MSS**

1. User requests to delete a specific task in the list
2. Opus deletes the task

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message<br>
> 1a2. Use case ends

#### Use case: Edit task

**MSS**

1. User requests to edit a specific task in the list
2. Opus edits the task

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message<br>
> 1a2. Use case ends

1b. The attribute given is invalid

> 1b1. Opus shows an error message<br>
> 1b2. Use case ends

1c. The value of the attribute is invalid

> 1c1. Opus shows an error message<br>
> 1c2. Use case ends

#### Use case: Schedule tasks

**MSS**

1. User requests to schedule tasks based on dates entered
2. Opus schedules the task and shows the scheduled task details

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

> Use case ends

1b. The value of the attribute is invalid

> 1b1. Opus shows an error message

> Use case ends

#### Use case: Mark a task as complete

**MSS**

1. User requests to mark task as complete
2. Opus displays request to mark task as complete

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message<br>
> 1a2. Use case ends

1b. The task is already complete

> 1b1. Opus marks task as incomplete and shows message

> Use case ends

#### Use case: Undo the previous action

**MSS**

1. User requests to undo the previous action
2. Opus shows which action is undone

Use case ends

**Extensions**

1a. There is no more actions that can be undone

> 1a1. System shows an error that an undo command is not possible<br>
> 1a2. Use case ends

#### Use case: Redo the previous undo action

**MSS**

1. User requests redo the previous undo action
2. Opus shows which action is restored

Use case ends

#### Use case: Find specific tasks using different attributes

**MSS**

1. User requests to find tasks based on keywords and/or attributes
2. Opus shows a list of tasks

Use case ends

**Extensions**

1a. The list is empty

> 1a1. Opus shows an error message<br>
> 1a2. Use case ends

1b. The input attribute is invalid

> 1b1. Opus shows an error message<br>
> 1b2. Use case ends

#### Use case: Sort tasks

**MSS**

1. User requests to sort tasks based on parameters entered
2. Opus sorts the tasks list and and result is shown on the GUI to the user

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

> Use case ends

1b. The given attribute is invalid

> 1b1. Opus shows an error message

> Use case ends

#### Use case: Save directory

**MSS**

1. User requests to change save directory
2. Opus changes the default save directory and subsequent changes will be saved to the new file specified

Use case ends

**Extensions**

1a. The given file path is invalid

> 1a1. Opus shows an error message

> Use case ends

1b. The given file path is already in use

> 1b1. Opus shows an error message

> Use case ends

#### Use case: Sync tasks to Google Tasks

**MSS**

1. User requests to sync tasks to Google Tasks
2. Opus connects to Google Tasks and pushes all valid tasks.
3. User changes data in Opus
4. Opus pushes any new changes to Google Task
5. User exits Opus

Use case ends

**Extensions**

1a. No authorisation to access User's Google Tasks<br>
> 1a1. Opus attempts to connect to Google Tasks and prompts for authorisation to access User's Google Tasks account in default web browser<br>
> 1a2. User accepts request for authorisation

1a1a. No internet connection<br>
> 1a1a1. Opus shows an error message that there is no internet access to Google Tasks<br>
> 1a1a2. Use case ends

1a2a. User denies request for authorisation<br>
> 1a2a1. Opus shows an error message that no authorisation is given by User<br>
> 1a2a2. Use case ends

2a. User revoked Opus access in Google Tasks<br>
> 2a1. Opus shows an error message that no authorisation is given by User <br>
> 2a2. Use case ends

4a. No internet connection<br>
> 4a1. Opus shows an error message that there is no internet access to Google Tasks<br>
> 4a2. Use case ends

#### Use case: Autocompletion

**MSS**

1. User types in first few letters of a command and press Tab
2. Opus displays the completed command in the CommandBox

Use case ends

**Extensions**

1a. There are no matching commands

> 1a1. Nothing happens

1b. There are multiple matching commands

> 1b1. User tabs multiple times to cycle through commands

