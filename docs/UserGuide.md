# Typed - User Guide

By : `Typedwriters`  &nbsp;&nbsp;&nbsp;&nbsp; Since: `March 2017`  &nbsp;&nbsp;&nbsp;&nbsp; Licence: `MIT`

---

1. [Quick Start](#quick-start)
2. [Features](#features)
3. [FAQ](#faq)
4. [Command Summary](#command-summary)

## 1. Quick Start

0. Ensure you have Java version `1.8.0_60` or later installed in your Computer.<br>

   > Having any Java 8 version is not enough. <br>
   > This app will not work with earlier versions of Java 8.

1. Download the latest `typed.jar` from the [releases](../../../releases) tab.
2. Copy the file to the folder you want to use as the home folder for Typed.
3. Double-click the file to start the app. The "Welcome" screen should appear in a few seconds.
   > <img src="images/StartingUI.png" width="600">

4. Upcoming events and tasks would then be shown on the screen. These are tasks, that are not yet completed and due today, or overdue. Events that are upcoming today would also be shown on the screen. Press <kbd>Enter</kbd> to continue to the default home screen.  
   > <img src="images/Ui.png" width="600"> <br>
   Today's screen <br> <br>
   > <img src="images/Ui.png" width="600"> <br>
   Default Typed screen
5. Type the command in the command box and press <kbd>Enter</kbd> to execute it. <br>
   e.g. typing **`help`** and pressing <kbd>Enter</kbd> will open the help window.
5. Some example commands you can try:
   * **`list`** : lists all tasks
   * **`add`**` email boss by tomorrow ` :
     adds a task to be completed by tomorrow.
   * **`delete`**` 3` : deletes the 3rd task shown in the current list
   * **`exit`** : exits the app
6. Refer to the [Features](#features) section below for details of each command.<br>


## 2. Features #TODO: fix

> **Command Format**
>
> * Words in `UPPER_CASE` are the parameters.
> * Words in `lower_case` are the exact strings.
> * Items in `SQUARE_BRACKETS` are optional.
> * Items with `|` indicates either or field.
> * Items with `...` after them can have multiple instances.

### 2.1. Viewing help : `help`

Format: `help [COMMAND]`

> `help [COMMAND]` shows a more detailed explanation of the command e.g. `help add` <br>
> Help is also shown if you enter an incorrect command e.g. `abcd`

### 2.2. Adding a task or event: `add`

Adds a task or event to Typed<br>
Format: `add TASK [by|every|from|now] [DATE] [to] [DATE] [#/tags]...`

> Tasks and events can have any number of tags (including 0)

Examples:

* `add read the little prince`
* `add training camp from 2 May to 5 May`

### 2.3. Listing all persons : `list`

Shows a list of all persons in the address book.<br>
Format: `list`

### 2.4. Editing a person : `edit`

Edits an existing person in the address book.<br>
Format: `edit INDEX [NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]...`

> * Edits the person at the specified `INDEX`.
    The index refers to the index number shown in the last person listing.<br>
    The index **must be a positive integer** 1, 2, 3, ...
> * At least one of the optional fields must be provided.
> * Existing values will be updated to the input values.
> * When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
> * You can remove all the person's tags by typing `t/` without specifying any tags after it. 

Examples:

* `edit 1 p/91234567 e/johndoe@yahoo.com`<br>
  Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@yahoo.com` respectively.

* `edit 2 Betsy Crower t/`<br>
  Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### 2.5. Finding all persons containing any keyword in their name: `find`

Finds persons whose names contain any of the given keywords.<br>
Format: `find KEYWORD [MORE_KEYWORDS]`

> * The search is case sensitive. e.g `hans` will not match `Hans`
> * The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
> * Only the name is searched.
> * Only full words will be matched e.g. `Han` will not match `Hans`
> * Persons matching at least one keyword will be returned (i.e. `OR` search).
    e.g. `Hans` will match `Hans Bo`

Examples:

* `find John`<br>
  Returns `John Doe` but not `john`
* `find Betsy Tim John`<br>
  Returns Any person having names `Betsy`, `Tim`, or `John`

### 2.6. Deleting a person : `delete`

Deletes the specified person from the address book. Irreversible.<br>
Format: `delete INDEX`

> Deletes the person at the specified `INDEX`. <br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `delete 2`<br>
  Deletes the 2nd person in the address book.
* `find Betsy`<br>
  `delete 1`<br>
  Deletes the 1st person in the results of the `find` command.

### 2.7. Select a person : `select`

Selects the person identified by the index number used in the last person listing.<br>
Format: `select INDEX`

> Selects the person and loads the Google search page the person at the specified `INDEX`.<br>
> The index refers to the index number shown in the most recent listing.<br>
> The index **must be a positive integer** 1, 2, 3, ...

Examples:

* `list`<br>
  `select 2`<br>
  Selects the 2nd person in the address book.
* `find Betsy` <br>
  `select 1`<br>
  Selects the 1st person in the results of the `find` command.

### 2.8. Clearing all entries : `clear`

Clears all entries from the address book.<br>
Format: `clear`

### 2.9. Exiting the program : `exit`

Exits the program.<br>
Format: `exit`

### 2.10. Saving the data

Address book data are saved in the hard disk automatically after any command that changes the data.<br>
There is no need to save manually.

## 3. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with
       the file that contains the data of your previous Typed folder.
       
**Q**: How do I backup my data to another location?<br>
**A**: Copy the saved Typed file to any desired location of your choice. Alternatively, 
       use our in-built backup feature as described in the [Features](#features) section above.<br>
       
**Q**: Is my personal data and information kept secure from the eyes of others (including the dev team)?<br>
**A**: Yes! Everything you enter into Typed is only stored on your local machine in your specified storage file.<br>

## 4. Command Summary

#IGNORE: sample
* **Add**  `add NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]...` <br>
  e.g. `add James Ho p/22224444 e/jamesho@gmail.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
#ENDIGNORE
 
* **Add**  `add TASK [by|every|from|on] [date] [to] [date] [#/tags]...` #TODO: fix <br> 
  e.g. `add read the little prince` <br>
  e.g. `add push git commit by next wednesday` <br>
  e.g. `add write blog post every day #/hobby` <br>
  e.g. `add meet boss every monday` <br>
  e.g. `add release paycheck every month` <br>
  e.g. `add my birthday every year` <br>
  e.g. `add training camp from 2 May to 5 May` <br>
  e.g. `add drinks with client tomorrow from 1PM to 2PM` <br>
  e.g. `add clean room on Sunday` <br>

* **Complete**  `complete INDEX [all|to|,] [INDEX] ...` #TODO: fix <br>
  e.g. `complete 2` <br>
  e.g. `complete 2 to 10` <br>
  e.g. `complete 1,3,5` <br>
  e.g. `complete 1, 3, 5 to 10` <br>
  e.g. `complete all` <br>

* **Delete** : `delete INDEX [all|to|,] [INDEX] ...` #TODO: fix <br>
  e.g. `delete 2` <br>
  e.g. `delete 2 to 10` <br>
  e.g. `delete 1,3,5` <br>
  e.g. `delete 1, 3, 5 to 10` <br>
  e.g. `delete all` <br>

* **Edit** : `edit INDEX ...` #TODO: fix <br>
  e.g. `edit 1 by following Saturday` <br>
  e.g. `edit 2 on 2 May` <br>
  e.g. `edit 4 from 13:00 to 5pm` <br>
  e.g. `edit 3 every Wednesday` <br>
  e.g. `edit 7 play mousehunt` <br>
  e.g. `edit 5 #work` <br>
  e.g. `edit 6 -#supplier` <br>

* **Find** : `find KEYWORD|TAG [MORE_KEYWORDS|MORE_TAGS] ...` <br> 
  e.g. `find johnny depp` <br>
  e.g. `find boss #work` <br>
  e.g. `find #forever #alone` <br>
  
* **Help** : `help` <br>
  e.g. help <br>
  
* **History** : `history` <br>
  e.g. `history` <br>
  
* **List** : `list TYPE` <br>
  e.g. `list` <br>
  e.g. `list all` <br>
  e.g. `list undone` <br>
  e.g. `list done` <br>
  e.g. `list events` <br>

* **Redo** : `redo [INDEX|all]` #TODO: fix <br>
  e.g.`redo` <br>
  e.g.`redo 5` <br>
  e.g.`redo all` <br>

* **Save** : `save FILENAME` <br>
  e.g.`save newfile.txt` <br>
  
* **Quit** : `quit` <br>
  e.g.`quit` <br>
  
* **Undo** : `undo [INDEX|all]` #TODO: fix <br>
  e.g.`undo` <br>
  e.g.`undo 5` <br>
  e.g.`undo all` <br>
