# Test Script

This is the test script to test Doist.

## Loading the sample data

1. Run the command `load src/test/data/ManualTesting/SampleData.xml`  
> Your sample tasks are loaded!

## View Help
1. 'help'
> A window with useful information will be shown, with all the command formats and usage

## Add command
1. `add buy milk`
> A floating task is added without any associated timings

2. `add buy milk \by 6pm`
> A task with a deadline is added

3. `add go to the gym \from tomorrow 5pm \to tomorrow 7pm \under health`
> An event with these timings is added
> The event also has a tag `health` associated with it

4. `add buy milk \as important`
> A floating task is added with a priority `important`

## List coommand
1. `list`
> Doist brings up a list of all tasks saved

2. `list overdue`
> Doist will list all events and deadlines that are past their end times, and unfinished

3. `list \under health \from Jan 1st \to Dec 31st`
> Doist will list all events and deadlines whose time periods overlap with the specified period, and have `health` as a tag
> The time period of the tasks listed will either partially overlap, or be completely contained in the specified interval.
