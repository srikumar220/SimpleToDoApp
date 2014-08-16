package com.codepath.simpletodoapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static final String DB_NAME = "simple_todo";
    private static final String DB_TABLE = "todo_items";
    private static final int DB_VERSION = 1;

    private static final String[] columns = new String[] {"id", "item_text", "priority", "status", "eta"};

    private DBOpenHelper dbOpenHelper;
    private SQLiteDatabase db;

    public DBHelper (Context context) {
        this.dbOpenHelper = new DBOpenHelper(context);
        if (db == null) {
            db = this.dbOpenHelper.getWritableDatabase();
        }
    }

    public void closeDB() {
        if (db != null) {
            db.close();
            db = null;
        }
    }

    public void insertToDoItem(ToDoItem item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_text", item.getItemText());
        contentValues.put("priority", item.getPriority());
        contentValues.put("status", item.getStatus());
        contentValues.put("eta", item.getEta());
        db.insert(DB_TABLE, null, contentValues);
    }

    public void updateToDoItem(ToDoItem item) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("item_text", item.getItemText());
        contentValues.put("priority", item.getPriority());
        contentValues.put("status", item.getStatus());
        contentValues.put("eta", item.getEta());
        db.update(DB_TABLE, contentValues, "id=" + item.getId(), null);
    }

    public void deleteTodoItem(ToDoItem item) {
        db.delete(DBHelper.DB_TABLE, "id=" + item.getId(), null);
    }

    public ArrayList<ToDoItem> getToDoItems() {

        ArrayList<ToDoItem> toDoItems =  new ArrayList<ToDoItem>();

        Cursor cursor = null;

        try {
            cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
            while (cursor.moveToNext()) {
                ToDoItem item = new ToDoItem();
                item.setId(cursor.getInt(0));
                item.setItemText(cursor.getString(1));
                item.setPriority(cursor.getInt(2));
                item.setStatus(cursor.getString(3));
                item.setEta(cursor.getLong(4));

                toDoItems.add(item);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return toDoItems;
    }

    static class DBOpenHelper extends SQLiteOpenHelper {

        DBOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DB_TABLE +
                       " (id INTEGER PRIMARY KEY, item_text TEXT, priority INTEGER, status TEXT, eta INTEGER);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // nothing to do for now.
        }

        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }
    }
}
