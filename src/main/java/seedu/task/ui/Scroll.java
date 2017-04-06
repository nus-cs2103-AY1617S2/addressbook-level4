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

    /*
     * Creates the scroll from the tasklistview
     * If the listview is too short to contain a scrollbar,
     * null is returned.
     */
    public Scroll(ListView<ReadOnlyTask> taskListView) {
        Node n = taskListView.lookup(".scroll-bar");
        if (n == null) {
            return;
        }
        if (n instanceof ScrollBar) {
            scrollBar = (ScrollBar) n;
        }
    }

    /*
     * Scrolls down the list view
     * if there is any scrollbar to be found.
     */
    public void scrollDown(ListView<ReadOnlyTask> taskListView) {
        if (scrollBar == null) {
            Node n = taskListView.lookup(".scroll-bar");
            if (n == null) {
                return;
            }
            if (n instanceof ScrollBar) {
                scrollBar = (ScrollBar) n;
            }
        }
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
    }

    /*
     * Scrolls up the list view if there is any scrollbar
     * that can be found.
     */
    public void scrollUp(ListView<ReadOnlyTask> taskListView) {
        if (scrollBar == null) {
            Node n = taskListView.lookup(".scroll-bar");
            if (n == null) {
                return;
            }
            if (n instanceof ScrollBar) {
                scrollBar = (ScrollBar) n;
            }
        }
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
    }

    /*
     * Gets the value of the scroll, returns -1 if the listview
     * is too short to contain a scrollbar.
     */
    public double getScrollValue(ListView<ReadOnlyTask> taskListView) {
        if (scrollBar == null) {
            Node n = taskListView.lookup(".scroll-bar");
            if (n == null) {
                return -1;
            }
            if (n instanceof ScrollBar) {
                scrollBar = (ScrollBar) n;
            }
        }

        return scrollBar.getValue();
    }

}
