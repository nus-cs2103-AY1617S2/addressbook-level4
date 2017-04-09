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
