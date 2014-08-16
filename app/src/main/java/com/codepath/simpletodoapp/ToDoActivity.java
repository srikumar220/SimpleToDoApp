package com.codepath.simpletodoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.codepath.simpletodoapp.data.DBHelper;
import com.codepath.simpletodoapp.data.ToDoItem;
import java.util.ArrayList;

public class ToDoActivity extends Activity {

    private ListView listView;
    private ArrayList<ToDoItem> items;
    private ArrayAdapter<ToDoItem> itemsAdapter;
    private EditText etNewItem;
    private DBHelper dbHelper;

    private static final int TODO_ACTTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        dbHelper = new DBHelper(this);
        readItems();

        listView = (ListView) findViewById(R.id.lvItems);
        itemsAdapter = new ArrayAdapter<ToDoItem>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem item = items.remove(position);
                dbHelper.deleteTodoItem(item);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ToDoItem item = items.get(position);
                Intent editItemIntent = new Intent(ToDoActivity.this, com.codepath.simpletodoapp.EditItemActivity.class);
                editItemIntent.putExtra("item", item);
                editItemIntent.putExtra("position", position);
                startActivityForResult(editItemIntent, TODO_ACTTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("onResume", "called");
        if (dbHelper == null) {
            dbHelper = new DBHelper(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v("onPause", "called");
        dbHelper.closeDB();
        dbHelper = null;
    }

    public void addToDoItem(View view) {
        etNewItem = (EditText) findViewById(R.id.etNewItem);

        ToDoItem item = new ToDoItem();
        item.setItemText(etNewItem.getText().toString());
        items.add(item);
        itemsAdapter.notifyDataSetChanged();
        dbHelper.insertToDoItem(item);
        etNewItem.setText("");
    }

    private void readItems() {
        items = dbHelper.getToDoItems();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == TODO_ACTTIVITY_REQUEST_CODE) && (resultCode == RESULT_OK)) {
            int position = data.getIntExtra("position", 0);
            ToDoItem item = data.getParcelableExtra("item");
            items.set(position, item);
            itemsAdapter.notifyDataSetChanged();
            if (dbHelper == null) {
                dbHelper = new DBHelper(this);
            }
            dbHelper.updateToDoItem(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.to_do, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
