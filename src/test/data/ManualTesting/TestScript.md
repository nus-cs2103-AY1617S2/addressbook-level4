# Test Script

This is the test script to test Doist.

## Loading the sample data

1. Run the command `load src/test/data/ManualTesting/SampleData.xml`  
> Your sample tasks are loaded!

## View Help
1. 'help'
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

## List coommand
1. `list`
> Doist brings up a list of all tasks saved.

2. `list overdue`
> Doist will list all events and deadlines that are past their end times, and unfinished.

3. `list \under health \from Jan 1st \to Dec 31st`
> Doist will list all events and deadlines whose time periods overlap with the specified period, and have `health` as a tag. <br>
> The time period of the tasks listed will either partially overlap, or be completely contained in the specified interval.

## Find command
1. `find milk`
> In the case that the previous tasks have been added, Doist will bring up all the tasks that match the keyword 'milk'. <br>
> In general, Doist will try to list all the tasks whose descriptions are exact/close matches to the query. <br>
> The longer the query, the better the chances of partial matches being listed.

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

## List_tag command
1. `list_tag`
> This will bring up a list of all the tags that the user has used.

## Undo and Redo command
1. `undo`
> This command will undo the last mutating command entered by the user, if possible. <br>
> The feedback box will display an appriopriate message to indicate the result of the command. <br>
> If needed, the list displayed will get updated automatically to reflect this change.

2. `redo`
> This command will redo the last mutating command entered by the user, if possible. <br>
> The feedback box will display an appriopriate message to indicate the result of the command. <br>
> If needed, the list displayed will get updated automatically to reflect this change.

## Clear command
1. `clear`
> This command will delete all tasks in stored. <br>
> The list displayed will get updated automatically to reflect this change.











