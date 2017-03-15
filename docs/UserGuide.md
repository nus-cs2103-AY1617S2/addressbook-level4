# User Guide
* [Introduction](#introduction)
* [Quick Start](#quick-start)
* [Features](#features)
  * [Viewing help](#viewing-help)
    * [Viewing a all command](#viewing-all-commands)
    * [Viewing a specific command](#viewing-a-specific-command)
  * [Adding a task](#adding-a-task)
    * [Adding a floating task](#adding-a-floating-task)
    * [Adding a pending task](#adding-a-pending-task)
       * [Adding a pending task with START DATE & TIME only](#adding-a-pending-task-with-start-date--time-only)
       * [Adding a pending task with END DATE & TIME only](#adding-a-pending-task-with-end-date--time-only)
       * [Adding a pending task with both START DATE & TIME and END DATE & TIME](#adding-a-pending-task-with-both-start-date--time-and-end-date--time)
   * [Adding a recurring task](#adding-a-recurring-task)
   * [Editing a task](#editing-a-task)
   * [Deleting a task](#deleting-a-task)
   * [Viewing tasks](#viewing-tasks)
     * [Viewing all tasks](#viewing-all-tasks)
     * [Viewing all Today's tasks](#viewing-all-todays-tasks)
     * [Viewing all pending tasks](#viewing-all-pending-tasks)
     * [Viewing all "done" tasks](#viewing-all-done-tasks)
     * [Viewing all floating tasks](#viewing-all-floating-tasks)
     * [Viewing all overdue tasks](#viewing-all-overdue-tasks)
     * [Viewing a specified task](#viewing-a-specified-task)
   * [Marking a task as "done"](#marking-a-task-as-done)
   * [Finding for tasks](#finding-for-tasks)
   * [Clearing of "done" tasks](#clearing-of-done-tasks)
   * [Toggle Google Calendar](#toggle-google-calendar)
   * [Undo most recent command](#undo-most-recent-command)
   * [Redo most recent undo](#redo-most-recent-undo)
   * [Reset saved data](#reset-saved-data)
   * [Save](#saving-the-data)
     * [Changing the save location](#changing-the-save-location)
   * [Exiting the program](#exiting-the-application)
* [FAQ](#faq)
* [Command Summary](#command-summary)

## Introduction
In today's hectic world, people are constantly bombarded by innumerable tasks at hand. As a result, people tend to lose track of their work progress and deviate from their priorities. Problems like these are far too common among working adults and students embarking on tightly scheduled projects. Thus, our team presents to you the solution to all your management problems! 

Presenting to you, iManager.

iManager helps you manage all your tasks in one convenient application. With its interactive UI, managing tasks has never been easier.
Our product incorporates niche features such as FlexiCommands and Google Integration which saves you time and energy, managing tasks that would otherwise require the use of multiple applications such as Todois and Google Calendar.

Without further ado, let us get started. 

## Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1. Download the latest `iManager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your iManager application.
3. Double-click the file to start the app. The GUI should appear in a few seconds. Refer to [UI Control](#ui-control) for a more detailed walkthrough of various UI components. 
   > <img src="images/Ui.png" width="800">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`add SunRiseMarathon`**: adds a reminder to attend SunRise Marathon.
   * **`find Today`**: searches the task list for all events happening today. 
   * **`exit`**: exits the application.
6. Refer to the [Features](#features) section below for details of each command.

### Task Status     

## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Words in `lower_case` are commands and connectors.
> * `|` means "or".
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` behind can have multiple instances.


### Viewing help

#### Viewing all commands
Shows a list of commands available in iManager. <br>

Format: `help`
> A list of available commands are also shown if you enter the wrong command.

Examples: 

* `help` <br>
  Shows all available commands with examples.

#### Viewing a specific command
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
  Shows "add" command with examples.
  
### Adding a task

#### Adding a floating task
Adds a floating task to the task list. <br>

Format: `add NAME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Name, description and tag are case insensitive.
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.

Examples: 
* `add exam` <br>
   Add a floating task named "exam" to the task list.
* `add exam p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 floating task named "exam" with description "SR1" and tag "CS2010" to the task list.

#### Adding a pending task
##### Adding a pending task with START DATE & TIME only

Adds a pending task with only start date & time to the task list. <br>

Format: `add NAME on START_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Name, description and tag are case insensitive.
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no start time (i.e HHMM) is specified, the time will be the CURRENT time.

Examples:
* `add exam on 10-22-2017` <br> 
   Add a pending task named "exam" on 22 October 2017, current time to the task list.
* `add exam on 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" on 22 October 2017, 1500 with description "SR1" and tag "CS2010" to the task list.
 
 
##### Adding a pending task with END DATE & TIME only

Adds a pending task with only end date & time to the task list. <br>

Format: `add NAME by END_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Name, description and tag are case insensitive.
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no end time (i.e HHMM) is specified, the time will be 2359.
> * The START_DATETIME is the CURRENT date and time by default.

Examples: 
* `add exam by 10-22-2017` <br>
   Add a pending task named "exam" that starts now and end by 22 October 2017, 2359 to the task list.
* `add exam by 10-22-2017 1500 p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts now and end by 22 October 2017, 1500 with description "SR1" and tag "CS2010" 
   to the task list.
  
  
##### Adding a pending task with both START DATE & TIME and END DATE & TIME 

Adds a pending task with both start date & time and end date & time to the task list. <br>

Format: `add NAME from START_DATETIME to END_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Name, description and tag are case insensitive.
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no start time (i.e HHMM) is specified, the time will be the CURRENT time.
> * If no end time (i.e HHMM) is specified, the time will 2359.

Examples:
* `add exam from 10-22-2017 to 10-22-2016` <br>
   Add a pending task named "exam" that starts from 22 October 2017, current time to 22 October 2017, 2359 to the task list.
* `add exam from 10-22-2017 1300 to 10-22-2016 1500 p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts from 22 October 2017, 1300 to 22 October 2017, 1500 with description "SR1" and 
   tag "CS2010" to the task list.
  
#### Adding a recurring task 

Adds a recurring task that spans over a period of time to the task list. <br>

Format: `add NAME [at|on|by|from START_DATETIME to END_DATETIME] every [DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION] [t/TAG]` <br>
> * Name, description and tag are case insensitive.
> * Tasks can have any number of tags.
> * If no priority is specified, the task will be of lowest priority.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * If no start time (i.e HHMM) is specified, the time will be the CURRENT time. 
> * If no end time (i.e HHMM) is specified, the time will 2359. 
> * Any form of abbreviation can be use for WEEK (e.g Tue, Tues, Tuesday), case insensitve. 
> * No abbreviation can be use for DAY, MONTH and YEAR.
> * Month (i.e MM) is ignored for every MONTH.
> * It is suffice to use time (i.e HHMM) for every DAY and every WEEK. Date (i.e MM-DD-YYYY) will be ignored.
> * One must include START_DATETIME and END_DATETIME for every MONTH and every YEAR. 

Examples:
* `add exam every day p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts at the current time and recurs everyday with description "SR1" and tag  
   "CS2010" to the task list.
* `add exam from 10-22-2017 1300 to 10-22-2017 1500 every year p/1 d/SR1 t/CS2010`<br>
   Add a priority 1 pending task named "exam" that starts on the 22nd October 1300 to 22nd October 1500 that recurs every year with 
   description "SR1" and tag "CS2010" to the task list.

### Editing a task

Edit a task in task list by index or name<br>

Format: `Edit INDEX|NAME [at|on|by|from START_DATETIME to END_DATETIME] [every DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION][t/TAG...]`<br>
> * Name, description and tag are case insensitive.
> * INDEX refers to the index number shown in the most recent listing of tasks. 
> * INDEX must be positive integer e.g 1, 2, 3.
> * Date format is MM-DD-YYYY HHMM (24 hour Format) e.g. 10-22-2017 1500.
> * Editing `t/TAG` appends to the existing tags of a task.
> * Editing the same tag name remove the tag itself. 
    e.g if task contains tag [friends], edit [/t friends] would remove the 'friends' tag.
> * All tags of an existing task can be remove by using `t/none`. Case insensitive. 
> * Operation is equivalent to deleting an old task and adding a new task, however tags will not get overwritten unless specified 
    (refer to previous annotation).

Examples:

* `Edit 1 on 10-22-2017 1500 p/1 d/SR1`<br>
   Edit the first task in the task list such that it is a priority 1 pending task named "exam" on 22 October 2017, 1500 with description 
   "SR1".
* `Edit exam from 10-22-2017 1300 to 10-22-2017 1500 every year p/1 d/SR1 t/CS2010`<br>
   Edit the first task in the task list such that it starts on the 22nd October 1300 to 22nd October 1500 that recurs every year with 
   description "SR1" and append tag "CS2010" to "exam".

### Deleting a task

Delete a task from the task list. <br>

Format: `del NAME|INDEX|TAG`<br>
> * Name and tag are case insensitive.
> * INDEX refers to the index number shown in the most recent listing of tasks.
> * INDEX must be positive integer e.g 1, 2, 3.
> * Deleted task can be restored with the `undo` command.

Examples: 
* `delete exam`<br>
   Edit all tasks named "exam" in the task list.
* `delete 1`<br>
   Delete the first task in the task list.
* `delete CS2010`<br>
   Delete all tasks containing tag "CS2010".

### Viewing tasks

#### Viewing all tasks
View a list of all task in iManager.

Format: `view `<br>

#### Viewing all Today's tasks
View a list of all today's task in iManager. Does not show floating, "done" and overdue tasks. 

Format: `view t`<br>

#### Viewing all pending tasks
View a list of all pending task in iManager. Does not show floating, "done" and overdue tasks. 

Format: `view p`<br>

#### Viewing all "done" tasks
View a list of all "done" task in iManager. Does not show pending, floating, overdue and Today's tasks. 

Format: `view c`<br>

#### Viewing all floating tasks
View a list of all floating tasks in iManage. Does not show pending, "done", overdue and Today's tasks. 

Format: `view f`<br>

#### Viewing all overdue tasks
View a list of all tasks iManage. Does not show pending, "done", floating and Today's tasks. 

Format: `view`<br>

#### Viewing a specified task
View a specified task by index in iManager. 

Format: `view INDEX`<br>
> * INDEX refers to the index number shown in the most recent listing of tasks. 

Examples:
* `view 1` <br>
   Show the first task in the task list. 
   
### Marking a task as "done"
Mark an existing task as "done" in iManager. A "done" task gets transferred to the "done" list. 

Format: `done INDEX|NAME`
> * Name is case insensitive.
> * INDEX refers to the index number shown in the most recent listing of tasks.
> * INDEX must be positive integer e.g 1, 2, 3.
> * Marking an "undone" task will change its status to "done" while marking a "done" task will change its status to 
    "undone". 
> * Marked tasks in the current session can be reverted with the `undo` command.

Examples: 
* `done 1`<br>
   Mark the first task in the task list as "done".
* `done exam`<br>
   Mark a task named "exam" in the task list as "done".
 
### Finding for tasks
Find tasks with details containing any of the given keywords. Details include name, priority, description, tags, start date & time and end date & time.

Keyword Type | Result  | Example
:--------: | -------- | -------- 
NAME | Find all tasks whoose name matches the input. | `find exam`
PRIORITY | Find a task whoose priority matches the input. | `find \p1`
DESCRIPTION | Find a task whoose description matches the input. | `find \dSR1`
TAG | Find all tasks whoose tag matches the input. | `find \tCS2010`
MM-DD-YYYY | Find all tasks whoose starts date or end date matches the input. | `find 10-22-2017`
\mMM-YYYY | Find all tasks whoose starts or ends on a particular month and year that matches the input. | `find \m10-2017`
\yYYYY | Find all tasks whoose starts or ends on a particular year that matches the input. |  `find \y2017`
\hHHMM | Find all tasks whoose starts time or end time matches the input. | `find \h1500`

Format: `find [KEYWORDS...]`

> * Keywords are case insensitive. 
> * The order of the keywords does not matter. 
> * Partial words will also be matched e.g `ex` will match `exam`.
> * Date format is MM-DD-YYYY e.g. 10-22-2017.
> * TIME format is HHMM (24 hour Format) e.g. 1300.
> * Mutiple keywords of varying types is allowed. 
> * A task is match as long as any of its detail (i.e NAME, PRIORITY, DESCRIPTION etc.) CONTAINS the keyword. 

Advanced Examples: 
* `find exam \p1 \dSR1`<br>
   Find all tasks whoose name contains "exam" or priority contains "1" or description contains "SR1".
* `find exam \p1 \dSR1 \tCS2010 \m10-2017 \h1500`<br>
   Find all tasks whoose name contains "exam" or priority contains "1" or description contains "SR1" or tag contains "2010" or (starts 
   or ends on October 2017") or (start time or end time contains "1500").
   
### Clearing of "done" tasks
Clears all "done" tasks. <br>

Format: `clear`

> * Cleared tasks in the current session can be restored with the `undo` command.

### Toggle Google Calendar
Toggle the view of Google Calendar. The tasks in iManager will be synchronised with your Google Calendar. You cannot make any modification through the calendar.

Format: `toggle`

### Undo most recent command
Undo the most recent command that was successfully executed. Only commands that modify iManager in the same session can be undone. Command history will be cleared once iManager exits. 

Format: `undo`

> * `undo` only support add, edit, delete, done, clear and redo command. 

### Redo most recent undo
Redo the most recent undo.  Only commands that modify iManager in the same session can be redo. Command history will be cleared once iManager exits. 

Format: `redo`

> * `redo` only support add, edit, delete, done, clear and undo command. 

### Reset saved data
Reset the applcation data. iManager can completely erase the saved data and start with a "clean" state. Application will be saved in the default save directory (under project root folder). When the application is reset, it cannot be restored.

Format: `reset`

### Save
#### Saving the data
The application data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

#### Changing the save location
Change the save directory. The application data is saved in a file called "iManager.txt" under the project root folder by default or if the file path is not specified. You can change the location by specifying the file path as a command argument. New file will be automatically created as long as given directory is valid.

Format: `saveto FILEPATH`

> * FILEPATH must be valid
> * `cd` will tell you the current location of the save data. 

Examples:
* `cd`
* `saveto C:\Users\Computing\Desktop\CS2103`

### Exiting the application
Exits the application.

Format : `exit`

## FAQ

Q: I can't undo my previous commands after reopening iManager <br>
A: Like most task managers out there, iManager does not save a list of previous commands upon exiting.

Q: Is it possible to set my storage path to a portable drive to use it on a different computer? <br>
A: Yes! Just use the `saveto` command and choose the specified path of the portable drive.

Q: The iManager is not showing everything that I have added upon booting?<br>
A: Our iManager only shows Today's task on start-up. To view pending, "done", floating or overdue task please refer to [Viewing tasks](#viewing-tasks). 


## Command Summary

| Command | Format | Description |
|:-------:|--------|-------------|
| [help](#viewing-help) | `help` | Opens a help page. |
|  | `help [COMMAND]` | Help with specific command. | 
| [add](#adding-a-task) | `add NAME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Adds a floating task to the task list. |
|  | `add NAME at START_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Adds a pending task with only start date & time to the task list. |
|  | `add NAME by END_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Adds a pending task with only end date & time to the task list. |
|  | `add NAME from END_DATETIME to START_DATETIME [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Adds a pending task with start date & time and end date & time to the task list. |
|  | `add NAME [at|on|by|from START_DATETIME to END_DATETIME] every [DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Adds a recurring task that spans over a period of time to the task list. |
| [edit](#editing-a-task) | `edit INDEX|NAME [at|on|by|from START_DATETIME to END_DATETIME] [every DAY|WEEK|MONTH|YEAR] [p/PRIORITY] [d/DESCRIPTION] [t/TAG...]` | Edit a task with the new parameters. |
| [del](#deleting-a-task) | `del INDEX|NAME|TAG` | Delete a task from the task list. |
| [view](#viewing-tasks) | `view` | View all tasks. |
|  | `view INDEX` | View a specified task by index. |
|  | `view p` | View all pending task. |
|  | `view c` | View all "done" task. |
|  | `view f` | View all floating task. |
|  | `view o` | View all overdue task. |
|  | `view t` | View all Today's task. |
| [done](#marking-a-task-as-done) | `done INDEX|NAME` | Mark a task as "done". |
| [find](#finding-for-tasks) | `find [KEYWORDS..]` | Find tasks with details containing any of the given keywords. |
|  | `find \mMM-YYYY`| Search for tasks belonging to a particular month and year, both start and end. |
|  | `find \yYYYY`| Search for tasks belonging to a particular year, both start and end. |
|  | `find\hHHMM`| Search for tasks belonging to a particular time, both start and end.  |
| [clear](#clearing-of-done-tasks) | `clear` | Clear all "done" tasks. |
| [toggle](#toggle-google-calendar) | `toggle` | Toggle Google Calendar. |
| [undo](#undo-most-recent-command) | `undo` | Undo the most recent command. |
| [redo](#redo-most-recent-undo) | `redo` | Redo the most recent undo. |
| [reset](#reset-saved-data) | `reset` | Reset saved data. |
| [saveto](#save) | `saveto PATH` | Change the save directory. |
|  | `cd` | Show current save directory. |
| [exit](#exiting-the-application) | `exit` | Exit the application. |

