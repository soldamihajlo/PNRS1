package com.example.projekat10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
Button onePageButton;
EditText onePageEditText;
String editTextValue="";
ListView listViewOnePage;
listAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePageButton=findViewById(R.id.pageOneButton);
        onePageButton.setOnClickListener(this);

        adapter=new listAdapter(this);
        adapter.addCharacter(new Character("Novi sad"));
        adapter.addCharacter(new Character("Kula"));
        adapter.addCharacter(new Character("Vrbas"));


        listViewOnePage=findViewById(R.id.pageOneListView);
        listViewOnePage.setAdapter(adapter);

        listViewOnePage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.removeChaacter(position);
                return true;
            }
        });
    }


    @Override
    public void onClick(View v){
        if(v.getId()==R.id.pageOneButton){
            onePageEditText=findViewById(R.id.pageOneEditText);
            editTextValue=onePageEditText.getText().toString();
            if(!editTextValue.isEmpty() && editTextValue.length()<50){
                adapter.addCharacter(new Character(editTextValue));
                onePageEditText.setText("");
            }

        }
    }
}

