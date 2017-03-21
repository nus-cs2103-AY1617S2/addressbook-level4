package seedu.task.model.chat;

import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ChatList implements Iterable<Chat> {
    private ObservableList<Chat> internalList = FXCollections.observableArrayList();

    @Override
    public Iterator<Chat> iterator() {
        return internalList.iterator();
    }

    /**
     * Adds a chat to the list.
     *
     */
    public void add(Chat toAdd) {
        assert toAdd != null;
        internalList.add(toAdd);
    }

    public ObservableList<Chat> asObservableList() {
        return internalList;
    }

    public int size() {
        // TODO Auto-generated method stub
        return internalList.size();
    }

}
