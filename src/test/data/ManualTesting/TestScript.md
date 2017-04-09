# Test Script

This is the test script to test Doist.

## Loading the sample data

1. Run the command `load src/test/data/ManualTesting/SampleData.xml`  
> Your sample tasks are loaded!

## View Help
1. `help`
> A window with useful information will be shown, with all the command formats and usage.

## Add command
1. `add buy milk`
> A floating task is added without any associated timings.

2. `add buy milk \by 6pm`
> A task with a deadline is added.

3. `add go to the gym \from tomorrow 5pm \to tomorrow 7pm \under health`
> An event with these timings is added
> The event also has a tag `health` associated with it.

4. `add buy milk \as important`
> A floating task is added with a priority `important`.

## Special
1. When you start typing a command (or type anything), Doist will provide you with command word suggestions. Press <kbd>TAB</kbd> to accept the first suggestion.

2. Use <kbd>↑</kbd> and <kbd>↓</kbd> to scroll through previously typed commands.

3. Use <kbd>Esc</kbd> to enter navigation mode. In navigation mode, you can use <kbd>j</kbd> and <kbd>k</kbd> to scroll through the tasks up and down respectively. To exit navigation mode and to resume normal typing, press <kbd>Esc</kbd> again.

## List command
1. `list`
> Doist brings up a list of all tasks saved.

2. `list overdue`
> Doist will list all events and deadlines that are past their end times, and unfinished.

3. `list \under health \from Jan 1st \to Dec 31st`
> Doist will list all events and deadlines whose time periods overlap with the specified period, and have `health` as a tag. <br>
> The time period of the tasks listed will either partially overlap, or be completely contained in the specified interval.

## Sort command
1. `sort alpha priority time`
> The tasks are now sorted by alphabetical order, then by priority and finally by time.

## Find command
1. `find milk`
> In the case that the previous tasks have been added, Doist will bring up all the tasks that match the keyword 'milk'. <br>
> In general, Doist will try to list all the tasks whose descriptions are exact/close matches to the query. <br>
> The longer the query, the better the chances of partial matches being listed.

2. `find marron 5`
> The task 'listen to the new Maroon 5 song' will appear, as the word 'Maroon' is a close-match

Now, run the command `list` to display all tasks again.

## Delete command
1. `delete 1 2`
> This will delete the first 2 tasks displayed currently, if they exist. <br>
> The list displayed will get updated automatically to reflect this change.

2. `delete 2-4`
> This will delete the tasks numbered 2 to 4, if they exist. <br>
> The list displayed will get updated automatically to reflect this change.

## Finish command

1. `finish 1 2`
> This will mark the first 2 tasks displayed currently as finished, if they exist. <br>
> These tasks will now be displayed with a green banner, as well as a 'ticked' checkbox. <br>
> The list displayed will get updated automatically to reflect this change.

2. `finish 2-4`
> This will mark the tasks numbered 2 to 4 as finished, if they exist. <br>
> These tasks will now be displayed with a green banner, as well as a 'ticked' checkbox. <br>
> The list displayed will get updated automatically to reflect this change.

3. `unfinish 1 2-3`
> This will unmark the tasks numbered 1,2 and 3, if they exist. <br>
> These tasks will now be displayed with the appriopriate banner (red for overdue, blue for pending), and will have empty checkboxes. <br>
> The list displayed will get updated automatically to reflect this change.

## Edit command

1. `edit 1 buy egg`
> This command will update the description of the first task in the current list to "buy eggs". <br>
> The list will automatically be re-sorted, and the new list will be displayed.

2. `edit 1 buy milk \by 6pm`
> This command will update the description of the first task, as well as make it a deadline. <br>
> The list will automatically be re-sorted, and the new list will be displayed.

3. `edit 1 \under`
> This command will remove all tags associated with the first task in the list.

4. `edit 2 \from \to \as important`
> This command will make the second task in the list a floating task, and change its priority to important. <br>
> The list will automatically be re-sorted, and the new list will be displayed.

## Undo and Redo command
1. `undo` or press <kbd>Ctrl</kbd> + <kbd>Z</kbd> <br>
> This command will undo the last mutating command entered by the user, if possible. <br>
> The feedback box will display an appropriate message to indicate the result of the command. <br>
> If needed, the list displayed will get updated automatically to reflect this change.

2. `redo` or press <kbd>Ctrl</kbd> + <kbd>Y</kbd> <br>
> This command will redo the last mutating command entered by the user, if possible. <br>
> The feedback box will display an appropriate message to indicate the result of the command. <br>
> If needed, the list displayed will get updated automatically to reflect this change.

## Alias command
1. `alias a \for add`
> This command will allow `a` to be an alias for the `add` command. You can now enter `a buy milk` and a new task will be added.

2. `alias b \for add`

3. `view_alias`
> This command shows you all existing aliases in the feedback box

4. `remove_alias a`
> This command will remove `a` as an alias for `add` that was set earlier in step 1
> To prove this, you can now enter `view_alias` and check that `a` is not an alias for add

5. `reset_alias`
> This command will remove all the aliases you have set and reset to the default aliases that was set for you.
> To prove this, you can now enter `view_alias` and check that `b` is not an alias for add

## Save At command
1. `save_at /doist_data`
> This command will change the data storage location to a folder named 'doist_data' which is at the current drive (C:/)
> Notice that this is a relative path that is relative to the current drive
> This can be also seen at the lower right corner of Doist where the data storage location is shown.
> This shifts the todolist data, alias list map data and also the user prefs data.

2. `save_at C:\Users`
> This command will change the data storage location to a folder named 'Users' which is at the current drive (C:/)
> Notice that this is an absolute path

## Change themes
1. Click on `Theme` on the menu bar then click `DarkTheme`
> Doist changes to a dark theme!

## Clear command
1. `clear`
> This command will delete all tasks in stored. <br>
> The list displayed will get updated automatically to reflect this change.

## Exit
1. `exit`
> You have now exited from Doist.
