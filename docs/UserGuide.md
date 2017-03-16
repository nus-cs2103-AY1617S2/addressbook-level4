# Doit User Guide

## Table of Contents

1. [Introduction](#1-introduction)
   * 1.1 [About Doit](#11-about-doit)
   * 1.2 [Terminologies Used](#12-terminologies-used)
2. [Getting Started](#2-getting-started)
   * 2.1 [Downloading Doit](#21-downloading-doit)
   * 2.2 [Launching Doit](#22-launching-doit)
3. [Quick Start](#3-quick-start)
   * 3.1 [Visual Walkthrough](#31-visual-walkthrough)
   * 3.2 [Command Format](#32-command-format)
4. [Features](#4-features)
   * 4.1 [Getting Help](#)
   * 4.2 [Adding an Item](#)
   * 4.3 [Undoing a Command](#)
   * 4.4 [Editing an Item](#)
   * 4.5 [Finding an Item](#)
   * 4.6 [Marking Item as Done](#)
   * 4.7 [Delete a Item](#)
   * 4.8 [Clear All Items](#)
   * 4.9 [Saving New Location](#)
   * 4.10 [Exiting The Program](#)
   * 4.11 [Saving at Local file](#)
5. [FAQ](#faq)
6. [Command Summary](#command-summary)

## 1. Introduction

### 1.1 About Doit
Doit is a personal task management software that helps you to organise your task by providing an overview of the different tasks that you may have at a glance.

Doit is simple to use as all functions can be executed with just a single line of command meaning that you will only need a keyboard to fully navigate this software.

### 1.2 Terminologies Used

* Task: A task to be done that has a deadline.
* Event: An event to be participated that has a start and end time.
* Floating Task: A task to be done that has no deadline.
* Items: A generic term used to describe a collection of tasks, events and floating tasks.
* GUI : Graphical User Interface.

## 2. Getting Started

### 2.1 Downloading Doit

1. Ensure that you have Java version `1.8.0_60` or later installed on your computer.<br>

 > Just having any Java 8 version is not enough. <br>
 > Doit will not work with earlier versions of Java 8.<br>
 > You can download the latest version of Java 8 at http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html.

2. Download the latest `Doit.jar` from the [releases](../../../releases) tab.
3. Copy the file to the folder you want to use as the home folder for Doit.

### 2.2 Launching Doit

1. Double-click on `Doit.jar` to launch the app.
2. The main interface should appear as shown in figure 1 in a few seconds.
<br>
    > <img src="https://raw.githubusercontent.com/CS2103JAN2017-W14-B3/main/master/docs/images/MainInterface.png" width="600">
    > _Figure 1 : Main GUI_
<br>

## 3. Quick Start

### 3.1 Visual Walkthrough

1. Figure 2 shows how Doit may look with a few items added.
<br>
    > <img src="https://raw.githubusercontent.com/CS2103JAN2017-W14-B3/main/master/docs/images/Ui.png" width="600">
    > _Figure 2 : Example of a GUI instance_<br>

2. Note how Doit has 3 columns "On", "By" and "Anytime".
   * The "On" Column shows you events that take place between a certain time period.
   * The "By" Column shows you tasks which have to be completed by a certain deadline.
   * The "Anytime" Column shows you floating tasks which can be completed anytime.

3. You can type a command in the command box at the bottom and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.

4. Here are some sample commands that you may try:
   * **`help`** : Displays help prompt.
   * **`add`** `completeUserGuide et/28-Feb-2017 23:59 d/description p/high #Important`
    : Adds a task named completeUserGuide to the task manager.
   * **`delete`**`3` : Deletes the 3rd item shown in the UI.
   * **`help`** : Displays help prompt.
   * **`exit`** : Exits the app.

5. You can refer to the [Features](#features) section below for the details of each command.<br>

### 3.2 Command Format

* Words in **`Bold`** are the commands.
* Words in `UPPER_CASE` are the parameters.
* Words in `[SQUARE_BRACKETS]` are optional parameters.
* Words with `...` after them can have multiple instances.
* Parameters & optional parameters can be in any order.



## 4. Features


### 4.1 Getting Help : `help`

Displays the help window.<br>

Format: **`help`**

> * Help is also shown if you enter an incorrect command e.g. `abcd`<br>


### 4.2 Adding an Item: `add`

Adds an item to the task manager.<br>
Format: `add NAME [st/STARTTIME] [et/ENDTIME] [d/DESCRIPTION] [p/PRIORITY] [#TAG]...`

> * Items without both `STARTTIME` and `ENDTIME` specified are considered as floating tasks. They will be auto-tagged as floating tasks.<br>
> * Items with `ENDTIME` specified without specifying `STARTTIME` are considered tasks. They will be auto-tagged as tasks.<br>
> * Items with both `STARTTIME` and `ENDTIME` or only `STARTTIME` specified are considered as events. They will be auto-tagged as events.<br>
> * Priority only has 3 types: low, med, high.<br>
> * You will be allocated a default `ENDTIME` for events without specified `ENDTIME`.
>   * Case 1: There is no more later events for the day. The default `ENDTIME` will be the end of the day.
>   * Case 2: There is another event later during the same day. The default `ENDTIME` will be the start of the next event.<br>

Examples:

* `add floatingtask d/description p/low #NotImportant`<br>
* `add task st/28-Feb-2017 23:59 d/description p/med #NotSoImportant`<br>
* `add event st/28-Feb-2017 23:59 et/31-Mar-2017 23:59 d/description p/high #Important`<br>


### 4.3 Undoing the Previous Command : `undo`

Undos the previous command that is not the undo command.<br>
Format: `undo`

> * You can undo a number of commands that is not the `undo` command (e.g. 3 commands) by typing `undo` that number of times (e.g. 3 times).<br>
> * You can only revert up to 1 command that is not the undo command.<br>

### 4.4 Editing an Item : `edit`

Edits an existing item in the task manager.<br>
Format: `edit INDEX [st/STARTTIME] [et/ENDTIME] [d/DESCRIPTION] [p/PRIORITY] [#TAG]...`

> * Edits the item at the specified `INDEX`.<br>
> * The index refers to the index number shown in the last item listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>
> * At least one of the optional fields must be provided.<br>
> * Existing values will be updated to the input values.<br>
> * When editing tags, the existing tags of the item will be removed i.e adding of tags is not cumulative.<br>
> * You can delete the field of a parameter by specifing the parameter syntax  without anything behind it.<br>
> * You can remove all the item's tags by typing `#` without specifying any tags after it.<br>

Examples:

* `edit 1 et/9pm p/high #CS1010 d/`<br>
  Edits the end time ,priority and hashtag of the 1st item to be `9pm` , `high` and `#CS1010` respectively while deleting the description.<br>

* `edit 2 et/9pm p/high #`<br>
  Edits the name of the 2nd item's deadline to `9pm` and priority to `high` and clears all existing tags.<br>

### 4.5 Finding all Items Containing any Keywords in Their Names, Tags and Timings: `find`

Finds items whose names, tags and timings contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case in-sensitive. e.g `cs1231` will  match `CS1231`<br>
> * Only the item name is searched.<br>
> * Only full words will be matched e.g. `CS2103T` will not match `CS2103`<br>
> * Tasks matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `OP` will match `OP1 OP`<br>
> * `KEYWORD` can be dates, times, names or tags.<br>

Examples:

* `find 1-Mar-2017`<br>
  The events, tasks and floating tasks lists will show items that take place on 1-Mar-2017<br>

* `find OP` <br>
  Returns `OP1`<br>

* `find OP1 OP2 Lab1`<br>
  Returns any task having names `Lab1`, `OP1`, or `OP2`<br>


### 4.6 Marking specified items from Doit as done: `done`
Marking the specified item from the task manager as done.<br>
Format: `done [ITEM_TYPE] INDEX`

> * Marks the item at the specified `INDEX` as done. <br>
> * `ITEM_TYPE` can be `ftask`, `floating tasks` and `anytime` for floating tasks or nothing,`task` and `before` for tasks. This is only use for the default page, where there are multiple columns.
<img src="" width>
> * The index otherwise refers to the index number shown in the most recent listing of pop-ups.
<img src="" width>
> * The index **must be a positive integer** 1, 2, 3, ...<br>
> * Events cannot be marked.<br>

Examples:

* `done 2`<br>
 Marks the 2nd item in the task manager as done.
* `find MA1521 Tutorial 1`<br>
 `done 1`<br>
 Marks the 1st item in the results of the `find` command.
* `done 3`<br>
 Marks the 3rd floating task shown in the floating task column of the default task manager page as done.


### 4.7 Deleting specified items from Doit: `delete`
Deletes the specified item from the task manager.<br>
Format: `delete INDEX`

> * Deletes the item at the specified `INDEX`. <br>
> * The index refers to the index number shown in the most recent listing.<br>
> * The index **must be a positive integer** 1, 2, 3, ...<br>

Examples:

* `delete 2`<br>
 Deletes the 2nd item in the task manager.
* `find MA1521 Tutorial 1`<br>
 `delete 1`<br>
 Deletes the 1st item in the results of the `find` command.


### 4.8 Clearing all entries : `clear`

Clears all entries from Doit.<br>
Format: `clear`

### 4.9 Saving the data into a specified filename and location  : `save`

Saves the data into a specified filename and location.<br>
Format: `save FILE_PATH/FILE_NAME.xml`
> * Saves the data file with your own file name in the layers of folder you declare yourself.<br>
> * Saved file must be of type xml. Hence, it must end with .xml.<br>
> * Saved file location will be limited in the Doit Application folder.<br>

Examples:

* `save folder1/folder2/savefile.xml`<br>
  Saves the data file in a file named savefile.xml at inside folder2 which is inside folder1 of the Doit Application folder.<br>
* `save savefile.xml`<br>
  Saves the data file in a file named savefile.xml in the Doit Application folder.<br>

### 4.10 Exiting the program : `exit`
Exits the program.<br>
Format: `exit`


### 4.11 Autosaving the local data

Program data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.<br>

## 5. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous task manager folder.<br>

## 6. Command Summary

* **Help** : `help` <br>
  e.g.
  * `help`

* **Add Task** : `add TASKNAME ed/END DATE [et/END TIME] [p/PRIORITY]  [#TAG]...`<br>
  e.g.
  * `add completeUserGuide d/28-Feb-2017 t/2400 p/high #Important`

* **Add Floating Task** : `add FLOATINGTASKNAME [p/PRIORITY] [#TAG]...`<br>
  e.g.
  * `add reachDiamondRank p/low #Overwatch`

* **Add Event** : `add EVENTNAME sd/START DATE ed/END DATE [st/START TIME] [et/END TIME] [p/PRIORITY]  [#TAG]...`<br>
  e.g.
  * `add finishCS3230Lab sd/01-Mar-17 ed/01-Mar-17 st/2000 et/2200 p/med #CS3230`
  * `add finishCS3230Lab sd/01-Mar-17 st/2000 p/med #CS3230`

* **Undo** : `undo` <br>
 e.g.
 * `undo`

* **Edit** `edit INDEX [t/DEADLINE TIME] [p/PRIORITY] [#TAG]...`<br>
 e.g.
 * `edit 1 t/9pm p/high #CS1010`

* **Find** : `find KEYWORD [MORE_KEYWORDS]` <br>
 e.g.
 * `find MA1101R assignment`
 * `find 01-Mar-2017`

* **Done** : `done [ITEM_TYPE] INDEX` <br>
 e.g.
 * `done 1`
 * `done ftask 1`
 * `done task 3`

* **Delete** : `delete INDEX` <br>
 e.g.
 * `delete 3`

* **Clear** : `clear` <br>
  e.g.
  * `clear`

* **Save** : `save FILE_PATH/FOLDER_NAME.xml`<br>
  e.g.
  * `save folder1/folder2/savefile.xml`<br>

* **Exit** : `exit`<br>
  e.g.
  * `exit`
