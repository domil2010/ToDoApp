package com.example.dmichel.todoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDoAdapter;
    ListView lvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateArrayItems();
        setupListViewListener();
        //lvItem.setAdapter(aToDoAdapter);
    }

    public void populateArrayItems(){
        lvItem = (ListView)findViewById(R.id.lvItems);
        todoItems = new ArrayList<>();
        readItem();
        aToDoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,todoItems);
        lvItem.setAdapter(aToDoAdapter);
        todoItems.add("Item 1");
        todoItems.add("Item 2");
        todoItems.add("Item 3");
        todoItems.add("Item 4");
        todoItems.add("Item 5");
        todoItems.add("Item 6");

    }
    public void onAddItem(View v){
        EditText editText = (EditText)findViewById(R.id.etEditText);
        String itemText = editText.getText().toString();
        aToDoAdapter.add(itemText);
        editText.setText("");
        writeItems();
    }
    private void setupListViewListener(){
        lvItem.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                        todoItems.remove(position);
                        aToDoAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                }
        );
    }
    private void readItem(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try{
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir,"todo.txt");
        try{
            FileUtils.writeLines(todoFile,todoItems);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
