# User Guide

- [About](#about)
- [Quick Start](#quick-start)
- [Features](#features)
- [FAQ](#faq)
- [Command Summary](#command-summary)

## About

Having trouble keeping track of the tasks you need to do? Looking for a simple yet effective task manager to help you organise your day? OneTwoDo is what you need!

OneTwoDo helps you to manage your evens, deadlines, and other to-do's so that you will not forget about your important tasks. 

OneTwoDo has a simple and clean interface, so that with one glance you can have a clear idea of your tasks ahead. 

OneTwoDo is specially designed to be keyboard-friendly. By simply typing commands in one line of text, OneTwoDo will faithfully execute your wish. You do not need to worry about clikcing multiple buttons or links. 

OneTwoDo is also extremely flexible. It is smart enough to understand various command formats and shortcuts. 

Get started with OneTwoDo today!


## Getting Started

Before using OneTwoDo, please ensure that you have installed Java version `1.8.0_60` or later as the app does not work with earlier Java 8 versions.

Start your OneTwoDo journey by downloading the latest `OneTwoDo.jar` from the [releases](../../../releases) tab. Download OneTwoDo directly into the home folder you want to use for the app.

You can start OneTwoDo by simply double-clicking the icon!
   > <img src="images/Ui.png" width="600">

Congratulations, now you are all set up!

Try some of these commands to get started!

   * **`help`**
      - Shows the help window.
   * **`add`**` Scubadiving session s/tmrw 1pm e/tmrw 2pm` 
        - Adds a task named 'Scubadiving session' to your OneTwoDo.
    * **`list`**
      - Shows a listing of all your tasks.
   * **`delete`**` E3`
      - Deletes the third `Event` shown in the current list of events.
   * **`exit`**
      - Exits from the application.
    
You may refer to the [Features](#features) section for more details on all the possible commands.


## Features

<img src="images/Ui-annotated.png" width="600">

After you have started OneTwoDo, you will see the following:

1. **Command box**
    - Enter a command into this box and press enter to execute the command.
2. **Result summary and tips**
    - A summary of the results of the executed command is shown to the user to give feedback about the effects of the command.
3. **Task panels**
    - Each panel displays your tasks depending on the category they belong to. 
    - Other commands also allow you to retreive the tasks to be shown here (e.g. `list` or `find` command. Please refer to the command format below).

Your tasks are separated into 3 categories, where each category is displayed in a task panel:

1. `Deadline` - This panel displays tasks with have an end date but no start date.
2. `Event` - This panel displays tasks with a start and end date.
3. `To-do` - This panel displays tasks without a start or end date.

### Command Format
    
- Command words are the first word of the command. (e.g. `find`, or `help`).
- Command options specifies additional information needed by the command.
    - It consists of a character followed by a forward slash (e.g. `s/`, `t/`, etc), followed by data to be specified after the forward slash if tgere are any. (e.g. `s/tomorrow 0900`). 
    - The order of options is not fixed after the task name. (e.g. executing `add Go home p/high i/after class` is the same as executing `add Go home i/after class p/high`).
    - Options surrounded by square brackets `[ ]` are optional.
    - Options with ellipses `...` after them can be specified multiple times (e.g. `t/Schoolwork t/CS2103`).
    - Options with `|` the pipe symbol means that either option on the left or right should be used.

- Command parameters are `UPPER_CASE` words representing required data for a command word or its options. Some common examples include:
    - `NAME`
        * This refers to the name of the task.
    - `PREFIX_INDEX`
        * Refers to the index number shown in the most recent listing.
        * Comprises of a category prefix (`e`, `d`, or `t`, representing `Event`, `Deadline` and `To-Do` categories respectively) and category index (a positive integer, e.g. `1`, `2`, `3`..).
    - `START_DATE`
        * Represents start date and time entered.
        * Time defaults 0000 hrs if no time is indicated.
        * Must be before `END_DATE`.
        * Refers to Date and Time format specification for what formats are accepted.
    - `END_DATE`
        * Represents end date and time entered.
        * Time defaults 2359 hrs if no time is indicated.
        * Must be after `START_DATE`.
        * Refers to Date and Time format specification for what formats are accepted.

### Date and Time Format Specification

OneTwoDo accepts most date and time formats, including:
* 2016/12/31
* 12/31/2016
* 2016-12-31
* Dec 31st
* 31st Dec 2016
* 31st of December
* Next Tue 3pm
* Tomorrow 5:30am
* Last Wednesday 0600h
* 3 days from now

Refer to [http://natty.joestelmach.com/doc.jsp](http://natty.joestelmach.com/doc.jsp) for a full list of supported formats.

---
### Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `hellpppppp!`

---
### Adding a task: `add`

The `add` command adds your tasks and displays it in the user interface, allowing you to view them any time you want.

Format: `add NAME [s/START_DATE e/END_DATE | d/DURATION] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...` 

**Options**
> 1. `s/`: Start date and time of a task. 
> 2. `e/`: End date and time of a task. 
>     - Should not be used together with `d/`.
> 3. `d/`: Duration of a task. 
>     - Should not be used together with `e/`, and defaults to 1 hour if both `d/` and `e/` is not specified. 
>     - Represent the task duration `DURATION` using a positive integer, followed by a letter or word - `m`, `h`, `d`, `w` (for minutes, hours, days, weeks respectively), or `min`, `mins`, `hr`, `hrs`, `day`, `days`, `week` and `weeks`. (e.g. `1m`, `2h`, or `3day`)
> 4. `r/`: Recurrence interval of a task. 
>     - Should not be less than the duration of the task. 
>     - Represent the recurrence interval duration `RECUR` using a positive integer, followed by a letter or word - `m`, `h`, `d`, `w` (for minutes, hours, days, weeks respectively), or `min`, `mins`, `hr`, `hrs`, `day`, `days`, `week` and `weeks`. (e.g. `1m`, `2h`, or `3day`)
> 5. `p/`: Priority of a task. 
>     - Represent `PRIORITY` with `high`, `medium`, `low`. or `h`, `m`, `l` for their respective priorities.
> 6. `i/`: Information about a task. 
>     - A sentence to describe extra details about the task may be placed here.
> 7. `t/`: Tags for a task. 
>     - An aplhanumeric tag word helps in searching for tasks of a similar nature.

**Notes**
> * A task can have any number of tags.
> * A task cannot have `START_DATE` but no `END_DATE`.
> * A task with both `START_DATE` and `END_DATE` is an `Event`.
> * A task with no `START_DATE` but has an `END_DATE` is a `Deadline`.
> * A task with no `START_DATE` and no `END_DATE` is a `To-Do`.

**Examples**
> * You want to do your exercise daily, but starting tomorrow, at 9 everyday, to prepare for your IPPT test, and you think it is a good and important habit to have.
> 
>   `add Exercise s/tomorrow 0900 d/1h i/core and running workout r/1d t/IPPT t/goodhabits p/h` 
> 
>   Adds an "Exercise" task which recurs daily, starting from the next day at 0900 hours, and contains the details of that exercise session, the "core and running workout". It is also tagged "IPPT", "goodhabits", and has a high priority.

---
### Finding all tasks containing a keyword in their name or tag: `find`

The `find` command shows tasks which have names, tags, or information containing the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS] [s/SPECIFICITY] [f/FIELDS]...`

**Options**
> 1. `s/`: Specificity of the search. 
>    - Represent `SPECIFICITY` with:
>       - `phrase` (matches if all keywords are in the same order as field data)
>       - `word` (matches if any keyword is the same as any word in field data)
>       - `substring` (matches if any keyword is a substring of field data). 
>    - Defaults to `substring` if not specified.
> 2. `f/`: Fields for the search. 
>    - Represent `FIELDS` with `name`, `tag`, or `information` fields. 
>    - Defaults to `name`, `tag`, and `information` if not specified.

**Notes**
> * The search is case-insensitive. e.g `work` will match `Work`

**Examples**
> * `find Project Tutorial Assignment` 
> 
>   Shows any task having names, tags, or information strings which contain `Project`, `Tutorial`, or `Assignment` as substrings.

---
### Listing tasks : `list`

The `list` command shows a listing of tasks in the OneTwoDo that satisfy the given criteria.

Format: `list [s/START_DATE e/END_DATE d/DURATION] [o/ORDER] [rev/] [done/] `

**Options**
> 1. `s/`: Start Date of listing. 
>    - Only tasks occuring after this date are shown.
>    - Should be used together with either d/ or e/.
> 2. `e/`: End Date of listing. 
>    - Only tasks occuring before this date are shown.
>    - Should not be used together with d/.
> 3. `d/`: Duration from start date.
>    - Should not be used together with e/.
> 4. `o/`: Order of listing. Represent `ORDER` with:
>    - `datetime`: sort in ascending chronological order
>    - `alphabetical`: sort in alphabetical order
>    - `priority`: sorted in order of `high`, `medium`, then `low`
> 5. `rev/`: Reversed listing order.
>    - The order of the tasks in the list is reversed if this flag is used.
> 6. `done/`: Done task filter. 
>    - Only completed tasks are shown if this flag is used.

**Notes**
> * `To-do` tasks are shown regardless of `START_DATE` and `END_DATE` as they contain no dates and are thus their occurence is not constrained by them.
> * `Deadline` tasks are considered to be occurring before their `END_DATE`

**Examples**
> * `list`
> 
>   Lists all uncompleted tasks.
>  
> * `list e/03 Mar 2017`
> 
>   Lists all the tasks that has occurs before `03 Mar 2017`

---
### Editing a task : `edit`

The `edit` command updates an existing task in the OneTwoDo with new data.

Format: `edit PREFIX_INDEX [NAME] [s/START_DATE e/END_DATE d/DURATION] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...`

**Notes**
> * Edits the task at the specified `PREFIX_INDEX`.
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, all existing tags of the task will be replaced. (i.e adding of tags is not cumulative)
> * You can remove all the task's tags by typing `t/` without specifying any tags after it. 

**Examples**

> * `edit d1 t/`
> 
>    Edits the 1st task in `DEADLINE` category to remove all its tags.
>   
> * `edit t2 e/16 Dec 2017 t/project t/2103 `
> 
>    Edits the 2nd task in `To-Do` category to update `END_DATE` of task to 16 Dec 2017 and update tags to "project" and "2103".

---
### Deleting a task : `delete`

The `delete` command removes the specified task from OneTwoDo.

Format: `delete PREFIX_INDEX`

> * Deletes the task at the specified `PREFIX_INDEX`. 

**Examples**
> * `list`
>   `delete e2`
>   
>    Deletes the 2nd task under `Event` category in OneTwoDo.
>   
> * `find Assignment`
>   `delete t1`
>   
>    Deletes the 1st task under `To-Do` category in OneTwoDo.

---
### Deleting all tasks: `clear`
The `clear` command removes all tasks in OneTwoDo.

Format: `clear`

---

### Undoing actions: `undo`
The `undo` command reverts OneTwoDo to the state before the previous command was executed.

Format: `undo`

---
### Redoing actions: `redo`
The `redo` command repeats the previous command that was reversed with the `undo` command.

Format: `redo`

---
### Marking a task as completed: `done`
The `done` command archive the task at PREFIX_INDEX.

Format: `done PREFIX_INDEX`

**Examples**

> * `done t1`
> 
>    Archive 1st to-do as completed.

---
### Marking a task as uncompleted: `undone`
The `undone` command unarchive the task at PREFIX_INDEX.

Format: `undone PREFIX_INDEX`


**Examples:**
> * `undone e1`
> 
>    Unarchive 1st event as uncompleted.

---
### Saving the data: `save`

By default, OneTwoDo data is saved in a file called `todolist.xml` in the data folder. You can change the file location by providing a new file name as the parameter.

Note that changes made to OneTwoDo are automatically saved. You do not need to manually save them each time using the`save` command.

Format: `save NEW_FILE_NAME`

**Examples**

> * `save newFile.txt`
> 
>    Save the OneTwoDo data in a new file entitled `newFile.txt`. 

---
### Exiting the program : `exit`

Exits the program.

Format: `exit`


---
## FAQ

**Q**: How do I transfer my data to another Computer?
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous OneTwoDo folder.

**Q**: I tend to forget the commands available. Using the `help` command always give me too much information. How do I recall all commands quickly?
**A**: You can check the command summary located at the bottom most of the user guide.

**Q**: What type of date and time format can I use?
**A**: OneTowDo support a variety of formats. You can checkout some of the more common formats above. For complete list of format, checkout the Natty website: http://natty.joestelmach.com/doc.jsp.

**Q**: How do save my OneTwoDo data?
**A**: We will save your data automatically everytime you input.


---
## Command Summary

| Command       | Format        | Description |
| ------------- |:-------------|:-------------|
|  Help         |  `help` | Open help window |
|  Add          |  `add NAME [s/START_DATE e/END_DATE | d/DURATION] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...` | Add a task| 
|  Find         |  `find KEYWORD [MORE_KEYWORDS] [s/SPECIFICITY] [f/FIELDS]...` | Find tasks with keywords|
|  List         |  `list [s/START_DATE e/END_DATE | d/DURATION] [o/ORDER] [rev/] [done/]` | List specified tasks| 
|  Edit         |  `edit PREFIX_INDEX [NAME] [s/START_DATE e/END_DATE d/DURATION] [r/RECUR] [p/PRIORITY] [i/INFORMATION] [t/TAG]...` | Edit any specified task's information|
|  Delete       |  `delete PREFIX_INDEX` | Delete specified task |
|  Clear        |  `clear` | Clear all data in OneTwoDo|
|  Undo         |  `undo`  | Undo previous action|
|  Redo         |  `redo` | Redo previous action|
|  Done         |  `done PREFIX_INDEX` | Archive the specified task|
|  Undone       |  `undone PREFIX_INDEX` | Unarchive the specified task|
|  Save         |  `save FILE_PATH` | Save file to specified location|
|  Exit         |  `exit`  | Quit the program|
