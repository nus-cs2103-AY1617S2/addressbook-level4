# DoIt User Guide

## Table of Contents

1. [Introduction](#1-introduction)
   * 1.1 [About DoIt](#11-about-doit)
   * 1.2 [Terminologies Used](#12-terminologies-used)
2. [Getting Started](#2-getting-started)
3. [Quick Start](#3-quick-start)
   * [Visual Walkthrough](#31-visual-walkthrough)
4. [Features](#4-features)
   * [Command Format](#32-command-format)
   * 4.1 [Getting Help](#)
   * 4.2 [Adding an Task](#)
   * 4.3 [Undoing a Command](#)
   * 4.4 [Redoing an Undone Command](#)
   * 4.5 [Editing an Task](#)
   * 4.6 [Finding an Task](#)
   * 4.7 [Viewing All Uncompleted Tasks](#)
   * 4.8 [Marking Task as Done](#)
   * 4.9 [Viewing Completed Tasks](#)
   * 4.10 [Marking Tasks as Uncompleted](#)
   * 4.11 [Delete an Task](#)
   * 4.12 [Clear All Tasks](#)
   * 4.13 [Sorting The Tasks](#)
   * 4.14 [Setting a Customized Command Word](#)
   * 4.15 [Saving to New Location](#)
   * 4.16 [Loading of Existing data](#)
   * 4.17 [Exiting The Program](#)
   * 4.18 [Saving at Local file](#)
5. [FAQ](#faq)
6. [Command Summary](#command-summary)

## 1. Introduction

### 1.1 About *DoIt*
Do you have problem organising your life?
Stop procrastinating and use *DoIt* !!!

*DoIt* is a personal task management software that helps you to organise your task by providing an overview of the different tasks that you may have at a glance.

*DoIt* is simple to use as all functions can be executed with just a single line of command meaning that you will only need a keyboard to fully navigate this software.

### 1.2 Terminologies Used

Here are some terminologies used in this user guide:

* Task: A task to be done that has a deadline.
* Event: An type of task that has a start and end time.
* Floating Task: A task to be done that has no deadline.
* GUI : Graphical User Interface.

## 2. Getting Started

* Ensure that you have Java version `1.8.0_60` or later installed on your computer.<br>

   > Just having any Java 8 version is not enough. <br>
   > *DoIt* will not work with earlier versions of Java 8.<br>
   > You can download the latest version of Java 8 at http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html.

* Download the latest `DoIt.jar` from the [releases](../../../releases) tab.
* Copy `DoIt.jar` to a folder as the home folder for *DoIt*.
* To launch the *DoIt* application, double-click on `DoIt.jar` .
* The main interface should appear as shown in figure 1 in a few seconds.
<img src="https://raw.githubusercontent.com/CS2103JAN2017-W14-B3/main/master/docs/images/MainInterface.png" width="600">
_Figure 1 : Main GUI_
<br>

## 3. Quick Start

### Visual Walkthrough

1. Figure 2 shows *DoIt* with a few tasks added.
<img src="https://raw.githubusercontent.com/CS2103JAN2017-W14-B3/main/master/docs/images/Ui.png" width="600">
_Figure 2 : Example of a GUI Instance_<br>

2. *DoIt* has 3 columns "On", "By" and "Anytime".
   * The "On" Column shows you events that take place between a certain time period.
   * The "By" Column shows you tasks which have to be completed by a certain deadline.
   * The "Anytime" Column shows you floating tasks which can be completed anytime.

3. You can type a command in the command box at the bottom and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

4. Here are some sample commands that you may try:
   * **`help`** : Displays help prompt.
   * **`add`** `completeUserGuide e/28-Feb-2017 23:59 d/description p/high #Important`
    : Adds a task named completeUserGuide to the task manager.
   * **`delete`**`3` : Deletes the 3rd task shown in the UI.
   * **`help`** : Displays help prompt.
   * **`exit`** : Exits the app.

5. You can refer to the [Features](#features) section below for the details of each command.<br>





## 4. Features

>### Command Format
>
>Syntax used to differentiate command types:
>
>   * Words in **`Bold`** are the commands.
>   * Words in `UPPER_CASE` are the parameters which are values passed to a command.
>   * Words in `[SQUARE_BRACKETS]` are optional parameters.
>   * Words with `...` after them can have multiple instances.
>   * Parameters & optional parameters can be in any order.


### 4.1 Getting Help : `help`

Not sure where to start? Enter **`help`** to view the help window as shown in Figure 3 below:
<insert a screenshot of help window here>

Format: **`help`**

> * Help window is also shown if you enter an incorrect command e.g. `abcd`<br>


### 4.2 Adding an task: `add`

If you have an task you want *DoIt* to track, add it! Enter **`add`** into the command box followed by the relevant parameters. You can refer below for the correct format of the parameters.<br>

Format: `add NAME [s/STARTTIME] [e/ENDTIME] [d/DESCRIPTION] [p/PRIORITY] [t/TAG]...`

> * Tasks without both `STARTTIME` and `ENDTIME` specified are considered as floating tasks. They will be add under the "Anytime" list.<br>
> * Tasks with `ENDTIME` specified without specifying `STARTTIME` are considered tasks. They will be added under the "Deadline" list.<br>
> * Tasks with both `STARTTIME` and `ENDTIME` or only `STARTTIME` specified are considered as events. They will be added under the "Event" list.<br>
> * Priority only has 3 types: low, med, high.<br>
> * You will be allocated a default `ENDTIME` for events without specified `ENDTIME`.
>   * Case 1: There are no more later events for the day. The default `ENDTIME` will be the end of the day.
>   * Case 2: There is another event later during the same day. The default `ENDTIME` will be the start of the next event.<br>
>

Examples:

* If you wish to add a task that can be done anytime such learning to speak a new language:
  * **`add`**`Learn to speak a new language`<br>
* If you wish to add a task that must be completed before a deadline such as submitting a progress report by tomorrow:
  * **`add`**`Submit progress report e/tomorrow`<br>
* If you wish to add a task that starts and end at a specified time such as a complusory staff meeting on the 28 Feb from 1300 to 1500:
  * **`add`**`Staff meeting s/28-Feb-2017 13:00 e/28-Feb-2017 15:00 `<br>


### 4.3 Undoing the Previous Commands : `undo`

Made a mistake? No worries, you can undo your previous commands up to 10 times by typing **`undo`** into the command box.<br>

Format: **`undo`**

> * You can undo a number of commands that is allowed (e.g. 3 commands) by typing **`undo`** that number of times (e.g. 3 times).<br>
> * You can only revert up to 10 commands that are not the undo command.<br>
> * Commands that can be undone are **`add`**, **`delete`**, **`edit`**, **`clear`** and **`done`**.

### 4.4 Redoing the Undone Commands : `redo`

Want to redo what you have undone? Fear not as you can redo what you have previously undone up to 10 times by typing **`redo`** into the command box.<br>
Format: **`redo`**

> * You can redo a number of commands that is undone (e.g. 3 commands) by typing **`redo`** that number of times (e.g. 3 times).<br>
> * Commands that can be redone must be undone just previously without other commands.<br>
> * If you used non-undo commands just before redo, you will not be able to redo.<br>

### 4.5 Editing an task : `edit`

Want to edit an existing task? Well you can do so by typing **`edit`** followed by the task index number into the command box.<br>

Format: **`edit`**`INDEX [s/STARTTIME] [e/ENDTIME] [d/DESCRIPTION] [p/PRIORITY] [t/TAG]...`

> * Edits the task at the specified `INDEX`.<br>
> * The index refers to the index number as displayed in the current UI.<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>
> * At least one of the optional fields must be provided.<br>
> * Existing values will be updated to the input values.<br>
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.<br>
> * You can delete the field of a parameter by specifing the parameter syntax  without anything behind it.<br>
> * You can remove all the task's tags by typing `t/` without specifying any tags after it.<br>

Examples:

* If you want to edits the end time ,priority and tag of the 1st task to be `9pm` , `high` and `#CS1010` respectively while deleting the description.
    * **`edit`**`1 e/9pm p/high t/CS1010 d/`<br>
* If you want to edits the name of the 2nd task's deadline to `9pm` and priority to `high` and clears all existing tags.<br>
    * **`edit`**`2 e/9pm p/high t/`<br>


### 4.6 Finding all tasks Containing any Keywords in Their Names, Priority, Description and Tags: `find`

Need to find a specific task? Use the find command to get what you are looking for. Just type **`find`** followed by a keyword into the command box.<br>

Format: **`find`**`[NAME...][p/PRIORITY...][d/DESCRIPTION...][t/TAG...]`

> * The search is case in-sensitive. e.g `cs1231` will  match `CS1231`<br>
> * Only the task name is searched.<br>
> * Only full words will be matched e.g. `CS2103T` will not match `CS2103`<br>
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `OP` will match `OP1 OP`<br>
> * `KEYWORD` can be names, priority, description or tags.<br>

Examples:

* If you wish to find tasks with low priority:
    * **`find`**`p/high`<br>
    * UI will display tasks having low priority<br>

* If you wish to find all tasks with descriptions containing `Meeting`;
    * **`find`**`d/Meeting` <br>
    * UI will display tasks having descriptions containing`Meeting`<br>

* If you wish to find all tasks with names containing `Staff Meeting`;
    * **`find`**`Staff Meeting`<br>
    * UI will display tasks having names containing `Staff` or `Meeting`<br>

* If you wish to find all tasks named `Report` and tags containing `urgent`
    * **`find`**`Report t/urgent` <br>
    * UI will display tasks that contain `Report` in their name and have the `urgent` tag <br>

### 4.7 Viewing all uncompleted tasks: `list`
Want to see all the tasks you have yet to complete? Simply type **`list`** to clear all **`find`** filters.

Format: **`list`**


### 4.8 Marking specified tasks from *DoIt* as completed: `mark`

Completed a task? Celebrate by marking it as done on *DoIt* by typing **`mark`** followed by the task index into the command box.

Format: **`mark`**`INDEX`

> * Marks the task at the specified `INDEX` as completed. <br>
> * The index refers to the index number of the task shown in the currently displayed UI.
> * The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:

* If you have filed your taxes which happens to be the second task on the list:
    * **`mark`**`2`
    * The second task in the UI will be marked as completed and will disappear.<br>

* You can also perform command operations after a find command. In this case:
    * **`find`**`MA1521`<br>
    * **`mark`**`1`<br>
    * The 1st task in the results of the `find` command will be marked as completed.

### 4.9 Viewing completed tasks: `done`
Sometimes it's good to look back on our past acheivements. This can be accomplished by typing **`done`** into the command box.<br>

Format: **`done`**

### 4.10 Marking tasks as uncompleted: `unmark`
Want to mark a completed task as uncompleted? First display all completed task using `done` and type `unmark` followed by the task index dislayed into the command box. <br>

Format: **`unmark`**`INDEX`

> * Marks the task at the specified `INDEX` as uncompleted. <br>
> * The index refers to the index number of the task shown in the currently displayed UI.
> * The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:

* If you have submitted an assignment but was asked to redo it and it happens to be the second task in the display:
    * **`done`**
    * **`unmark`**`2`
    * The second task in the UI will be marked as uncompleted.<br>

### 4.11 Deleting specified tasks from DoIt: `delete`

Have an task that is not needed anymore? Delete it from DoIt by typing **`delete`** followed by the task index into the command box.<br>

Format: **`delete`**`INDEX`

> * Deletes the task at the specified `INDEX`.<br>
> * The index refers to the index number shown in the currently displayed UI.<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:

* If your meeting was cancelled which happens to be the 2nd task on the UI.
    * **`delete`**`2`<br>
    * The 2nd task displayed in the UI will be deleted.<br>
* You can also perform command operations after a find command. In this case:
    * **`find`**`MA1521 Tutorial 1`<br>
    * **`delete`**`1`<br>
    * The 1st task in the result of the`find` command will be deleted.<br>


### 4.12 Clearing all entries : `clear`

Want to start afresh? Type **`clear`** into the command box to erase all entries from DoIt.

Format: `clear`

> <p style="color:#ff0000; font-weight: bold; font-style:italic ">CAUTION: YOU WILL NOT BE ABLE TO UNDO THIS ACTION IF YOU CLOSE THE APP<p/>

### 4.13 Sorting tasks by name, priority, start time or end time  : `sort`

Want to control what you see first in the list? You can do so by sorting through priority, deadline or start time. <br>
Format: **`sort`**`SORT_CHOICE`

> * `SORT_CHOICE` includes `name`, `priority`, `end time` or `start time`<br>
> * The tasks will be sorted lexicographically if they are similar in your sorting choice.<br>

Examples:
* To sort the tasks by name:<br>
    * **`sort`**`name`<br>
* To sort the tasks by priority:<br>
    * **`sort`**`priority`<br>
* To sort the tasks by start time:<br>
    * **`sort`**`start time`<br>
* To sort the tasks by end time:<br>
    * **`sort`**`end time`<br>


### 4.14 Setting a customized command word : `set`

Want to set your own command shortcuts? DoIt allows this by typing **`set`** into the command box followed by the old command and the new command to reference it.<br>
Format: **`set`**`OLD_COMMAND NEW_COMMAND`
> Default commands are not deleted when you set a new command
> Default commands refer to those in section 4.1 to 4.15 of this user guide
> `OLD_COMMAND` can be default commands or commands you have set previously

Examples
* To set a shortcut for delete:<br>
  * **`set`**`delete del`
  * This allows `del` to be used in the future as a delete command.
* To set another shortcut for delete:<br>
  * **`set`**`del -`
  * This allows `-` to be used in the future as a delete command if you find the previous delete command `del` still too long to your liking.

### 4.15 Specifying the filename and location for data saving   : `save`

Saves the data into a specified filename and location.<br>

Format: **`save`**`FILE_PATH/FILE_NAME.xml`
> * Saves the data file with your own file name in the layers of folder you declare yourself.<br>
> * Saved file must be of type xml. Hence, it must end with .xml.<br>
> * `/` can be replaced with `\`.
> * Saved file location will be limited in the DoIt Application folder if the full directory is not used.<br>
> * Saved file location can be outside of the DoIt Application folder if you used the full directory path name for`FILE_PATH`.

Examples:
* To change the save location to a file called `savefile.xml` at `C:\Users\USER\Desktop`:<br>
    * **`save`**`C:\Users\USER\Desktop\savefile.xml`<br>
* To save the data file in a file named savefile.xml in the DoIt Application folder:<br>
    * **`save`**`savefile.xml`<br>

### 4.16 Loading of data from the specified the file: `load`

Loads the data from specified filename and location.<br>

Format: **`load`**`FILE_PATH/FILE_NAME.xml`
> * Loads existing data file with the file name in the layers of folder.<br>
> * Loaded file must be of type xml. Hence, it must end with .xml.<br>
> * `/` can be replaced with `\`.
> * Loaded file location will be limited in the DoIt Application folder if the full directory is not used.<br>
> * Loaded file location can be outside of the DoIt Application folder if you used the full directory path name for`FILE_PATH`.

Examples:
* To load a file called `savefile.xml` at `C:\Users\USER\Desktop`:<br>
    * **`load`**`C:\Users\USER\Desktop\savefile.xml`<br>
* To load a file named savefile.xml in the DoIt Application folder:<br>
    * **`load`**`savefile.xml`<br>

### 4.17 Exiting the program : `exit`

Want to exit the program? Type **`exit`** into the command box.<br>
Format:  **`exit`**


### 4.18 Saving local data

Program data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.<br>

## 5. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task manager folder.<br>

## 6. Command Summary


**Command** | **Parameters** | **Result** | **Example**
--- | --- | --- | ---
**add** | TASKNAME s/START DATE e/END DATE [p/PRIORITY] [#TAG]... | Adds a task under event | `add finishCS3230Lab s/01-Mar-17 e/01-Mar-17 p/med t/CS3230`
**add** | TASKNAME e/END DATE [p/PRIORITY] [t/TAG]... | Adds a task under "Deadlines" | `add completeUserGuide d/28-Feb-2017 2400 p/high t/Important`
**add** | TASKNAME [p/PRIORITY] [#TAG]... | Adds a task under "Anytime" | `add reachDiamondRank p/low t/Overwatch`
**clear** | | Deletes all tasks from DoIT | `clear`
**delete** | INDEX | Deletes task at the specified index | `delete 3`
**done** | | Displays all completed tasks | `done`
**edit** | INDEX [s/START TIME] [e/END TIME] [p/PRIORITY] [#TAG]... | Edits exisitng task with new details | `edit 1 s/9pm e/11pm p/high t/CS1010`
**exit** | | Exits DoIt | `exit`
**find** | KEYWORD [MORE_KEYWORDS] | Display tasks that match the keywords | `find n/MA1101R assignment p/med`
**help** | | Opens the help window | `help`
**list** | | Lists all uncompleted tasks | `list`
**mark** | INDEX | Marks task at specified index as completed | `mark 20`
**redo** | | Redo previously undone command | `redo`
**sort** | SORT_CHOICE | Sort tasks by name, priority, end time , start time | `sort name`<br>`sort priority`<br>`sort end time`<br>`sort start time`
**set** | OLD_COMMAND NEW_COMMAND | Customizes command words | `set delete del`<br>`set del -`<br>
**save** | FILE_PATH/FILE_NAME.xml | Saves DoIt's data at specified location and in specified file | `save folder1/savefile.xml`<br> `save C:/Users/USER/savefile.xml`<br>
**load** | FILE_PATH/FILE_NAME.xml | Loads an existing DoIt's data at specified location and in specified file | `load folder1/savefile.xml`<br> `load C:/Users/USER/savefile.xml`<br>
**undo** | | Undo previous command | `undo`
**unmark** | INDEX | Marks task at specified index as uncompleted | `unmark 6`
