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

iManager is a task manager application that helps you manage all your tasks at one place. With its interactive UI, user can now experience managing their tasks like never before.  Our product incorporates niche features such as FlexiCommands and Google Integration that brings convenient to user in managing their schedules and tasks. iManager remove the need to rely on multiple applications as it manages it all! 

Without further ado, let’s get started. 

   
## Quick Start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>
   > Having any Java 8 version is not enough. <br>
   This app will not work with earlier versions of Java 8.
   
1.  Download the latest `iManager.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your iManager application.
3. Double-click the file to start the app. The GUI should appear in a few seconds. Refer to [UI Control](#ui-control) for a more detailed walkthrough of various UI components. 
   > <img src="images/UIOverview.png" width="800">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window. 
5. Some example commands you can try:
   * **`NIL`**:    
6. Refer to the[Features](#features) section below for details of each command.
   


## UI Control

#### Task Status     
The statuses of a task is shown as below:  
| Avatar | Status |
| --- | --- |
| <img src="images/Floating.png" width="150"> | Floating |
| <img src="images/Complete.png" width="150"> | Complete |
| <img src="images/Pending.png" width="150"> | Pending |
| <img src="images/Overdue.png" width="150"> | Overdue |


## Features

> **Command Format**
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * The order of optional parameters are flexible.

#### Viewing help : `help`

Shows a list of all commands in iManager.

List of available [KEY_WORD] for help: 
- 
- 
- 
- 

Format: `help`

Examples:

* `help`  
  Shows all available commands and examples 
  
#### Adding a task: `add` 

Adds a task to iManager<br>

Format: `add`<br>

Examples: 
* 
* 

#### Editing a task: `add` 

Edit a task in iManager<br>

Format: `edit`<br>

Examples:
* 
* 

#### Deleting a task: `delete` 

Delete a task from iManager<br>

Format: `delete`<br>

Examples: 
* 
* 

#### Viewing all tasks : `view` 
Shows a list of all tasks iManage. Able to filter based on status of task/event.

Format: `view`<br>

Examples: 
* 
* 

#### Marking a task as completed : `done` 
Mark an existing task as completed in iManager.

Format: `done`

Examples: 
* 
* 
 
#### Finding for tasks : `find` 
With the find command, you can find for tasks or events which contain some keywords in their **name** as well as in their **descriptions**. 

Format: `find `

Examples:
* 
*  
  
#### Clearing of tasks
Clears all completed tasks. <br>

Format: `clear`

Examples: 
* 
* 

#### Toggle google calender
Toggle the view of google calendar and focus to certain time time as user specified. 

Format: 

Examples:
* 
* 

#### Redo most recent command 
You can go advance to historical versions of Dowat with the use of redo commands. Only commands that modify Dowat in the same session will be restored. Any versions of current session will not be accessible after restarting Dowat.  

Format: `redo`

#### Undo most recent command 
You can go back to historical versions of iManager with the use of undo commands. Only commands that modify iManager in the same session will be restored. Any versions of current session will not be accessible after restarting iManager.  

Format: `undo`

#### Changing the save location
iManager will save data in a file called iManager.txt in the project root folder by dafault or if the file path is not specified. 
You can change the location by specifying the file path as a command argument.
New file will be automatically created as long as given directory is valid.

Format: `saveto FILEPATH`

> FILEPATH (must be valid)

Examples:
* `saveto C:\Users\Computing\Desktop\CS2103` 

#### Exiting the program
Format : `exit`



## FAQ

**Q**: <br>
**A**: <br>


## Command Summary

 Command | Format  | Description
-------- | -------- | -------- 
[help](#help) | `help`|
[add](#add) | `add NAME [PRIORITY] [/t TAG1, TAG2…] `|
 | `add NAME [PRIORITY] [/t TAG1, TAG2…] `|
 | `add NAME [PRIORITY] [/t TAG1, TAG2…] `|
 | `add NAME [PRIORITY] [/t TAG1, TAG2…] `|
 | `add NAME [PRIORITY] [/t TAG1, TAG2…] `|


[edit](#edit) | `list [/t|/e] [/a]`
[delete](#delete) | `edit /t INDEX [/name NEW_NAME] [/desc NEW_DESCRIPTION] [/by NEW_DEADLINE_DATETIME]`
[view](#view) | `edit /e INDEX [/name NEW_NAME] [/desc NEW_DESCRIPTION] [/from NEW_START_DATETIME /to NEW_END_DATETIME]`
[done](#done) | `mark INDEX`
[undo](#undo) |`delete /t|/e INDEX`
[redo](#redo) |`select /t|/e INDEX`
[find](#find) | `save FILEPATH`
[clear](#clear) | `help [COMMAND]`
[reset](#reset) | `undo`
[saveto](#saveto) | `find KEYWORD [/ MORE_KEYWORDS][/power]`
[exit](#exit) |`clear /t|/e [/a]`



