package seedu.task.ui;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import seedu.task.model.task.ReadOnlyTask;
//@@author A0142939W
public class Scroll {
    private static ScrollBar scrollBar;

    Scroll() {
        scrollBar = null;
    }

    public void scrollDown(ListView<ReadOnlyTask> taskListView) {
        Node n = taskListView.lookup(".scroll-bar");
        if (n == null) {
            System.out.println("null");
            return;
        }
        if (n instanceof ScrollBar) {
            scrollBar = (ScrollBar) n;
        }
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
        scrollBar.increment();
    }

    public void scrollUp(ListView<ReadOnlyTask> taskListView) {
        Node n = taskListView.lookup(".scroll-bar");
        if (n == null) {
            return;
        }
        if (n instanceof ScrollBar) {
            scrollBar = (ScrollBar) n;
        }
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
        scrollBar.decrement();
    }
}
