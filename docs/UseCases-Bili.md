#### Use case: Mark a task as complete

**MSS**

1. User requests to mark task as complete
2. Opus displays request to mark task as complete

Use case ends

**Extensions**

1a. The given index is invalid

> 1a1. Opus shows an error message

> Use case ends

1b. The task is already complete

> 1b1. Opus marks task as incomplete and shows message

> Use case ends


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

