package com.example.dodo.translate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //https://translate.yandex.net/api/v1.5/tr.json/translate
    //public static ArrayList<Translate> array_translate = new ArrayList<Translate>();
    ListView translate_list;
    //ArrayAdapter<Translate> adapter;
    AlertDialog.Builder ad;
    Context context ;
    public long translateId;
    ArrayAdapter<Translate> arrayAdapter;
    public DataBaseAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new DataBaseAdapter(this);
        translate_list = (ListView)findViewById(R.id.translate_list);
//        adapter = new ArrayAdapter<Translate>(this, android.R.layout.simple_list_item_1,array_translate);
//        translate_list.setAdapter(adapter);
//        adapter.notifyDataSetChanged();

        translate_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(MainActivity.this,ActivityChange.class);
//                intent.putExtra("position", position);
//                startActivity(intent);
                Translate translate = arrayAdapter.getItem(position);
                if(translate!=null) {

                    Intent intent = new Intent(MainActivity.this, ActivityChange.class);
                    intent.putExtra("id", translate.getId());
                    startActivity(intent);
                }
            }
        });
        context = MainActivity.this;
        String message = "Удалить слово?",answer1="Да",answer2="Нет";
        ad = new AlertDialog.Builder(context);
        ad.setMessage(message);
        ad.setPositiveButton(answer1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                array_translate.remove(position);

                adapter.open();
                adapter.delete(translateId);
                adapter.close();
                onResume();
                Toast.makeText(context, "Удалено...", Toast.LENGTH_LONG).show();
//                adapter.notifyDataSetChanged();
            }
        });
        ad.setNegativeButton(answer2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Нет, так нет...", Toast.LENGTH_LONG).show();
            }
        });
        ad.setCancelable(true);
        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(context, "Вы ничего не выбрали",
                        Toast.LENGTH_LONG).show();
            }
        });

        translate_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Translate translate = arrayAdapter.getItem(position);
                MainActivity.this.translateId = translate.getId();
                ad.show();
                return false;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(this, ActivityAdd.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();
        DataBaseAdapter adapter = new DataBaseAdapter(this);
        adapter.open();

        List<Translate> translateList = adapter.getTranslates();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, translateList);
        translate_list.setAdapter(arrayAdapter);
        adapter.close();
    }
    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return  true;
    }

}
