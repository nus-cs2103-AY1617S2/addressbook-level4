1. Unzip the folder.
2. Locate the directory that contains [W14-B4][ezDo].jar, SampleData.xml and TestScript.md.
3. Create a folder named 'data' in this directory.
4. Move sampleTask.xml into the 'data' folder.
5. Rename sampleTask.xml to ezDo.xml.
6. Return back to the directory that contains [W14-B4][ezDo].jar and TestScript.md.
7. Rename [W14-B4][ezDo].jar to ezDo.jar.
8. Run ezDo.jar.

Initially, ezDo sorts tasks lexicographically by name.


<br>


Input:
sort n d
Output:
ezDo is now sorted alphabetically in descending order.


<br>


Input:
sort n a
Output:
ezDo is now sorted alphabetically in ascending order.


<br>


Input:
****add zebra sightseeing****

Description Box response:
****"New task added: zebra sightseeing"****

- 'zebra sightseeing' is now added as index 51 as ezDo will sort added tasks according to your last sort criteria. ezDo will scroll to the newly added task.


<br>


Input:
****add aaron's birthday****

Description Box response:
****"New task added: aaron's birthday"****
- Added at index 1


<br>


Input:
****add crash boss's meeting****

Description Box response:
****"New task added: crash boss's meeting"****
- Added at index 24

<br>

Input:
****sort p a****

Description Box response:
****Sorted all tasks.****

- Will scroll to index 1 and sort by priority in ascending order

<br>
Input:
****sort p d****

Description Box reponse:
****Sorted all tasks.****

- Will scroll to index 1 and sort by priority in descending order


<br>


Input:
****add alice's housewarming p/3****

Description Box response:
****New task added: alice's housewarming | Priority: 3****

- Added at index 6

<br>
Input:
****add buy phone p/1****

Description Box response:
****New task added: buy phone | Priority: 1****

- Added at index 32


<br>


Input:
****add buy camera p/2****

Description Box response:
****New task added: buy camera | Priority: 2****

- Added at index 16



<br>

Input:
****add buy snacks****

Description Box response:
****New task added: buy snacks****

- Added at index 57


<br>


Input:
****sort s a****

Description Box response:
****Sorted all tasks.****

- Scrolls to index 1 and sorts by start date from earliest to latest.


<br>


Input:
****sort s d****

Description Box response:
****Sorted all tasks.****

- Scrolls to index 1 and sorts by start date from latest to earliest.


<br>


Input:
****add buy exit sign s/06/05/2017 00:29****

Description Box response:
****New task added: buy exit sign | StartDate: 06/05/2017 00:29****

- Added at index 3

<br>

Input:
****add buy headphones s/06/04/2016 00:00****

Description Box response:
****New task added: buy headphones | StartDate: 06/04/2016 00:00****

- Added at index 27


<br>


Input:
****add start a fight s/20/04/2017 23:00****

Description Box response:
****New task added: start a fight | StartDate: 20/04/2017 23:00****

- Index 11


<br>


Input:
****sort d a****

Description Box response:
****Sorted all tasks.****

- Scrolls to index 1 and sorts by due date from earliest to latest.


<br>

Input:
****sort d d****

Description Box response:
****Sorted all tasks.****

- Scrolls to index 1 and sorts by due date from latest to earliest.

<br>

Input:
****add master deagle d/20/05/2017 12:00****

Description Box response:
****New task added: master deagle | DueDate: 20/05/2017 12:00****

- Added at index 1


<br>


Input:
****add master m249 d/04/04/2014 12:00****

Description Box response:
****New task added: master m249 | DueDate: 04/04/2017 12:00****

- Added at index 28

<br>

Input:
****add master knife d/28/04/2017 13:37****

Description Box response:
****New task added: master knife | DueDate: 28/04/2017 13:37****

- Added at Index 9

<br>

Input:
****sort n****

Description Box response:
****Sorted all tasks.****

- Will scroll to index 1 and sort by name in ascending order (Defaults to ascending order as no order (a or d) was specified).

<br>

Input:
****edit 1 zap bugs****

Description Box response:
****Edited Task: zap bugs****

- Scrolls to the task's index at 62.

Input:
****edit 60 eat mcdonalds****

Description Box response:
****Edited Task: eat mcdonalds | Priority: 1 | DueDate: 01/05/2017 23:28 | Tags: [dota2]****

- Scrolls to the task's index at 30.

<br>

Input:
****sort p****

Description Box response:
****Sorted all tasks.****

- Will scroll to index 1 and sort by priority in ascending order.

Input:
****edit 1 p/3****

Description Box response:
****Edited Task: buy chair | Priority: 3 | DueDate: 05/04/2017 23:23 | Tags: [dontForget]****

- Scrolls to the task's index at 27. The leftmost color bar (priority) is now green.

<br>

Input:
****edit 28 p/1****

Description Box response:
****Edited Task: alice's housewarming | Priority: 1****

- Scrolls to the task's index at 17. The leftmost color bar(priority) is now red.

<br>

Input:
****edit 1 p/2****

Description Box response:
****Edited Task: buy charger | Priority: 2 | StartDate: 15/04/2017 23:24 | Tags: [dontForget]****

- Scrolls to the task's index at 17. The leftmost color bar(priority) is now orange.


Input:
****sort s****

Description Box response:
****Sorted all tasks.****

- Will scroll to index 1 and sort by start date from earliest to latest.

<br>

Input:
****edit 1 s/06/04/2018 13:37****
Description Box response:

****Edited Task: buy headphones | StartDate: 06/04/2018 13:37****

- Scrolls to the task's index at 38.

<br>

Input:
****edit 37 s/05/04/2016 13:37****

Description Box response:
****Edited Task: buy water bottle | StartDate: 05/04/2016 13:37****

- Scrolls to the task's index at 1.
<br>

Input:
****sort d****

Description Box response:
****Sorted all tasks.****

- Will scroll to index 1 and sorts by due date from earliest to latest.

<br>

Input:
****edit 1 d/04/04/2019 12:00****

Description Box response:
****Edited Task: master m249 | DueDate: 04/04/2019 12:00****

- Scrolls to the task's index at 29.

<br>

Input:
****edit 28 d/27/11/1994 07:00****

Description Box response:
****Edited Task: master deagle | DueDate: 27/11/1994 07:00****

- Scrolls to the task's index at 1.

<br>

Input:
****sort n****

Description Box response:
****Sorted all tasks.****

- Will scroll to index 1 and tasks are sorted alphabetically in ascending order.

<br>

Input:
****done 1****

Description Box response:
****Done task: [alice's housewarming | Priority: 1]****

- Task 1 is marked as done and moved to the done list (done list not shown).

<br>

Input:
****done****

Description Box response:
****Done tasks listed****

- There is 1 task present in done list.

<br>

Input:
****done 1****

Description Box response:
****Undone task: [alice's housewarming | Priority: 1]****

- The done list is now empty because the task is moved back to undone list.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- The view returns to the undone list.

<br>

Input:
****done 1 2 3****

Description Box response:
****Done task: [alice's housewarming | Priority: 1, buy camera | Priority: 2, buy capsicums | Priority: 2 | StartDate: 07/04/2017 23:25 | Tags: [shopping]]****

- Tasks 1, 2 and 3 are marked as done and moved to the done list.

<br>

Input:
****d****

Description Box response:
****Done tasks listed****

- Goes to the done list. (d is shortcut for done)
- There are 3 tasks present in done list.

<br>

Input:
****d 1 2 3****

Description Box response:
****Undone task: [alice's housewarming | Priority: 1, buy camera | Priority: 2, buy capsicums | Priority: 2 | StartDate: 07/04/2017 23:25 | Tags: [shopping]]****

- Tasks 1, 2 and 3 are marked as undone. Done list is now empty.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- The view returns to the undone list.

<br>

Input:
****add Amy's tuition s/08/04/2017 14:00 d/08/04/2017 16:00 f/weekly****

Description Box response:
****New task added: Amy's tuition | StartDate: 08/04/2017 14:00 | DueDate: 08/04/2017 16:00 | Recur: weekly****

- Added at index 2

<br>

Input:
****d 2****

Description Box response:
****Done task: [Amy's tuition | StartDate: 08/04/2017 14:00 | DueDate: 08/04/2017 16:00]****

- Marks the recurring task at index 2 as done, and updates the recurring task. The new start date of Amy's tuition is 15/04/2017 14:00 and the new due date is 15/04/2017 16:00.

<br>

Input:
****done****

Description Box response:
****Done tasks listed****

- The view switches over to the done list. There is only one task present which is Amy's tuition, and it is not recurring.

<br>

Input:
****d 1****

Description Box response:
****Undone task: [Amy's tuition | StartDate: 08/04/2017 14:00 | DueDate: 08/04/2017 16:00]****

- Task 1 is marked as undone and removed from the done list.

<br>

Input:
****l****

Description Box response:
****Listed all tasks****

- The view returns to the undone list. Index 2 is a non recurring instance of amy's tuition. Index 3 is the original recurring task.

<br>

Input:
****kill 2****

Description Box response:
****Deleted Task: [Amy's tuition | StartDate: 08/04/2017 14:00 | DueDate: 08/04/2017 16:00]****

- Task at index 2 is deleted.

<br>

Input:
****k 1 2 3****

Description Box response:
****Deleted Task: [alice's housewarming | Priority: 1, Amy's tuition | StartDate: 15/04/2017 14:00 | DueDate: 15/04/2017 16:00 | Recur: weekly, buy camera | Priority: 2]****

- The tasks at index 1, 2 and 3 are deleted.
<br>

Input:
****find buy****

Description Box response:
****26 tasks listed!****

- 26 tasks containing the word "buy" are displayed.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone task listed.

<br>

Input:
****find t/shopping****

Description Box response:
****10 tasks listed!****

- 10 tasks with tag 'shopping' are displayed.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone task listed

<br>

Input:
****find d/after 07/04/2017****

Description Box response:
****26 tasks listed!****

- 26 tasks with due date on and after 07/04/2017 are shown.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are listed.

<br>

Input:
****find d/before 07/04/2017****

Description Box response:
****3 tasks listed!****

- 3 tasks with due date on and before 07/04/2017 are shown.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are listed.

<br>

Input:
****find t/shopping t/ntuc****

Description Box response:
****3 tasks listed!****

- 3 tasks with both the tags 'shopping' and 'ntuc' are shown.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are listed.

<br>

Input:
****find t/market t/shopping****

Description Box response:
****1 tasks listed!****

- 1 task with both the tags "market" and "shopping" are shown.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are listed.

<br>

Input:
****find p/1****

Description Box response:
****15 tasks listed!****

- 15 tasks with priority 1 are shown.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are list.

<br>

Input:
****find p/2****

Description Box response:
****10 tasks listed!****

- 10 tasks with priority 2 are shown.


<br>


Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are listed.


<br>


Input:
****find p/3****

Description Box response:
****6 tasks listed!****

- 6 tasks with priority 3 are shown.

<br>

Input:
****list****

Description Box response:
****Listed all tasks****

- All undone tasks are listed.

<br>

Input:
****sort n****

Description Box response:
****Sorted all tasks.****

- Tasks are sorted alphabetically in ascending order.


<br>


Input:
****add a1****

Description Box response:
****New task added: a1****

- Added at index 1

<br>

Input:
****add a2****

Description Box response:
****New task added: a2****

- Added at index 2

<br>
Input:
****add a3****

Description Box response:
****New task added: a3****

- Added at index 3

<br>
Input:
****add a4****

Description Box response:
****New task added: a4****

- Added at index 4

<br>
Input:
****add a5****

Description Box response:
****New task added: a5****

- Added at index 5

<br>

Input:
****add a6****

Description Box response:
****New task added: a6****

- Added at index 6

<br>

Input:
****undo****

Description Box response:
****Previous undoable command has been undone!****

- Index 6 is now buy capsicums instead of a6.

<br>

Input:
****undo****

Description Box response:
****Previous undoable command has been undone!****

- Index 5 is now buy capsicums instead of a5.

<br>

Input:
****undo****

Description Box response:
****Previous undoable command has been undone!****

- Index 4 is now buy capsicums instead of a4.

<br>

Input:
****undo****

Description Box response:
****Previous undoable command has been undone!****

- Index 3 is now buy capsicums instead of a3

<br>

Input:
****u****

Description Box response:
****Previous undoable command has been undone!****

- Index 2 is now buy capsicums instead of a2

<br>

Input:
****undo****

Description Box response:
****There is no previous undoable command!****

- ezDo only supports undoing up to 5 most recent action.

<br>

Input:
****redo****

Description Box response:
****Last command undone has been redone!****

- Task a2 comes back at index 2.

<br>

Input:
****redo****

Description Box response:
****Last command undone has been redone!****

- Task a3 comes back at index 3.

<br>

Input:
****redo****

Description Box response:
****Last command undone has been redone!****

- Task a4 comes back at index 4.

<br>
Input:
****redo****

Description Box response:
****Last command undone has been redone!****

- Task a5 comes back at index 5.

<br>
Input:
****redo****

Description Box response:
****Last command undone has been redone!****

- Task a6 comes back at index 6.

<br>

Input:
****redo****

Description Box response:
****There is no redoable command!****

- ezDo only supports redoing up to 5 most recent undo as well.

<br>

Input:
****save ./****

Description Box response:
****New Save Location: .//ezDo.xml****

- The file is moved to the same folder as ezDo.jar. The file no longer exists at the old location at ./data


<br>


Input:
****save ./data****

Description Box response:
****New Save Location: ./data/ezDo.xml****

- The file is moved to the ./data folder relative to ezDo.jar. The file no longer exists at the old location at ./

<br>

Input:
****k 1 2 3 4 5 6****

Description Box response:
****Deleted Task: [a1, a2, a3, a4, a5, a6]****

- Tasks at index 1, 2, 3, 4, 5, and 6 are deleted.

<br>

Input:
****sort n****

Description Box response:
****Sorted all tasks.****

- The tasks are sorted by lexicographical order.


<br>


Input:
****quit****

Response:
****The app closes with no warning.****


1. Start ezDo again.
2. The order is just the same as you left it (name ascending).

<br>

Input:
****sort p****

Description Box response:
****Sorted all tasks.****

- Tasks are sorted by priority in ascending order.

<br>


Input:
****q****

Response:
****The app closes with no warnings.****



1. Start ezDo again.

2. The order is just the same as you left it (priority ascending).

<br>

Input:
****sort s****

Description Box response:
****Sorted all tasks.****

- The tasks are sorted by start date in ascending order.

<br>


Input:
****q****

Response:
****The app closes with no warnings.****


1. Start ezDo again.

2. The order is just the same as you left it (start date ascending).

<br>

Input:
****sort d d****

Description Box response:
****Sorted all tasks.****

- The tasks are sorted by due date in descending order.

<br>


Input:
****q****

Response:
****The app closes with no warnings.****


1. Start ezDo again.

2. The order is just the same as you left it (due date descending).

<br>



Input:
****clear****

Description Box response:
****EzDo has been cleared!****

- ezDo is cleared.


<br>


Input:
****alias undo helpimtwelve****

Description Box response:
****Successfully aliased command****

- You can now Undo commands with 'helpimtwelve'


<br>


Input:
****helpimtwelve****

Description Box response:
****Previous undoable command has been undone!****

- The previous command "clear" has been undone.


<br>


Input:
****alias reset****

Description Box response:
****Successfully reset aliases****

- All aliases are removed.


<br>


Input:
****helpimtwelve****

Description Box response:
****Unknown command****

- helpimtwelve is no longer recognised because aliases were reset.

<br>


Input:
****help****

Description Box response:
****Opened help window.****

- A help window appears, which is linked to the user guide.


<br>


Input:
****select 1****
Description Box response:
****Selected Task: [1]****

- The task at index 1 is marked as ongoing, as indicated by the hammer icon.


<br>


Input:
****select 5 3 4****
Description Box response:
****Selected Task: [5, 3, 4]****

- The tasks at indexes 3, 4 and 5 are marked as ongoing, as indicated by the hammer icons.



<br>


Input:
****select 1****
Description Box response:
****Selected Task: [1]****

- The task at index 1 is marked as no longer ongoing, as indicated by the lack of a hammer icon.


<br>


Input:
****select 3 4 5****
Description Box response:
****Selected Task: [3, 4, 5]****

- The tasks at indexes 3, 4, 5 are marked as no longer ongoing, as indicated by the lack of hammer icons.


<br>


Input:
****done 1****

Description Box response:
****Done task: [buy capsicums | Priority: 2 | StartDate: 07/04/2017 23:25 | Tags: [shopping]]****

- The done list is now empty because the task is moved back to undone list.

<br>

Input:
****done****

Description Box response:
****Done tasks listed****

- There is 1 task present in done list.

<br>

Input:
****select 1****
Description Box response:
****The task has a status marked as done.****

- An error message is shown as done tasks cannot be marked as ongoing.

<br>

Input:
****quit****
Description Box response:

- ezDo exits.


This concludes the test script.


