package com.example.projekat10;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
Button onePageButton;
EditText onePageEditText;
String editTextValue="";
ListView listViewOnePage;
ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePageButton=findViewById(R.id.pageOneButton);
        onePageButton.setOnClickListener(this);

        adapter=new ListAdapter(this);
        adapter.addCharacter(new Character("Novi sad"));
        adapter.addCharacter(new Character("Kula"));
        adapter.addCharacter(new Character("Vrbas"));
        adapter.addCharacter(new Character("Kucura"));
        adapter.addCharacter(new Character("Wien"));


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
                onePageEditText.setHint("");
            }else if(editTextValue.length()>=50){
                onePageEditText.setError("Maksimalan broj karaktera-50");
                onePageEditText.setText("");
            }else if(editTextValue.isEmpty()){
                onePageEditText.setError("Unesite grad");
            }

        }
    }
}

