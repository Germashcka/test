package com.example.dodo.translate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.zip.Inflater;

public class ActivityChange extends AppCompatActivity {
    private DataBaseAdapter adapter;
    private long translateId=0;
    EditText wordBox,translateBox;
    List <String> list;
    ArrayAdapter arrayAdapter;
    ListView proposal;
    //private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        wordBox = (EditText)findViewById(R.id.word1);
        translateBox = (EditText)findViewById(R.id.word2);
        proposal = (ListView)findViewById(R.id.listviewChange);

        adapter = new DataBaseAdapter(this);
        wordBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if (wordBox.getText().length()==0) {

                }
                else {
                    list = JsonHelper.getJsonStringYandex(wordBox.getText().toString());
                    arrayAdapter = new ArrayAdapter<String>(ActivityChange.this, android.R.layout.simple_list_item_1, list);
                    proposal.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
        proposal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                translateBox.setText(list.get(position));
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            translateId = extras.getLong("id");
        }
        // если 0, то добавление
        if (translateId > 0) {
            // получаем элемент по id из бд
            adapter.open();
            Translate translate = adapter.getTranslate(translateId);
            wordBox.setText(translate.getWord());
            translateBox.setText(translate.getTranslate());
            adapter.close();
        }
    }

    public void onChange(View v){

        Translate translate = new Translate(translateId,wordBox.getText().toString().toUpperCase(),translateBox.getText().toString().toUpperCase());

        adapter.open();
        adapter.update(translate);
        adapter.close();
        goHome();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_change, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_item:
                adapter.open();
                adapter.delete(translateId);
                adapter.close();
                goHome();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goHome(){
        // переход к главной activity
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}
