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
Show details of a specific command using `[COMMAND]` flag.

List of available [COMMAND] for help: <br>
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

Format: `help [COMMAND]`

Examples:

* `help add` <br>
  Shows add command with examples.
  
### Adding a task

#### Adding a floating task
Adds a floating task to the task list. <br>

Format: `add NAME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.

Examples: 
* `add exam`
* `add exam p/1`
* `add exam p/1 d/SR1`
* `add exam p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 floating task named "exam" with description "SR1" and tag "CS2010" into task list.

#### Adding a pending task with START DATE & TIME only

Adds a pending task with only start date & time to the task list. <br>

Format: `add NAME on START_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no start time (i.e HHMM) is specified, the time will be the CURRENT time.

Examples:
* `add exam on 10-22-2017`
* `add exam on 10-22-2017 1500`
* `add exam on 10-22-2017 1500 p/1`
* `add exam on 10-22-2017 1500 p/1 d/SR1`
* `add exam on 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 pending task named "exam" on 22 October 2017 with description "SR1" and tag "CS2010" into task list.
 
 
#### Adding a pending task with END DATE & TIME only

Adds a pending task with only end date & time to the task list. <br>

Format: `add NAME by END_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no end time (i.e HHMM) is specified, the time will be 2359.
> * The START_DATETIME is the CURRENT date and time by default.

Examples: 
* `add exam by 10-22-2017`
* `add exam by 10-22-2017 1500`
* `add exam by 10-22-2017 1500 p/1`
* `add exam by 10-22-2017 1500 p/1 d/SR1`
* `add exam by 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 pending task named "exam" that starts now and end by 22 October 2017 1500 with description "SR1" and tag "CS2010" 
  into task list.
  
  
#### Adding a pending task with both START DATE & TIME + END DATE & TIME 

Adds a pending task with both start date & time and end date & time to the task list. <br>

Format: `add NAME from START_DATETIME to END_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no start time (i.e HHMM) is specified, the time will be the CURRENT time.
> * If no end time (i.e HHMM) is specified, the time will 2359.

Examples:
* `add exam from 10-22-2017 to 10-22-2016`
* `add exam from 10-22-2017 1300 to 10-22-2016 1500`
* `add exam from 10-22-2017 1300 to 10-22-2016 1500 p/1`
* `add exam from 10-22-2017 1300 to 10-22-2016 1500 p/1 d/SR1`
* `add exam from 10-22-2017 1300 to 10-22-2016 1500 p/1 d/SR1 t/CS2010`<br>
  Add a priority 1 pending task named "exam" that starts from 22 October 2017 1300 to 22 October 2017 1500 with description "SR1" and 
  tag "CS2010" into task list.
  
#### Adding a recurring task 

Adds a recurring task that spans over a period of time. <br>

Format: `add NAME [at|on|by|from START_DATETIME to END_DATETIME] every [DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no start time (i.e HHMM) is specified, the time will be the CURRENT time. 
> * If no end time (i.e HHMM) is specified, the time will 2359. 
> * Any form of abbreviation can be use for WEEK (e.g Tue, Tues, Tuesday), case insensitve. 
> * No abbreviation can be use for DAY, MONTH and YEAR
> * Month (i.e MM) is ignored for every MONTH.
> * It is suffice to use time for every DAY and every WEEK. Date (i.e MM-DD-YYYY) will be ignored.
> * One must include START_DATETIME and END_DATETIME for every MONTH and every YEAR. 

Examples:
* `add exam every day p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at the current time everyday with description "SR1" and tag "CS2010" into task 
   list.
   
* `add exam at 1300 every day p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at 1300 everyday with description "SR1" and tag "CS2010" into task list.
   
* `add exam by 1500 every day p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at the current time and end by 1500 everyday with description "SR1" and tag 
   "CS2010" into task list.
   
* `add exam from 1300 to 1500 every day p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts from 1300 to 1500 everyday with description "SR1" and tag "CS2010" into task 
   list.
   
   
   
* `add exam every Tue p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at the current time every Tuesday with description "SR1" and tag "CS2010" into 
   task list.
   
* `add exam at 1300 every Tue p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at 1300 every Tuesday with description "SR1" and tag "CS2010" into task list.
   
* `add exam by 1500 every Tue p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at the current time and end by 1500 every Tuesday with description "SR1" and 
   tag "CS2010" into task list.
   
* `add exam from 1300 to 1500 every Tue p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts from 1300 to 1500 every Tuesday with description "SR1" and tag "CS2010" into 
   task list.



* `add exam on 10-22-2017 every month p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on the 22nd of every month, current time, with description "SR1" and tag 
   "CS2010" into task list.
   
* `add exam on 10-22-2017 1300 every month p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on 22nd of every month 1300 with description "SR1" and tag "CS2010" into task 
   list.
   
* `add exam from 10-22-2017 to 10-22-2017 every month p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts from the 22nd of every month, current time, to 22nd of every month 2359 with
   description "SR1" and tag "CS2010" into task list.
   
* `add exam from 10-22-2017 1300 to 10-22-2017 1500 every month p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts from the 22nd of every month 1300 to 22nd of every month 1500 with description 
   "SR1" and tag "CS2010" into task list.
   
   
   
* `add exam on 10-22-2017 every year p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on the 22nd October of every year, current time,  with description "SR1" and 
   tag "CS2010" into task list.
   
* `add exam on 10-22-2017 1300 every year p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on the 22nd October of every year 1300 with description "SR1" and tag "CS2010" 
   into task list.
   
* `add exam from 10-22-2017 to 10-22-2017 every year p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on the 22nd October of every year, current time, to 22nd October of every year 
   2359 with description "SR1" and tag "CS2010" into task list.
   
* `add exam from 10-22-2017 1300 to 10-22-2017 1500 every year p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on the 22nd October of every year 1300 to 22nd October of every year 1500 with 
   description "SR1" and tag "CS2010" into task list.

### Editing a task

Edit a task in iManager by index or name<br>

Format: `Edit INDEX|NAME [at|on|by|from START_DATETIME to END_DATETIME] [every DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION][t/TAG...]`<br>
> * INDEX refers to the index number shown in the most recent listing of tasks. 
> * INDEX must be positive integer (e.g 1, 2, 3)
> * All tags of an existing task can be remove by using `t/none`. Case insensitive. 
> * Editing `t/TAG` appends to the existing tags of a task 
> * Editing the same tag name remove the tag itself. 
   (e.g if task contains tag [friends], edit [/t friends] would remove the 'friends' tag)

Examples:

* `Edit 1 on 10-22-2017 1500 p/1 d/SR1`<br>
   Edit the first task in the task list such that it is a priority 1 pending task named "exam" on 22 October 2017 with description 
   "SR1".
* `Edit exam on 10-22-2017 1500 p/1 d/SR1`<br>
   Edit a task named "exam" in the task list such that it is a priority 1 pending task named "exam" on 22 October 2017 with description 
   "SR1".
* `Edit exam on 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
   Edit a task named "exam" in the task list such that it is a priority 1 pending task named "exam" on 22 October 2017 with description 
   "SR1" and append tag "CS2010" to "exam".
* `Edit exam on 10-22-2017 1500 p/1 d/SR1 t/none`<br>
   Edit a task named "exam" in the task list such that it is a priority 1 pending task named "exam" on 22 October 2017 with description 
   "SR1" and remove all tags from "exam".
* `Edit exam from 10-22-2017 1300 to 10-22-2017 1500 every year p/1 d/SR1 t/CS2010`<br>
   Edit the first task in the task list such that it start on the 22nd October of every year 1300 to 22nd October of every year 1500 
   with description "SR1" and append tag "CS2010" to "exam".

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
Mark an existing task as completed in iManager. A completed task gets transferred to the done list. 

Format: `done INDEX|NAME`
> * INDEX refers to the index number shown in the most recent listing of tasks.
> * INDEX must be positive integer (e.g 1, 2, 3)
> * Marking an uncompleted task will change its status to completed while marking a completed task will change its status to uncompleted. 
> * Marked tasks in the current session can be reverted with the `undo` command.

Examples: 
* `done 1`
   Mark the first task in the task list as completed.
* `done exam`
   Mark a task named "exam" in the task list as completed.
 
### Finding for tasks
With the find command, you can find for tasks or events which contain some keywords in their **name** as well as in their **descriptions**. 

Format: `find `

Examples:
* 
*  
  
### Clearing of completed tasks
Clears all completed tasks. <br>

Format: `clear`

> * Cleared tasks in the current session can be restored with the `undo` command.

### Toggle google calender
Toggle the view of Google Calendar. The tasks in iManager will be synchronised with your Google Calendar. You cannot make any modification through the calendar.

Format: `toggle`

### Undo most recent command
Undo the most recent command that was successfully executed. Only commands that modify iManager in the same session can be undone. Command history will be cleared once iManager exits. 

Format: `undo`

> * `undo` only support add, edit, delete, done, clear and redo command. 

### Redo most recent command
Redo the most recent undo.  Only commands that modify iManager in the same session can be redo. Command history will be cleared once iManager exits. 

Format: `redo`

> * `redo` only support add, edit, delete, done, clear and undo command. 

### Reset data
Reset the applcation data. iManager can completely erase the saved data and start with a "clean" state. Application data will be save in the default save directory. (under project root folder) Once application data gets resetted, it CANNOT be restored. 

Format: `reset`

### Save
#### Saving the data
The application data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

#### Changing the save location
Change the save directory. The application data is saved in a file called iManager.txt under the project root folder by dafault or if the file path is not specified. You can change the location by specifying the file path as a command argument. New file will be automatically created as long as given directory is valid.

Format: `saveto FILEPATH`

> * FILEPATH must be valid
> * `cd` will tell you the current location of the save data. 

Examples:
* `saveto C:\Users\Computing\Desktop\CS2103`

### Exiting the program:`exit`
Exits the application.

Format : `exit`

## FAQ

**Q**: <br>
**A**: <br>


## Command Summary

 Command | Format  | Description
:--------: | -------- | -------- 
[help](#viewing-help) | `help`| Opens a help page.
 | `help [COMMAND]`| Help with specific command. 
[add](#adding-a-task) | `add NAME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]`| Adds a floating task with priority and tags.
 | `add NAME at START_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]`| Adds a pending task with only start date&time, priority and tags.
 | `add NAME by END_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]`| Adds a pending task with only end date&time, priority and tags.
 | `add NAME from END_DATETIME to START_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]`| Adds a pending task with start date&time, end date&time, priority and tags.
 | `add NAME [at|on|by|from START_DATETIME to END_DATETIME] every [DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Adds a recurring task that spans over a period of time with priority and tags.
[edit](#editing-a-task) | `Edit INDEX|NAME [at|on|by|from START_DATETIME to END_DATETIME] [every DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Edits a task with the new parameters.
[delete](#deleting-a-task) | `Delete INDEX|NAME` | Delete a task 
[view](#viewing-tasks) | `view` | Show all tasks
 | `view t` | Show all today’s tasks
 | `view p` |Show all pending task
 | `view c` | Show all completed task
 | `view f` | Show all floating task
 | `view o` | Show all overdue task
 | `view INDEX|NAME|START_DATETIME|END_DATETIME|PRIORITY|DAY|MONTH|YEAR TAG` | Show a specified task based on its detail.
[done](#marking-a-task-as-completed) | `done INDEX|NAME`| Mark a task as completed.
[find](#finding-for-tasks) | `Find KEYWORDS [KEYWORD 1, KEYWORD2,..]`| Find the task associated to the keyword.
[clear](#clearing-of-tasks) | `clear` | Clear all completed tasks.
[toggle](#clearing-of-tasks) | `toggle` | Toggle Google Calender.
[undo](#undo-most-recent-command) | `undo` | Undo the most recent command.
[redo](#redo-most-recent-command) | `redo` | Redo the most recent undo.
[reset](#reset-data) | `reset` | Reset saved data.
[saveto](#changing-the-save-location) | `saveto PATH` | Change the default save directory.
 | `cd` | Show current save directory.
[exit](#exiting-the-program) | `exit` | Exit the application.
