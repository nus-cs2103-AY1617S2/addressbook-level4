# Watodo Test Script

### Loading
1. Open Watodo by double clicking the .jar executable <br>
2. Type in command "saveas data/SampleData.xml <br>
3. Close Watodo <br>
4. Overwrite the newly created SampleData.xml with the provided SampleData.xml <br>
5. Reopen Watodo <br>

### Add Function

1. Type "add floating task" <br>
	> Result: <br>
	> New task added: floating task <br>
	> Tags: <br>

2. Type "a deadline task by/ 25 june" <br>
	> Result: <br> 
	> New task added: deadline task <br>
	> Tags: <br>
	> By: Sun 25 Jun, 11.59PM <br>
	
3. Type "add event task from/ 1 June to/ 15 June" <br>
	> Result: <br>
	> New task added: event task <br>
	> Tags: <br>
	> Start: Thu 1 Jun, 11.59PM <br>
	> By: Thu 15 Jun, 11.59PM <br>
	
4. Type "a task with tags #tag1 #tag2 #tag3" <br>
	> Result: <br>
	> New task added: task with tags <br>
	> Tags: `[tag1][tag2][tag3]`
	
5. Type "add important task #impt" <br>
6. Scroll down the right side of the screen to see task added to important tasks 	list<br>
	> Result: <br>
	> New task added: important task <br>
	> Tags: [impt]

### List Function

1. Type "list" <br>
	> Result: <br>
	> Listed all overdue tasks and tasks due tomorrow <br>
	
2. Type "list all" <br>
	> Result: <br>
	> Listed all tasks <br>

3. Type "list float" <br>
	> Result: <br>
	> Listed all floating tasks <br>

4. Type "list deadline" <br>
	> Result: <br>
	> Listed all tasks with deadlines <br>

5. Type "list event" <br>
	> Result: <br>
	> Listed all events <br>

6. Type "list #tag1" <br>
	> Result: <br>
	> Listed all tasks with the entered tag <br>

7. Type "list undone" <br>
	> Result: <br>
	> Listed all tasks that are not yet completed <br>

8. Type "list done" <br>
	> Result: <br>
	> Listed all tasks that are marked as completed <br>

9. Type "list by/ june" <br>
	> Result: <br>
	> Lists all tasks scheduled within the range of dates specified <br>

10. Type "list from/ may to/ june" <br>
	> Result: <br>
	> Lists all tasks scheduled within the range of dates specified <br>
	
### Find Function
	
1. Type "find task" <br>
	> Result: <br>
	> 5 tasks listed! <br>

2. Type "find lea" <br>
	> Result: <br>
	> 2 tasks listed! <br>

3. Type "find part time find summer" <br>
	> Result: <br>
	> 1 tasks listed! <br>
	
### Edit Function

1. Type "list all"
2. Type "edit 19 Study maths" <br>
	> Result: <br>
	> Edited Task: Study maths <br>
	> Tags: <br>

3. Type "edit 19 on/ 13 july" <br>
	> Result: <br>
	> Edited Task: Study maths <br>
	> Tags: <br>
	> By: Thu 13 Jul, 11.59PM <br>

4. Type "edit 19 from/ 12 july to/ 13 july" <br>
	> Result: <br>
	> Edited Task: Study maths <br>
	> Tags: <br>
	> Start: Wed 12 Jul, 11.59PM <br>
	> By: Thu 13 Jul, 11.59PM <br>

5. Type "edit 19 #moretag1 #moretag2" <br>
	> Result: <br>
	> Edited Task: Study maths <br>
	> Tags: `[moretag2][moretag1]` <br>
	> Start: Wed 12 Jul, 11.59PM <br>
	> By: Thu 13 Jul, 11.59PM <br>

6. Type "edit 19 #moretag1" <br>
	> Result: <br>
	> Edited Task: Study maths <br>
	> Tags: `[moretag2]` <br>
	> Start: Wed 12 Jul, 11.59PM <br>
	> By: Thu 13 Jul, 11.59PM <br>
	
1. Type "edit 19 REMOVEDATES" <br>
	> Result: <br>
	> Edited Task: Study maths <br>
	> Tags: [moretag2] <br>

### Delete Function

1. Type "list all" <br>
2. Type "delete 1" <br>
	> Result: <br>
	> Task #1 deleted: ST2334 Lecture <br>
	> Tags: [LT27] <br>
	> Start: Mon 27 Mar, 10.00AM <br>
	> By: Mon 27 Mar, 12.00PM <br>

3. Type "delete 1 2 4 5" <br>
	> Result: <br>
	> Task #5 deleted: CG2023 Lecture <br>
	> Tags: [E30601] <br>
	> Start: Tue 28 Mar, 4.00PM <br>
	> By: Tue 28 Mar, 6.00PM <br>
	> Task #4 deleted: EE2024 Lecture <br>
	> Tags: [LT6] <br>
	> Start: Tue 28 Mar, 2.00PM <br>
	> By: Tue 28 Mar, 4.00PM <br>
	> Task #2 deleted: EE2024 Lab <br>
	> Tags: [DigELab] <br>
	> Start: Tue 28 Mar, 9.00AM <br>
	> By: Tue 28 Mar, 12.00PM <br>
	> Task #1 deleted: CG2271 Lecture <br>
	> Tags: [LT19] <br>
	> Start: Mon 27 Mar, 4.00PM <br>
	> By: Mon 27 Mar, 6.00PM <br>

### Mark Function

1. Type "mark 7" <br>
	> Result: <br>
	> Task #7 completed: EE2024 <br>
	> Tags: [LT6] <br>
	> Start: Thu 30 Mar, 5.00PM <br>
	> By: Thu 30 Mar, 6.00PM <br>

2. Type "mark 8 9 10" <br>
	> Result: <br>
	> Task #10 completed: CS2103 Lecture <br>
	> Tags: [i3Aud] <br>
	> Start: Fri 31 Mar, 4.00PM <br>
	> By: Fri 31 Mar, 6.00PM <br>
	> Task #9 completed: EE2024 Tutorial <br>
	> Tags: [E30612] <br>
	> Start: Fri 31 Mar, 2.00PM <br>
	> By: Fri 31 Mar, 3.00PM <br>
	> Task #8 completed: CG2023 Lab <br>
	> Tags: [SignalLab] <br>
	> Start: Fri 31 Mar, 9.00AM <br>
	> By: Fri 31 Mar, 12.00PM <br>

### Unmark Function

1. Type "unmark 7" <br>
	> Result: <br>
	> Task #7 marked undone: EE2024 <br>
	> Tags: [LT6] <br>
	> Start: Thu 30 Mar, 5.00PM <br>
	> By: Thu 30 Mar, 6.00PM <br>

2. Type "unmark 8 9 10" <br>
	> Result: <br>
	> Task #10 marked undone: CS2103 Lecture <br>
	> Tags: [i3Aud] <br>
	> Start: Fri 31 Mar, 4.00PM <br>
	> By: Fri 31 Mar, 6.00PM <br>
	> Task #9 marked undone: EE2024 Tutorial <br>
	> Tags: [E30612] <br>
	> Start: Fri 31 Mar, 2.00PM <br>
	> By: Fri 31 Mar, 3.00PM <br>
	> Task #8 marked undone: CG2023 Lab <br>
	> Tags: [SignalLab] <br>
	> Start: Fri 31 Mar, 9.00AM <br>
	> By: Fri 31 Mar, 12.00PM <br>

### Clear Function

1. Type "clear" <br>
	> Result: <br>
	> All tasks have been cleared! <br>

### Undo Function
1. Type "list all" <br>
2. Type "add task 1" <br>
3. Type "add task 2" <br>
4. Type "edit 1 hello" <br>
5. Type "delete 2" <br>
6. Type "clear" <br>

7. Type "undo" <br>
	> Result: <br>
	> clear undo success. <br>

8. Type "undo" <br>
	> Result: <br>
	> delete undo success. <br>

9. Type "undo" <br>
	> Result: <br>
	> edit undo success. <br>

10. Type "undo" <br>
	> Result: <br>
	> add undo success. <br>

11. Type "undo" <br>
	> Result: <br>
	> add undo success. <br>

12. Type "undo" <br>
	> Result: <br>
	> clear undo success. <br>

### Redo Function

1. Type "redo" <br>
	> Result: <br>
	> clear redo success. <br>
	
2. Type "redo" <br>
	> Result: <br>
	> add redo success. <br>

3. Type "redo" <br>
	> Result: <br>
	> add redo success. <br>

4. Type "redo" <br>
	> Result: <br>
	> edit redo success. <br>

5. Type "redo" <br>
	> Result: <br>
	> delete redo success. <br>

6. Type "redo" <br>
	> Result: <br>
	> clear redo success. <br>

7. Type "redo" <br>
	> Result: <br>
	> No command left to redo. <br>

### Saveas Function

1. Type "saveas data/testSave.xml" <br>
	> Result: <br>
	> Storage file location moved to data/testSave.xml <br>

### Viewfile Function

1. Type "viewfile" <br>
	> Result: <br>
	> Storage file is currently located at data/testSave.xml <br>

### Shortcut Function

1. Type "shortcut + clear wipe" <br>
	> Result: <br>
	> New shortcut key added: wipe->clear <br>

2. Type "wipe" <br>
	> Result: <br>
	> All tasks have been cleared! <br>

3. Type "shortcut - clear wipe" <br>
	> Result: <br>
	> Existing shortcut key deleted: wipe->clear <br>

4. Type "wipe" <br>
	> Result: <br>
	> Unknown command <br>

5. Type "shortcut + add hellomynamejeff" <br>
6. Type "viewshortcuts" <br>
	> Result: <br>
	> add: [add, a, hellomynamejeff] <br>
	> edit: [e, edit] <br>
	> ... <br>
	> ... <br>
	> ... <br>
	> All shortcuts shown.<br>

### Exit Function

1. Type "exit" <br>
	> Result: <br>
	> Watodo closes <br>

