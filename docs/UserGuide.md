# VeTo User Guide:

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick start
0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `addressbook.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for your Address Book.
3. Double-click the file to start the app. The GUI should appear in a few seconds.
   > <img src="images/Ui.png" width="600">

4. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all contacts
   * **`add`**` John Doe p/98765432 e/johnd@gmail.com a/John street, block 123, #01-01` :
     adds a contact named `John Doe` to the Address Book.
   * **`delete`**` 3` : deletes the 3rd contact shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>

## 2. Features

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `...` after them can have multiple instances.
> * Parameters can be in any order.

### 2.1. Viewing help : `help`

Format: `help`

> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2 Adding a task: `add`

Adds a task to the to do list. New tasks are [not done] by default.
Format: `add NAME [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`

> Persons can have any number of tags (including 0)

Examples:

* `add do 2103 project r/waiting for john’s reply d/03-03-2017 l/nus soc t/school`
* `add buy groceries r/cabbage and broccoli d/02-03-2017 t/personal`

### 2.3	Listing of all task: `list`
Shows a list of all the tasks in to do list.
Format: `list`, `l`, `ls`

### 2.4	Listing of all done task: `listdone`
Shows a list of all the tasks that has been completed.
Format: `listdone`, `ld`

### 2.5	Listing of all not done task: `listnotdone`
Shows a list of all the tasks that has yet to be completed. Sorted by due date?
Format: `listnotdone`, `lnd`

### 2.6	Listing of all task under a tag: `listtag`
Shows a list of task by the specified tag
Format: `listtag TAGNAME`, `lt TAGNAME`

### 2.7	Marking a task as done: `done`
Marks the task at the specified `INDEX`.
Format: `done INDEX`

> * The index refers to the index number shown in the last person listing.
> * The index **must be a positive integer** 1, 2, 3, ...

### 2.8	Deleting a task: `delete`
Deletes the task at the specified `INDEX`.
Format: delete INDEX

### 2.9	Undoing last command: `undo`
Undo the last command.
Format: undo

### 2.10 Editing a task: `edit`
Edits an existing task in the to do list
Format: edit `edit INDEX [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the task will be removed i.e adding of tags is not cumulative.
> * You can remove all the task’s tags by typing `t/` without specifying any tags after it.

Examples:

* `edit 1 r/finish up user guide l/home`<br>
  Edits the remark and location of the 1st task to be `finish up user guide` and `home` respectively.

* `edit 2 debug add command t/`<br>
  Edits the name of the 2nd task to be `debug add command` and clears all existing tags.

### 2.11 Finding a task:`find`

Finds task whose names or remark contain any of the given keywords.
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case insensitive. e.g `project` will match `Project`
> * The order of the keywords does not matter. e.g. `do project` will match `project do`
> * both the name and remarks are searched.
> * Substrings will be matched e.g. `project` will match `projects`
> * task matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `project` will match `do project`

Examples:

* `find homework`<br>
  Returns `do homework`

* `find homework school`<br>
  Returns Any task having names or remarks with `homework `,  or `school`

### 2.12 Deleting a task: `delete`
Deletes the specified task from the to do list. Irreversible.
Format: `delete INDEX`

> * Deletes the task at the specified `INDEX`.
> * The index refers to the index number shown in the most recent listing.
> * The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`
  `delete 2`<br>
  Deletes the 2nd task in the to do list.

* `find homework`
  `delete 1`<br>
  Deletes the 1st task in the results of the `find` command.


### 2.13 Clearing all task: `clear`
Clears all entries from the to do list. Will prompt a confirmation.
Format: `clear`

### 2.14 Exiting the program: `exit`
Exits the program
Format: `exit`

### 2.15 Specifying save location: `save`
Specify location of save file
Format: `save PATHNAME`

### 2.16. Saving the data
To do list data are saved in the hard disk automatically after any command that changes the data.
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?
**A**: Copy the files to the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous ‘to do list’ folder.

**Q**: Is it a desktop or web application?
**A**: It’s a desktop application.

**Q**: Does it have a in-built synchronization with the real-time calendar?
**A**: It has access to the time and calendar of the device on which the program is run, not necessarily the internet calendar.

**Q**: Does the application support Windows 10?
**A**: Yes. The application works on Windows 7 and later.

**Q**: Will my files be saved after I close the application?
**A**: Yes.

**Q**: Can i have two task with the same name?
**A**: Yes, you may.

**Q**: I accidentally created two exact same tasks, how do I remove just one of them?
**A**: You can do so by typing undo if the creation of one of the tasks was the previous command, or list all the tasks and delete one of them. The other task will remain in the to-do list.

**Q**: I accidentally deleted the wrong task. Can I undo it?
**A**: No.

**Q**: Do you have a mobile version?
**A**: No.

**Q**: Can multiple user access at the same time?
**A**: No.

**Q**: Can I manually edit the storage file?
**A**: Yes, it is a text file. It is called ‘addressbook.txt’. It can be found in the project file by default. It is formatted in XML. Be careful in making changes to it if you are unfamiliar with XML, the program might not start properly if the file’s format is invalid.

**Q**: think of more questions

## 4. Command Summary

* **Help** : `help` <br>

* **Add**  `add NAME [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`
  e.g.  `add do 2103 project r/waiting for john’s reply d/03-03-2017 l/nus soc t/school`

* **List** : `list`, `l`, `ls` <br>

* **List done** : `listnotdone`, `lnd` <br>

* **List not done** : `list`, `l`, `ls` <br>

* **List by tag** : `listtag TAGNAME`, `lt TAGNAME`
  E.g.  `lt school`

* **Done** : `done INDEX`
  E.g. `done 2`

* **Undo** : `undo`

* **Edit**  `edit NAME [r/REMARKS] [d/DEADLINE] [l/LOCATION] [t/TAG]...`
  e.g.  `edit 1 r/finish up user guide l/home`

* **Find** : `find KEYWORD [MORE_KEYWORDS]`
  e.g. `find homework`

* **Delete** : `delete INDEX`
   e.g. `delete 3`

* **Clear** : `clear`

* **Exit** : `exit`

* **Save location** : `save PATHNAME`
   e.g. `save C:\Program Files`


