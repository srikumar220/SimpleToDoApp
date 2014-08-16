package com.codepath.simpletodoapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ToDoItem implements Parcelable {
    private int id;
    private String itemText;
    private int priority;
    private String status;  // 'open', 'completed' etc.
    private long eta;

    public ToDoItem() {
    }

    public ToDoItem(int id, String itemText, int priority, String status, long eta) {
        this.id = id;
        this.itemText = itemText;
        this.priority = priority;
        this.status = status;
        this.eta = eta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getEta() {
        return eta;
    }

    public void setEta(long eta) {
        this.eta = eta;
    }

    @Override
    public String toString() {
        return itemText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(itemText);
        dest.writeInt(priority);
        dest.writeString(status);
        dest.writeLong(eta);
    }

    public static final Parcelable.Creator<ToDoItem> CREATOR
            = new Parcelable.Creator<ToDoItem>() {
        public ToDoItem createFromParcel(Parcel in) {
            return new ToDoItem(in);
        }

        public ToDoItem[] newArray(int size) {
            return new ToDoItem[size];
        }
    };

    private ToDoItem(Parcel in) {
        id = in.readInt();
        itemText = in.readString();
        priority = in.readInt();
        status = in.readString();
        eta = in.readLong();
    }
}