package seedu.task.ui;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import seedu.task.model.task.ReadOnlyTask;
//@@author A0142939W
/**
 * This class controls the scroll bar of any listview.
 */
public class Scroll {
    private ScrollBar scrollBar;

    public Scroll() {
        scrollBar = null;
    }

    /**
     * Creates the scroll from the tasklistview
     * If the listview is too short to contain a scrollbar,
     * null is returned.
     * @param taskListView the list we want to find scroll off
     */
    public Scroll(ListView<ReadOnlyTask> taskListView) {
        scrollBar = getScrollBar(taskListView);
    }

    /**
     * Gets the scrollbar in the list view
     * @param taskListView the targeted list view to get scroll of
     * @return ScrollBar if scrollbar is found or else null
     */
    public ScrollBar getScrollBar(ListView<ReadOnlyTask> taskListView) {
        ScrollBar scrollBar = null;

        Node n = taskListView.lookup(".scroll-bar");

        if (n instanceof ScrollBar) {
            scrollBar = (ScrollBar) n;
        }

        return scrollBar;
    }

    /**
     * Scrolls down the list view
     * if there is any scrollbar to be found.
     * @param taskListView the intended ListView
     */
    public void scrollDown(ListView<ReadOnlyTask> taskListView) {
        scrollBar = getScrollBar(taskListView);

        if (scrollBar == null) {
            return;
        }

        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
    }

    /**
     * Scrolls up the list view if there is any scrollbar
     * that can be found.
     * @param taskListView the intended ListView
     */
    public void scrollUp(ListView<ReadOnlyTask> taskListView) {
        scrollBar = getScrollBar(taskListView);

        if (scrollBar == null) {
            return;
        }

        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
    }

    /**
     * Gets the value of the scroll, returns -1 if the listview
     * is too short to contain a scrollbar.
     * @param taskListView the intended ListView
     * @return the current value or the scrollbar
     */
    public double getScrollValue(ListView<ReadOnlyTask> taskListView) {
        scrollBar = getScrollBar(taskListView);

        if (scrollBar == null) {
            return -1;
        }

        return scrollBar.getValue();
    }

}
