package com.example.dodo.translate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdd extends AppCompatActivity {
    private DataBaseAdapter adapter;
    private long translateId=0;
    EditText el1,el2;
    ListView proposal;
    List<String> list;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        proposal = findViewById(R.id.listviewAdd);



        el2 = (EditText)findViewById(R.id.word2);
        el1 = (EditText)findViewById(R.id.word1);



        el1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                if (el1.getText().length()==0){

                }
                else {
                    list = JsonHelper.getJsonStringYandex(el1.getText().toString());
                    arrayAdapter = new ArrayAdapter<String>(ActivityAdd.this, android.R.layout.simple_list_item_1, list);
                    proposal.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });

        proposal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                el2.setText(list.get(position));
            }
        });


        adapter = new DataBaseAdapter(this);


        adapter.open();
        Translate translate = adapter.getTranslate(translateId);
        adapter.close();

    }

    public void add(View view){

        Translate translate = new Translate(translateId,el1.getText().toString().toUpperCase(), el2.getText().toString().toUpperCase());
        adapter.open();
        adapter.insert(translate);
        adapter.close();
        goHome();
    }

    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
