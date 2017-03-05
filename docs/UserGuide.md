# User Guide

* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [UI Control](#ui-control)
* [Features](#features)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Introduction
In the hectic world of today, people are constantly bombarded by innumerable tasks at hand. As a result, people tend to lose track of their work progress and deviate from their priorities. Problems like these are far too common among working adults and students embarking on tight projects. Thus, our team would like to present to you a solution that could curb all your management problem! 

Presenting to you, iManager.

iManager is a task manager application that helps you manage all your tasks at one place. With its interactive UI, user can now experience managing their tasks like never before.  Our product incorporates niche features such as FlexiCommands and Google Integration that brings convenient to user in managing their schedules. iManager remove the need to rely on multiple applications as it manages it all! 

Without further ado, let’s get started. 

   
## Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `iManager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your iManager application.
3. Double-click the file to start the app. The GUI should appear in a few seconds. Refer to [UI Control](#ui-control) for a more detailed walkthrough of various UI components. 
   > <img src="images/UIOverview.png" width="800">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`add`**: `SunRise Marathon` adds a reminder to attend SunRise Marathon
   * **`find`** `Today` searches the task list for all events happening today. 
   * **`exit`**: exits the application
6. Refer to the[Features](#features) section below for details of each command.
   

## UI Control

### Task Status     
The status of a task can be classified as one of the following: 

|  Avatar | Status  
|  :---:  | :---:  
| <img src="images/Floating.png" width="50"> | Floating 
| <img src="images/Completed.png" width="50"> | Complete 
| <img src="images/Pending.png" width="50"> | Pending 
| <img src="images/Overdue.png" width="50"> | Overdue 


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Words in `lower_case` are commands and connectors.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.

### Viewing help

Shows a list of commands available in iManager. <br>

Format: `help`
> A list of commands available is also shown if you enter the wrong command.

Examples: 

* `help` <br>
  Shows all available commands with examples.

#### Viewing a specific commands
Show details of a specific command using `[KEYWORD]` flag.

List of available [KEY_WORD] for help: <br>
- add <br>
- edit <br>
- delete <br>
- view <br>
- done <br>
- find <br>
- clear <br>
- undo <br>
- redo <br>
- reset <br>
- saveto <br>
- exit <br>

Format: `help [KEY_WORD]`

Examples:

* `help add` <br>
  Shows add command with examples.
  
### Adding a task

#### Adding a floating task
Adds a floating task to the task list. <br>

Format: `add NAME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be added to the bottom of the list.

Examples: 
* `add exam`
* `add exam p/1`
* `add exam p/1 d/SR1`
* `add exam p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 task named "exam" with description "SR1" and tag "CS2010" into task list.

#### Adding a pending task with START DATE only

Adds a pending task with only start date to the task list. <br>

Format: `add NAME at START_DATE [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be added to the bottom of the list.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no time is specified, the time will be the CURRENT time.

Examples:
* `add exam on 10-22-2017`
* `add exam on 10-22-2017 1500`
* `add exam on 10-22-2017 1500 p/1`
* `add exam on 10-22-2017 1500 p/1 d/SR1`
* `add exam on 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 task named "exam" on 22 October 2017 with description "SR1" and tag "CS2010" into task list.
 
 
#### Adding a pending task with END DATE only

Adds a pending task with only end date to the task list. <br>

Format: `add NAME by END_DATE [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be added to the bottom of the list.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * The Start Date is the CURRENT date and time.

Examples: 
* `add exam by 10-22-2017`
* `add exam by 10-22-2017 1500`
* `add exam by-22-2017 1500 p/1`
* `add exam by 10-22-2017 1500 p/1 d/SR1`
* `add exam by 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 task named "exam" that starts now and end by 22 October 2017 1500  with description "SR1" and tag "CS2010" into task   list.
  
  
#### Adding a pending task with both START DATE & END DATE 

Adds a pending task with both start date and end date to the task list. <br>

Format: `add NAME by END_DATE [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be added to the bottom of the list.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no time is specified, the time will be the CURRENT time.

Examples:
* `add exam on 10-22-2017 by 10-22-2016`
* `add exam on 10-22-2017 1300 by 10-22-2016 1500`
* `add exam on 10-22-2017 by-22-2016 1500 p/1`
* `add exam on 10-22-2017 by 10-22-2016 1500 p/1 d/SR1`
* `add exam on 10-22-2017 by 10-22-2016 1500 p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 task named "exam" that starts on 22 October 2017 1300 and end by 22 October 2017 1500 with description "SR1" and tag   "CS2010" into task list.
  
  
### Editing a task

Edit a task in iManager<br>

Format: `edit`<br>

Examples:
* 
* 

### Deleting a task

Delete a task from iManager<br>

Format: `delete`<br>

Examples: 
* 
* 

### Viewing tasks
Shows a list of all tasks iManage. Able to filter based on status of task/event.

Format: `view`<br>

Examples: 
* 
* 

### Marking a task as completed
Mark an existing task as completed in iManager.

Format: `done`

Examples: 
* 
* 
 
### Finding for tasks
With the find command, you can find for tasks or events which contain some keywords in their **name** as well as in their **descriptions**. 

Format: `find `

Examples:
* 
*  
  
### Clearing of tasks
Clears all completed tasks. <br>

Format: `clear`

Examples: 
* 
* 

### Toggle google calender
Toggle the view of google calendar and focus to certain time time as user specified. 

Format: 

Examples:
* 
* 

### Undo most recent command
You can go back to historical versions of iManager with the use of undo commands. Only commands that modify iManager in the same session will be restored. Any versions of current session will not be accessible after restarting iManager.  

Format: `undo`

### Redo most recent command
You can go advance to historical versions of Dowat with the use of redo commands. Only commands that modify Dowat in the same session will be restored. Any versions of current session will not be accessible after restarting Dowat.  

Format: `redo`

### reset data
iManager will save data in a file called iManager.txt in the project root folder by dafault or if the file path is not specified. 
You can change the location by specifying the file path as a command argument.
New file will be automatically created as long as given directory is valid.

Format: `reset`

### Changing the save location
iManager will save data in a file called iManager.txt in the project root folder by dafault or if the file path is not specified. 
You can change the location by specifying the file path as a command argument.
New file will be automatically created as long as given directory is valid.

Format: `saveto FILEPATH`

> FILEPATH (must be valid)

Examples:
* `saveto C:\Users\Computing\Desktop\CS2103` 

### Exiting the program:`exit`
Format : `exit`



## FAQ

**Q**: <br>
**A**: <br>


## Command Summary

 Command | Format  | Description
-------- | -------- | -------- 
[help](#viewing-help) | `help`| Opens a help page
[add](#adding-a-task) | `add NAME [PRIORITY] [/t TAG1, TAG2…]`| Adds a floating task with priority and tags
 | `add NAME at START_DATETIME [PRIORITY] [/t TAG1, TAG2…]`| Adds a pending task with only start time, priority and tags
 | `add NAME by END_DATETIME [PRIORITY] [/t TAG1, TAG2…]`| Adds a pending task with only deadline, priority and tags
 | `add NAME by END_DATETIME [PRIORITY] [/t TAG1, TAG2…]`| Adds a pending task with start time, end time, priority and tags
 | `add [INSTANCES] NAME [at|by|from START_DATETIME] [to END_DATETIME][PRIORITY] every [DAY|MONTH|YEAR] [/t TAG1, TAG2…]` | Adds a recurring number of task instances that spans over a period of time with priority and tags
[edit](#editing-a-task) | `Edit INDEX|NAME [at|by|from START_DATETIME] [to END_DATETIME][PRIORITY] every [DAY|MONTH|YEAR] [/t TAG1, TAG2…]` | Edits a task with the new parameters
[delete](#deleting-a-task) | `Delete INDEX|NAME` | Delete a task 
[view](#viewing-tasks) | `view` | Show all tasks
 | `view t` | Show all today’s tasks
 | `view p` |Show all pending task
 | `view c` | Show all completed task
 | `view f` | Show all floating task
 | `view o` | Show all overdue task
 | `view INDEX|NAME|START_DATETIME|END_DATETIME|PRIORITY|DAY|MONTH|YEAR TAG` | Show a specified task based on its detail 
[done](#marking-a-task-as-completed) | `done INDEX|NAME`| Mark a task as done
[find](#finding-for-tasks) | `Find KEYWORDS [KEYWORD 1, KEYWORD2,..]`| Find the task associated to the keyword
[clear](#clearing-of-tasks) | `clear` | Clear all completed tasks
[undo](#undo-most-recent-command) | `undo` | Undo the most recent command
[redo](#redo-most-recent-command) | `redo` | Redo the most recent undo
[reset](#reset-data) | `reset` | Reset save data
[saveto](#changing-the-save-location) | `saveto PATH` | Change the default save directory 
[exit](#exiting-the-program) | `exit` | Exit the application



