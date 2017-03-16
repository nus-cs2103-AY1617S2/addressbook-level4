# KIT User Guide:

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `KIT.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for KIT.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all task
   * **`add`**`  do 2103 project r/waiting for john's reply d/03-03-2017 l/nus soc t/school` : <br>
     adds a task named `do 2103 project` to KIT.
   * **`delete`**` 3` : deletes the task with index 3 shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1.1 Viewing help : `help`

If you are not sure where to start, take a sneakpeek at the help sheet!

Format: `help`

> Help brings up user guide in another window

### 2.1.2 Viewing quick overview of command formats : `helpf`

Format: `helpf`, `hf`

> Helpf shows a quick summary of command formats

### 2.2 Adding a task: `add`

If you are ready to start exploring KIT,do it by adding a task!

Adds a task to KIT. New tasks are [not done] by default. <br>

Format: `add NAME [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`

> * If you need to type the character /, put a \ before it to avoid t/ getting recognized as tag. eg. carrot\/cabbage <br>
> * Tasks can have any number of tags (including 0).

Here are some examples:

* `add do 2103 project r/waiting for john’s reply d/03-03-2017 l/nus soc t/school`
* `add buy groceries r/cabbage and broccoli d/02-03-2017 t/personal`

* `add feed the cat\/dog t/personal t/family d/01-01/2017`


### 2.3.1	Listing of all task: `list`

If you want to view all the tasks at once, simply use the list function(s).

Description: Shows a list of all the tasks in KIT. <br>

Format: `list`, `l`, `ls`

### 2.3.2	Listing of all done task: `listdone`
Description: Shows a list of all the tasks that has been completed. <br>

Format: `listdone`, `ld`

### 2.3.3	Listing of all not done task: `listnotdone`
Description: Shows a list of all the tasks that has yet to be completed. Sorted by due date. <br>

Format: `listnotdone`, `lnd`

### 2.3.4	Listing of all task under a tag: `listtag`

KIT understands that you do not wish to mix up personal life with business, so say if you want to see all work-related tasks, just use `listtag` function.

Description: Shows a list of task by the specified tag. <br>

Format: `listtag TAGNAME`, `lt TAGNAME`

### 2.4	Marking a task as done: `done`

Congratulations!
Now that you have completed a task and would like to mark it as done, you can do so by using `done` function.

Description: Marks the task at the specified `INDEX`. <br>

Format: `done INDEX`

> * The index refers to the index number shown in the listing.
> * The index **must be a positive integer** 1, 2, 3, ...

### 2.5	Deleting a task: `delete`

If you would like to remove a task from KIT, simply use the `delete` function.

Description: Deletes the specified task from KIT. **Irreversible**. <br>

Format: `delete INDEX`

> * Deletes the task at the specified `INDEX`.
> * The index refers to the index number shown in the listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `delete 2`<br>
  Deletes the 2nd task in KIT.

* `find homework`
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.

### 2.6	Undoing last command: `undo`

In case you have executed an unintended command, you can retract it by `undo`.

Description: Undo the last command. <br>

Format: undo

### 2.7 Editing a task: `edit`

If you wish to edit the details of a current task, `edit` function allows you to do that.

Description: Edits an existing task in KIT. <br>

Format: `edit INDEX [NAME] [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`

> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 r/finish up user guide l/home`<br>
  Edits the remark and location of the 1st task to be `finish up user guide` and `home` respectively.

* `edit 2 debug add command t/`<br>
  Edits the name of the 2nd task to be `debug add command` and clears all existing tags.

### 2.8.1 Finding a task:`find`

From time to time, you might want to find a task. You can do so using `find` and providing any one keyword.

Description: Finds task whose names or remark contain **at least one** of the given keywords. <br>

Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `project` will match `Project`
> * The order of the keywords does not matter. e.g. `do project` will match `project do`
> * Both the name and remarks are searched.
> * Substrings will be matched. e.g. `project` will match `projects`
> * Task matching **at least one** keyword will be found.
    e.g. `project` will match `do project` and `do homework`

Examples:

* `find homework`<br>
  Finds `do homework`

* `find homework school`<br>
  Finds any task having names or remarks with `homework`,  or `school`

### 2.8.2 Finding with exact keyword(s):`findexact`

Description: Finds task whose name or remark matches **all** the keyword(s). <br>

Format: `findexact KEYWORD...`

> * The search is the same as to `find` except it only matches tasks that contains **all** keywords.
e.g `do project` will match `do School project` but not `do homework`

### 2.9 Clearing all task: `clear`

If you would like to remove every task of the manager, just use `clear`.

Description: Clears all entries from KIT. Will prompt a confirmation. <br>

Format: `clear`

### 2.10 Exiting the program: `exit`
Description: Exits the program. <br>

Format: `exit`

### 2.11 Specifying save location: `save`

If you wish to save the file to a custom location, you can do so using the `save` function.

Description: Specify location of save file. <br>

Format: `save PATHNAME`

### 2.12. Saving the data

You do not have to worry about saving changes, because KIT saves it for you!

In KIT, data are saved automatically after a change has been made. There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer? <br>
**A**: Copy the files to the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous KIT folder. The data file is called KIT.xml .

**Q**: Is it a desktop or web application? <br>
**A**: It’s a desktop application.

**Q**: Does it have a in-built synchronization with the real-time calendar? <br>
**A**: It has access to the time and calendar of the device on which the program is run, not necessarily the internet calendar.

**Q**: Does the application support Windows 10? <br>
**A**: Yes. The application works on Windows 7 and later.

**Q**: Will my files be saved after I close the application? <br>
**A**: Yes. Data is saved after every command, you can safely just turn off the application when you are done.

**Q**: Can i have two task with the same name? <br>
**A**: Yes, task with duplicate names are accepted.

**Q**: I accidentally created two exact same tasks, how do I remove just one of them? <br>
**A**: You can do so by typing undo if the creation of one of the tasks was the previous command, or list all the tasks and delete one of them. The other task will remain in KIT.

**Q**: Can I manually edit the storage file? <br>
**A**: Yes, it is a xml file. It is called KIT.xml . It can be found in the folder data by default. Be careful in making changes to it if you are unfamiliar with XML, the program might not start properly if the file’s format is invalid.


## 4. Command Summary

* **Help** : `help` <br>

* **Help format** : `helpf`, `hf` <br>

* **Add**  `add NAME [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`
  e.g.  `add do 2103 project r/waiting for john’s reply d/03-03-2017 l/nus soc t/school`

* **List** : `list`, `l`, `ls` <br>

* **List done** : `listdone`, `ld` <br>

* **List not done** : `listnotdone`, `lnd`<br>

* **List by tag** : `listtag TAGNAME`, `lt TAGNAME`
  E.g.  `lt school`

* **Done** : `done INDEX`
  E.g. `done 2`

* **Undo** : `undo`

* **Edit**  `edit NAME [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`
  e.g.  `edit 1 r/finish up user guide l/home`

* **Find** : `find KEYWORD [MORE_KEYWORDS]`
  e.g. `find home`

* **Find exact** : `findexact KEYWORD...`
  e.g. `findexact do homework`

* **Delete** : `delete INDEX`
   e.g. `delete 3`

* **Clear** : `clear`

* **Exit** : `exit`

* **Save location** : `save PATHNAME`
   e.g. `save C:\Program Files`
   